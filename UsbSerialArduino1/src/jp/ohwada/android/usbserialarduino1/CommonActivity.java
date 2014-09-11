/**
 * 2014-08-01 K.OHWADA
 */ 

package jp.ohwada.android.usbserialarduino1;

import java.util.List;

import jp.ohwada.android.usbcdc.UsbCdcManager;

import android.app.Activity;
import android.hardware.usb.UsbDevice;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * CommonActivity
 */     
public class CommonActivity extends Activity {

	// debug
	protected final static boolean D = true;
	private final static String TAG = "UsbSerial";
	
	// serial
	private final static int BAUDRATE = 9600;

	// recieve
	private final static int MAX_STRING_LENGTH = 256;
	private final static int MAX_STRING_WAIT = 100;	

	// char
	protected final static String SPACE = " ";
	private final static String LF = "\n";
				
	// object
	private UsbCdcManager mUsbCdcManager;

	// UI 
	private TextView mTextViewConnect;

	/**
	 * initManager
	 */  
	protected void initManager() {                
		mUsbCdcManager = new UsbCdcManager( this );
		mUsbCdcManager.setBaudrate( BAUDRATE );
		mUsbCdcManager.setOnChangedListener( new UsbCdcManager.OnChangedListener() {
			@Override
			public void onAttached( UsbDevice device ) {
				execAttached( device );
			}
			@Override
			public void onDetached( UsbDevice device ) {
				execDetached( device );
			}
		});
		mUsbCdcManager.setOnRecieveListener( new UsbCdcManager.OnRecieveListener() {
			@Override
			public void onRecieve( byte[] bytes ) {
				execRecieve( bytes ); 
			}
		});
	}

	/**
	 * initView
	 */     
	protected void initView() {
		mTextViewConnect = (TextView) findViewById( R.id.TextView_connect );
	}

	/**
	 * === onResume ===
	 */
	@Override
	public void onResume() {
		super.onResume();
		mUsbCdcManager.open();
		UsbDevice device = mUsbCdcManager.findDevice();
		if ( device != null ) {
			String name = device.getDeviceName();
			showConnected( name );
			String msg = getString( R.string.msg_found ) + SPACE + name;
			toast_show( msg );
		} else {
			showNotConnect();
		}       
	}

	/**
	 * === onDestroy ===
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		mUsbCdcManager.close();
	}

	/**
	 * send with LineFeed
	 * @param String str
	 */
	protected void sendLF( String str ) {
		send( str + LF );
	}

	/**
	 * send
	 * @param String str
	 */
	protected void send( String str ) {
		mUsbCdcManager.sendBulkTransfer( str.getBytes() );
	}

	/**
	 * execRecieve
	 * @param byte[] bytes
	 */	
	protected void execRecieve( byte[] bytes ) {
		// dummy
	}

	/**
	 * setBaudrate
	 * @param int baudrate
	 * @return boolean
	 */	
	protected boolean setBaudrate( int baudrate ) {
		return mUsbCdcManager.setBaudrate( baudrate );
	}

	/**
	 * bytesToString
	 * @param byte[] bytes
	 */		
	protected String bytesToString( byte[] bytes ) {
		return mUsbCdcManager.bytesToString( bytes );
	}

	/**
	 * getListString
	 * @param String str
	 * @return List<String>
	 */	
	protected List<String> getListString( String str ) {
		// arduino send with CR and LF
		return mUsbCdcManager.getListString( 
			str, LF, MAX_STRING_LENGTH, MAX_STRING_WAIT );
	}

	/**
	 * procRecieve
	 * @param String str
	 */
	protected void procRecieve( String str ) {
		// dummy
	}
	                                        
	/**
	 * execAttached
	 * @param UsbDevice device
	 */       
	private void execAttached( UsbDevice device ) {
		String name = device.getDeviceName();
		showConnected( name );
		String msg = getString( R.string.msg_attached ) + SPACE + name;
		toast_show( msg );
	}

	/**
	 * execDetached
	 * @param UsbDevice device
	 */      
	private void execDetached( UsbDevice device ) {
		showNotConnect();
		String msg = getString( R.string.msg_detached ) + SPACE + device.getDeviceName();
		toast_show( msg );  
	}

	/**
	 * showNotConnect
	 */      
	private void showNotConnect() {
		mTextViewConnect.setText( R.string.msg_not_connect );
	}

	/**
	 * showNotConnect
	 */      
	private void showConnected( String name ) {
		String msg = getString( R.string.msg_connected ) + SPACE +  name;
		mTextViewConnect.setText( msg );
	}

	/**
	 * toast_show
	 * @param int res_id
	 */ 
	protected  void toast_show( int res_id ) {
		ToastMaster.showText( this, res_id, Toast.LENGTH_SHORT );
	}

	/**
	 * toast_show
	 * @param String msg
	 */ 
	protected void toast_show( String msg ) {
		ToastMaster.showText( this, msg, Toast.LENGTH_SHORT );
	}

	/**
	 * log_d
	 * @param String msg
	 */        
	protected void log_d( String msg ) {
		Log.d( TAG, msg );
	}
                
}
