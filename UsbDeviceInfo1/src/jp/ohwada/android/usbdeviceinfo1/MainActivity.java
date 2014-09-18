/**
 * 2014-08-01 K.OHWADA
 */ 

package jp.ohwada.android.usbdeviceinfo1;

import java.util.List;

import android.app.Activity;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * MainActivity
 */	
public class MainActivity extends Activity {

	// char
	protected final static String SPACE = " ";

   	// UI
	private ArrayAdapter<String> mAdapter;

	// object
	private UsbDeviceManager mManager;

			
	/**
	 * === onCreate ===
	 */	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnFind = (Button) findViewById( R.id.Button_find );
		btnFind.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				findDevice();
			}
		});
		
		Button btnClear = (Button) findViewById( R.id.Button_clear );
		btnClear.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				clearMessage();
			}
		});
						
		ListView lv = (ListView) findViewById( R.id.ListView_report );
		mAdapter = new ArrayAdapter<String>( 
			this, R.layout.report, R.id.TextView_report );
		lv.setAdapter( mAdapter );
		
		mManager = new UsbDeviceManager( this );
		
		mManager.setOnChangedListener( new UsbDeviceManager.OnChangedListener() {
			@Override
			public void onAttached( UsbDevice device ) {
				execAttached( device );
			}
			@Override
			public void onDetached( UsbDevice device ) {
				execDetached( device );
			}
		});

		mManager.open();		
	}

	/**
	 * === onDestroy ===
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		mManager.close();	
	}

	/**
	 * findDevice
	 */
	private void findDevice() {
		List<String> list = mManager.getDeviceListString();
		if (( list != null )&&( list.size() > 0 )) {
			toast_show( R.string.msg_found );
			addMessage( list );
		} else {
			toast_show( R.string.msg_not_find );
		}
	}

	/**
	 * execAttached
	 * @param UsbDevice device
	 */	  
	private void execAttached( UsbDevice device ) {
		String msg = getString( R.string.msg_attached ) + SPACE + device.getDeviceName(); 
		toast_show( msg );
		List<String> list = mManager.getDeviceInfo( device );
		if ( list != null ) {
			addMessage( list );
		}
	}

	/**
	 * execDetached
	 * @param UsbDevice device
	 */	 
	private void execDetached( UsbDevice device ) {
		String msg = getString( R.string.msg_detached ) + SPACE + device.getDeviceName();
		toast_show( msg );  
	}

	/**
	 * clearMessage
	 */	 
	private void clearMessage()  {
	   	mAdapter.clear();
	}   	
	   	
	/**
	 * addMessage
	 * @param String msg
	 */	
	private void addMessage( List<String> list ) {
		clearMessage();
		mAdapter.addAll( list );
	}

	/**
	 * toast_show
	 * @param int id
	 */ 	
	private void toast_show( int id ) { 
		ToastMaster.showText( this, id, Toast.LENGTH_SHORT );
 	}

	/**
	 * toast_show
	 * @param String msg
	 */ 	
	private void toast_show( String msg ) { 
		ToastMaster.showText( this, msg, Toast.LENGTH_SHORT );
 	}
 
}
