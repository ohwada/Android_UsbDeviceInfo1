package jp.ohwada.android.usb;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Usb Transfer Manager
 */	
public class UsbTransferManager extends UsbConnectionManager {

	// message
	protected final static int MSG_ARG1_BYTES = 1;
	protected final static int MSG_ARG1_STRING = 2;	

	// send
	private final static int SEND_BULK_TIMEOUT = 10;

	// recv
	private static final int RECV_BUFFER_SIZE  = 256;
	private static final int RECV_BLUK_TIMEOUT = 50;
    private static final long RECV_BLUK_WAIT = 10;

	// recv
	private byte[] mRecvBuffer = new byte[ RECV_BUFFER_SIZE ];
	private boolean isRecvRunning = false;

	// callback
    private OnRecieveListener mRecieveListener = null; 

	/**
	 * interface OnRecieveListener
	 */
    public interface OnRecieveListener {  
        public void onRecieve( byte[] bytes );
        public void onRecieve( String str );    
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
	 * execRecvRunnable(
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
			len = mConnection.bulkTransfer( mEndpointIn, mRecvBuffer, mRecvBuffer.length, RECV_BLUK_TIMEOUT ) ;
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
		System.arraycopy( mRecvBuffer, 0, bytes, 0, len );
		return bytes;
	}		

	/**
	 * getRecvString
	 * @param int len
	 * @return String
	 */ 			
	protected String getRecvString( int len ) {
		return bytesToString( mRecvBuffer, len );
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
		message.arg1 = MSG_ARG1_BYTES;
		message.obj = bytes;
		recvHandler.sendMessage( message );
	}

	/**
	 * sendRecvMessage
	 * @param String str
	 */		
	protected void sendRecvMessage( String str ) {
		Message message = new Message();
		message.arg1 = MSG_ARG1_STRING;
		message.obj = str;
		recvHandler.sendMessage( message );
	}
		                	                
	/**
	 * --- Handler ---
	 */  
	final Handler recvHandler = new Handler() {
		public void handleMessage( Message msg ) {
			if ( msg.arg1 == MSG_ARG1_BYTES ) {
				notifyRecieve( (byte[]) msg.obj );
			} else if ( msg.arg1 == MSG_ARG1_STRING ) {
				notifyRecieve( (String) msg.obj );
			}
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

	/**
	 * notifyRecieve
	 * @param String str
	 */	
	protected void notifyRecieve( String str ) {
		if ( mRecieveListener != null) {  
			mRecieveListener.onRecieve( str );  
		} 
	}

}
