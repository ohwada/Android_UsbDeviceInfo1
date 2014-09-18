package jp.ohwada.android.usb;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Usb Transfer Manager
 */	
public class UsbTransferManager extends UsbConnectionManager {

	// send
	private final static int SEND_BULK_TIMEOUT = 10;

	// recv
	private static final int RECV_BYTE_BUFFER_SIZE  = 256;
	private static final int RECV_BLUK_TIMEOUT = 50;
    private static final long RECV_BLUK_WAIT = 10;
		
	// recv
	private byte[] mRecvByteBuffer = new byte[ RECV_BYTE_BUFFER_SIZE ];
	private String mRecvStringBuff = "";
	private boolean isRecvRunning = false;
		
	// callback
    private OnRecieveListener mRecieveListener = null; 

	/**
	 * interface OnRecieveListener
	 */
    public interface OnRecieveListener {  
        public void onRecieve( byte[] bytes );   
    } 

    /**
	 * constructor
	 * @param Context context
     */	    
	public UsbTransferManager( Context context ) {
		super( context );
		TAG_SUB = "UsbTransferManager";	
	}

	/**
	 * controlTransfer
	 * @param int requestType
	 * @param int request
	 * @param int value
	 * @param int index
	 * @param byte[] buffer
	 * @param int length
	 * @param int timeout
	 * @return int	 
	 * 	 	 
	 */
    protected int controlTransfer( int requestType, int request, int value, int index, byte[] buffer, int length, int timeout ) {
        if ( mConnection != null ) {
			return mConnection.controlTransfer( requestType, request, value, index, buffer, length, timeout ); 
        }
        return -1;
    }
    	
// --- send ---
	/**
	 * sendBulkTransfer
	 * @param byte[] buffer
	 * @return int
	 */
	public int sendBulkTransfer( byte[] buffer ) {
		if ( mConnection != null ) {
			return mConnection.bulkTransfer( mEndpointOut, buffer, buffer.length, SEND_BULK_TIMEOUT );
		}
		return -1;
	}

// --- read ---
	/**
	 * startRecv
	 */
    protected void startRecv() {
        if ( !isRecvRunning ) {
            isRecvRunning = true;
            new Thread( mRecvRunnable ).start();
        }
    }

	/**
	 * stopRecv
	 */
    protected void stopRecv() {
    	isRecvRunning = false;
    }

	/**
	 * --- Runnable ---
	 */   
    private Runnable mRecvRunnable = new Runnable() {
        @Override
        public void run() {
            while ( true ) {
                if ( !isRecvRunning ) return;
                execRecvRunnable();
                sleep( RECV_BLUK_WAIT );
            }
        }
    };

	/**
	 * execRecvRunnable
	 */   
	protected void execRecvRunnable() {
    	int len = recvBulkTransfer();             
        if ( len > 0 ) {
			byte[] bytes = getRecvBytes( len );
			sendRecvMessage( bytes );
		}
	}
	                
	/**
	 * recvBulkTransfer
	 * @return int
	 */  
	protected int recvBulkTransfer() {
		int len = 0;
		try {
			len = mConnection.bulkTransfer( mEndpointIn, mRecvByteBuffer, mRecvByteBuffer.length, RECV_BLUK_TIMEOUT ) ;
		} catch( Exception e ) {
			if (UsbConstant.D) e.printStackTrace();
		}
		return len;	
	}

	/**
	 * getRecvBytes
	 * @param int len
	 * @return byte[]
	 */  
	protected byte[] getRecvBytes( int len ) {
		byte[] bytes = new byte[ len ];
		System.arraycopy( mRecvByteBuffer, 0, bytes, 0, len );
		return bytes;
	}		

	/**
	 * bytesToString
	 * @param byte[] bytes
	 * @return String
	 */	
	public String bytesToString( byte[] bytes ) {
		return bytesToString( bytes, bytes.length );
	}

	/**
	 * bytesToString
	 * @param byte[] bytes
	 * @return String
	 */	
	private String bytesToString( byte[] bytes, int count ) {
	    String str = null;
		try {
			str = new String( bytes, 0, count, "UTF-8" );
		} catch ( UnsupportedEncodingException e ) {
			if (UsbConstant.D) e.printStackTrace();
		}
		return str;
	}

	/**
	 * sendRecvMessage
	 * @param byte[] bytes
	 */	
	protected void sendRecvMessage( byte[] bytes ) {
		Message message = new Message();
		message.obj = bytes;
		recvHandler.sendMessage( message );
	}
	
	/**
	 * getListString
	 * @param String in
	 * @param String delmita
	 * @param int max_size
	 * @param int max_wait 	 
	 * @return List<String>
	 */	
	public List<String> getListString( String in, String delmita, int max_size, int max_wait ) {
		List<String> list = new ArrayList<String>();
		String str = mRecvStringBuff + in;
		int index = 0;
		String msg = "";
		int cnt = 0;
		// divide string by LF, and put into list
		while ( true ) {
			index = str.indexOf( delmita );
			// end loop, if no LF, or reach to imit
			if (( index == -1 )||( cnt > max_wait )) {
				mRecvStringBuff = str;
				break;
			}
			cnt ++;
			int end = index + delmita.length();
			msg = str.substring( 0, end );
			str = str.substring( end );
			list.add( msg );
		}
		// clear recv buffer, if buffer size is over limit
		if ( mRecvStringBuff.length() > max_size ) {
			mRecvStringBuff = "";
		}
		return list;
	}
		                	                
	/**
	 * --- Handler ---
	 */  
	final Handler recvHandler = new Handler() {
		public void handleMessage( Message msg ) {
			notifyRecieve( (byte[]) msg.obj );
		}	
	};

	/**
	 * sleep
	 * @param long time
	 */		
	private void sleep( long time ) {
		try {
			Thread.sleep( time );
		} catch ( InterruptedException e ) {
			if (UsbConstant.D) e.printStackTrace();
		}
	}

// --- callback ---		
    /**
     * Sets the listener to be called 
     * @param listener The listener.
     */
    public void setOnRecieveListener( OnRecieveListener listener ) {
        mRecieveListener = listener;
    }

	/**
	 * notifyRecieve
	 * @param byte[] bytes
	 */	 
	protected void notifyRecieve( byte[] bytes ) {
		if ( mRecieveListener != null) {  
			mRecieveListener.onRecieve( bytes );  
		} 
	}

}
