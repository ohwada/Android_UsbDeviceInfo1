/**
 * 2014-08-01 K.OHWADA
 */ 

package jp.ohwada.android.usbdeviceinfo1;

import java.util.ArrayList;
import java.util.List;

import jp.ohwada.android.usb.UsbConnectionManager;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;

/**
 * UsbDeviceManager
 */	
public class UsbDeviceManager extends UsbConnectionManager {
	
	private List<String> mList = new ArrayList<String>();

    /**
	 * constructor
	 * @param Context context
     */	   	
	public UsbDeviceManager( Context context ) {
		super( context );
		TAG_SUB = "UsbDeviceManager";
	}
	
	/**
	 * === onCreate ===
	 */	
	public void open() {
		registerAttached();
		registerDetached();
	}

	/**
	 * === onDestroy ===
	 */
	public void close() {
		unregister();
	}

	/**
	 * getDeviceList
	 * @return List<String>
	 */
	public List<String> getDeviceListString() {
	  	clearMessage();
		List<UsbDevice> list = getDeviceList();
		if (( list == null )||( list.size() == 0 )) return null;
		for ( UsbDevice device: list ) {
			getDeviceMessage( device );
		}	
		return mList;
	}
	
	/**
	 * getDeviceInfo
	 * @param UsbDevice device
	 * @return List<String>
	 */	
	public List<String> getDeviceInfo( UsbDevice device ) {  
	   	if (device == null) return null;
	  	clearMessage();
	   	return getDeviceMessage( device );
	}

	/**
	 * getDeviceMessage
	 * @param UsbDevice device
	 * @return List<String>
	 */	
	private List<String> getDeviceMessage( UsbDevice device ) {  	   	
	   	addMessage( "Device" );
	   	addMessage( device.toString() );
	   	// Interface
        int intf_count = device.getInterfaceCount();
        for (int i = 0; i < intf_count; i++) {
            UsbInterface intf = device.getInterface(i);
            addMessage( "Interface " + intf.getId() );
            addMessage( intf.toString() );
            int cls = intf.getInterfaceClass();
            int sub = intf.getInterfaceSubclass();
            int pro = intf.getInterfaceProtocol();
            addMessage( UsbDeviceInfo.getClass( cls ));
            addMessage( UsbDeviceInfo.getSubclass( cls, sub ));
            addMessage( UsbDeviceInfo.getProtocol( cls, pro ));
	   		// Endpoint
			int ep_count = intf.getEndpointCount();
			for (int j = 0; j < ep_count; j++) {
				UsbEndpoint endpoint = intf.getEndpoint(j);
				addMessage( "Endpoint " + endpoint.getAddress() );
            	addMessage( endpoint.toString() );           	
            	addMessage( UsbDeviceInfo.getDirection( endpoint.getDirection() ) );
            	addMessage( UsbDeviceInfo.getType( endpoint.getType() ) );
			}
		}
	   	addMessage( "" );
        return mList;
 	}

	/**
	 * clearMessage
	 */	
	private void clearMessage() {
		mList.clear();
	}

	/**
	 * addMessage
	 * @param String msg
	 */	
	private void addMessage( String msg ) {
		mList.add( msg );
		log_d( msg );
	}

// --- BroadcastReceiver ---	
	/**
	 * receiveAttached
	 * @param UsbDevice device
	 */
   	protected void	receiveAttached( UsbDevice device ) {
   		notifyAttached( device );
   	}

	/**
	 * receiveDetached
	 * @param UsbDevice device
	 */
   	protected void	receiveDetached( UsbDevice device ) {
		notifyDetached( device );
   	}
    	
}
