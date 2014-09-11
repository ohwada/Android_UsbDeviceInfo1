package jp.ohwada.android.usb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

/**
 * Usb Connection Manager
 */	
public class UsbConnectionManager extends UsbAttachManager {
	
	// usb interface
	private static final boolean CLAIMINTERFACE_FORCE = true;

	// variable
	protected UsbDeviceConnection mConnection = null;
	protected UsbEndpoint mEndpointOut = null;
	protected UsbEndpoint mEndpointIn = null;

	private UsbDevice mDevice = null;
	private UsbInterface mInterface = null;

    /**
	 * constructor
	 * @param Context context
     */	    
	public UsbConnectionManager( Context context ) {
		super( context );
		TAG_SUB = "UsbConnectionManager";
	}

	/**
	 * getDeviceList
	 * @return List<UsbDevice>
	 */	
	protected List<UsbDevice> getDeviceList() {
		List<UsbDevice> list = new ArrayList<UsbDevice>();
		UsbManager manager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
	  	HashMap<String, UsbDevice>	hash = manager.getDeviceList();
	  	if ( hash == null ) return null;
	  	String key = "";
	  	UsbDevice device = null;
	  	Iterator<String> it = hash.keySet().iterator();
        while ( it.hasNext() ) {
            key = it.next();
            device = hash.get( key );
            if ( device != null ) {
            	list.add( device );
            }
        }
        return list;
	}

	/**
	 * openDevice
	 * @param UsbDevice device
	 * @return UsbDeviceConnection
	 */ 
	protected UsbDeviceConnection openDevice( UsbDevice device ) {
		 UsbDeviceConnection connection = mUsbManager.openDevice( device );
		 if ( connection == null ) {
            log_d( "could not open device" );
        	return null;
        }
        return connection; 
	}


	/**
	 * findInterface
	 * @param UsbDevice device
	 * @param int cls
	 * @param int sub
	 * @return List<UsbInterface>
	 */
	protected UsbInterface findInterface( UsbDevice device, int cls, int sub ) {
	   	List<UsbInterface> list = findInterfaceList( device, cls, sub );
	   	if ( list.size() == 0 ) {
            log_d( "could not find interface" );
	   		return null;
	   	}
	   	return list.get(0);
	}
		   	
	/**
	 * findInterfaceList
	 * @param UsbDevice device
	 * @param int cls
	 * @param int sub
	 * @return List<UsbInterface>
	 */
	protected List<UsbInterface> findInterfaceList( UsbDevice device, int cls, int sub ) {
		List<UsbInterface> list = new ArrayList<UsbInterface>();
        int count = device.getInterfaceCount();
        for ( int i = 0; i < count; i++ ) {
            UsbInterface usbInterface = device.getInterface( i );
            log_d( usbInterface.toString() ); 
            if ( checkValue( usbInterface.getInterfaceClass(), cls ) &&
			     checkValue( usbInterface.getInterfaceSubclass(), sub )) { 
				list.add( usbInterface );
 			}
		}
		return list;	
 	}

	/**
	 * findEndpoint
	 * @param UsbInterface usbInterface
	 * @param int dir
	 * @param int type
	 * @return UsbEndpoint
	 */
	protected UsbEndpoint findEndpoint( UsbInterface usbInterface, int dir, int type ) {
		int[] types = new int[]{ type }; 
	   	List<UsbEndpoint> list = findEndpointList( usbInterface, dir, types );
	   	if ( list.size() == 0 ) {
			log_d( "could not find endpoint" );
	   		return null;
	   	}
	   	return list.get(0);
	}

	/**
	 * findEndpointList
	 * @param UsbInterface usbInterface
	 * @param int dir
	 * @param int[] types
	 * @return List<UsbEndpoint>
	 */
	protected List<UsbEndpoint> findEndpointList( UsbInterface usbInterface, int dir, int[] types ) {
		List<UsbEndpoint> list = new ArrayList<UsbEndpoint>();
		int count = usbInterface.getEndpointCount();
		for ( int i = 0;  i < count; i++ ) {
			UsbEndpoint endpoint = usbInterface.getEndpoint( i );
			log_d( endpoint.toString() ); 
            if ( checkValue( endpoint.getDirection(), dir ) &&
			     checkType( endpoint.getType(), types )) {  
				list.add( endpoint );
			}
		}
		return list;	
	}
						
	/**
	 * claimInterface
	 * @param UsbDeviceConnection connection
	 * @param UsbInterface usbInterface
	 */
	protected boolean claimInterface( UsbDeviceConnection connection, UsbInterface usbInterface ) {	
		boolean ret = connection.claimInterface( usbInterface, CLAIMINTERFACE_FORCE );
		if ( !ret ) {
			log_d( "claimInterface falied" );
			return false;
		}
		return true;
	}

	/**
	 * releaseInterface
	 */
	protected void releaseInterface() {
		if ( mConnection != null ) {
			mConnection.releaseInterface( mInterface );
			mConnection.close();
		}
		mInterface = null;
		mConnection = null;
	}

	/**
	 * checkVendor
	 * @param UsbDevice device
	 * @param int vendor
	 * @return boolean 
	 */ 
	protected boolean checkVendor( UsbDevice device, int vendor ) {
		if ( device == null ) return false;
	 	if ( checkValue( device.getVendorId(), vendor )) {
			return true;
		}
		return false;	
	}

	/**
	 * checkProduct
	 * @param UsbDevice device
	 * @param int product	 
	 * @return boolean 
	 */ 
	protected boolean checkProduct( UsbDevice device, int product ) {
		if ( device == null ) return false;
	 	if ( checkValue( device.getProductId(), product )) {
			return true;
		}
		return false;	
	}

	/**
	 * checkValue
	 * @param int current
	 * @param int target
	 * @return boolean 
	 */ 
	protected boolean checkValue( int current, int target ) {
		if ( target == current ) return true;
		return false;
	}

	/**
	 * checkType
	 * @param int current
	 * @param int[] types
	 * @return boolean 
	 */ 
	protected boolean checkType( int current, int[] types ) {
		if ( types == null ) return true;
		for ( int i = 0; i < types.length; i ++ ) {
			if ( current == types[ i ] ) {
				 return true;
			}	 
		}
		return false;
	}
				
	/**
	 * matchDevice
	 * @param UsbDevice device1
	 * @param UsbDevice device2
	 * @return boolean 
	 */    	
	protected boolean matchDevice( UsbDevice device1, UsbDevice device2 ) {
		if ( device1 == null ) return false;
		if ( device2 == null ) return false;
		boolean ret = device1.getDeviceName().equals( device2.getDeviceName() );
		return ret;
	}

	/**
	 * setDevice
	 * @param UsbDevice device
	 */ 
	protected void setDevice( UsbDevice device ) {
        mDevice = device; 
	}

	/**
	 * setConnection
	 * @param UsbDeviceConnection connection
	 */ 
	protected void setConnection( UsbDeviceConnection connection ) {
        mConnection = connection;
	}

	/**
	 * clearDevice
	 */ 
	protected void clearDevice() {
		mDevice = null;
	}

	/**
	 * isDeviceAlready
	 */ 
	protected boolean isDeviceAlready() {
		boolean ret = ( mDevice == null ) ? false : true;
		return ret;
	}

	/**
	 * matchDevice
	 * @param UsbDevice device
	 */ 
	protected boolean matchDevice( UsbDevice device ) {
		return matchDevice( device, mDevice );
	}

	/**
	 * setInterface
	 * @param UsbInterface usbInterface
	 */
	protected void setInterface( UsbInterface usbInterface ) {	
		mInterface = usbInterface ;
	}

	/**
	 * setEndpointOut
	 * @param UsbEndpoint ep
	 */
	protected void setEndpointOut( UsbEndpoint ep ) {
		mEndpointOut = ep;
	}

	/**
	 * setEndpointIn
	 * @param UsbEndpoint ep
	 */
	protected void setEndpointIn( UsbEndpoint ep ) {
		mEndpointIn = ep;
	}

}
