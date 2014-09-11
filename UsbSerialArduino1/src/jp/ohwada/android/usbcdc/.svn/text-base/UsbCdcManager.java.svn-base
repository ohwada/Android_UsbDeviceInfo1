package jp.ohwada.android.usbcdc;

import java.util.List;

import jp.ohwada.android.usb.UsbTransferManager;

import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;

/**
 * UsbCdcManager
 */	
public class UsbCdcManager extends UsbTransferManager {

	// interface parameter
	private static final int SUBCLASS_CDC_DATA = 0;	
	private static final int SUBCLASS_COMM_ACM = 2;	

	// SET_SLC_LINE_STATE
	public static final int SCLS_DTE_NOT_PRESENT = 0x00;
	public static final int SCLS_DTE_PRESENT = 0x01;
	public static final int SCLS_CARRIER_DEACTIVATE = 0x00;
	public static final int SCLS_CARRIER_ACTIVATE = 0x02;
	
	private static final int SCLS_REQUEST_TYPE = 0x21;
	private static final int SCLS_REQUEST = 0x22;
	private static final byte[] SCLS_BUFFER = null; 
	private static final int SCLS_LENGHT = 0;
	private static final int SCLS_TIMEOUT = 0;
		
	// SET_LINE_CODING
	public static final int SLC_STOP_BIT_1 = 0;
	public static final int SLC_STOP_BIT_15 = 1;
	public static final int SLC_STOP_BIT_2 = 2;
	public static final int SLC_PARITY_NONE = 0;
	public static final int SLC_PARITY_ODD = 1;
	public static final int SLC_PARITY_EVEN = 2;
	public static final int SLC_PARITY_MARK = 3;
	public static final int SLC_PARITY_SPACE = 4;

	private static final int SLC_REQUEST_TYPE = SCLS_REQUEST_TYPE;
	private static final int SLC_REQUEST = 0x20;		
	private static final int SLC_VALUE = 0;
	private static final int SLC_TIMEOUT = 100;
	
	private static final int DATA_BIT = 8;

	// index for controlTransfer
	private int mAcmIndex = 0;

    /**
	 * constructor
	 * @param Context context
     */	    
	public UsbCdcManager( Context context ) {
		super( context );
		TAG_SUB = "UsbCdcManager";
	}

    /**
	 * open
     */	
	public void open() {
		registerAttached();
		registerDetached();
		registerPermission();
	}

    /**
	 * close
     */	
	public void close() {
		unregister();
		closeDevice();
	}

	/**
	 * findDevice
	 * @return UsbDevice
	 */	
	public UsbDevice findDevice() {
		List<UsbDevice> list = getDeviceList();
		for ( int i = 0; i < list.size(); i++ ) {
			UsbDevice device = list.get( i );;
			boolean ret = findDeviceCdc( list.get( i ) );
			if ( ret ) {
				return device;
			}
		}
		return null;
	}

	/**
	 * findDeviceCdc
	 * @param UsbDevice device
	 * @return boolean 
	 */  
	private boolean findDeviceCdc( UsbDevice device ) {
		log_d( "findDeviceCdc " + device );
		if ( device == null ) return false;
		if ( isDeviceAlready() ) return false;
		boolean ret = findDeviceCdcAcm( device );
		if ( !ret ) return false;
		return findDeviceCdcData( device );
	}

	/**
	 * findDeviceCdcData
	 * @param UsbDevice device
	 * @return boolean 
	 */  
	private boolean findDeviceCdcData( UsbDevice device ) {
	   	UsbInterface usbInterface = findInterface( 
	   		device, 
	   		UsbConstants.USB_CLASS_CDC_DATA, 
	   		SUBCLASS_CDC_DATA );
	   	if ( usbInterface == null ) return false;
	   	UsbEndpoint epOutput = findEndpoint( 
	   		usbInterface,
	   		UsbConstants.USB_DIR_OUT,
	   		UsbConstants.USB_ENDPOINT_XFER_BULK );
		if ( epOutput == null ) return false;
		UsbEndpoint epInput = findEndpoint( 
			usbInterface,
	   		UsbConstants.USB_DIR_IN,
	   		UsbConstants.USB_ENDPOINT_XFER_BULK );
		if ( epInput == null ) return false;
	   	UsbDeviceConnection connection = openDevice( device );
        if ( connection == null ) return false;
		boolean ret = claimInterface( connection, usbInterface );
		if ( ret == false ) return false;
		log_d( "find CDC DATA");
		setDevice( device );
		setInterface( usbInterface );
		setConnection( connection );
		setEndpointOut( epOutput );		
		setEndpointOut( epOutput );
		setEndpointIn( epInput );
		initSetControlLineState();
		startRecv();
        return true;
	}

	/**
	 * findDeviceCdcAcm
	 * @param UsbDevice device
	 * @return boolean 
	 */ 
	private boolean findDeviceCdcAcm( UsbDevice device ) {
	   	UsbInterface usbInterface = findInterface( 
	   		device, 
	   		UsbConstants.USB_CLASS_COMM, 
	   		SUBCLASS_COMM_ACM );
	   	if ( usbInterface == null ) return false;
	   	UsbEndpoint endpoint = findEndpoint( 
	   		usbInterface,
	   		UsbConstants.USB_DIR_IN,
	   		UsbConstants.USB_ENDPOINT_XFER_INT );
		if ( endpoint == null ) return false;
		mAcmIndex = usbInterface.getId();
		log_d( "find CDC ACM " + mAcmIndex );
		return true;
	}
			
	/**
	 * close
	 */
	private void closeDevice() {
		releaseInterface();
		stopRecv();
		clearDevice();
	}

	/**
	 * initSetControlLineState
     * @return boolean
	 */
	private boolean initSetControlLineState() {
		return setControlLineState( 
			SCLS_CARRIER_DEACTIVATE, SCLS_DTE_NOT_PRESENT );
    }

	/**
	 * setControlLineState
	 * @param int carrier
	 * @param int dte
     * @return boolean
	 */
	private boolean setControlLineState( int carrier, int dte ) {
		int value = carrier | dte;
  		int ret = controlTransfer( 
  			SCLS_REQUEST_TYPE, 
  			SCLS_REQUEST, 
  			value, 
			mAcmIndex, 			
  			SCLS_BUFFER, 
  			SCLS_LENGHT, 
  			SCLS_TIMEOUT );
        if ( ret < 0 ) { 
            log_d( "setControlLineState failed " + ret );
            return false;
        }
        return true;
    }

    /**
     * setBaudrate
     * @param int baudrate
     * @return boolean
     */
    public boolean setBaudrate( int baudrate ) {
    	return setLineCoding( 
			baudrate, SLC_STOP_BIT_1, SLC_PARITY_NONE, DATA_BIT );
    }
    
    /**
     * setLineCoding
     * @param int baudrate
     * @param int stop
     * @param int parity
     * @param int data        
     * @return boolean
     */
    public boolean setLineCoding( int baudrate, int stop, int parity, int data ) {
        byte b0 = (byte) (baudrate & 0x000000FF);
        byte b1 = (byte) ((baudrate & 0x0000FF00) >> 8);
        byte b2 = (byte) ((baudrate & 0x00FF0000) >> 16);
        byte b3 = (byte) ((baudrate & 0xFF000000) >> 24);
        byte[] buf = new byte[] {
			b0, b1, b2, b3, 
			(byte)stop, (byte)parity, (byte)data };
        int ret = controlTransfer( 
        	SLC_REQUEST_TYPE, 
        	SLC_REQUEST, 
        	SLC_VALUE, 
			mAcmIndex, 
        	buf, 
        	buf.length, 
        	SLC_TIMEOUT );
        if ( ret < 0 ) { 
            log_d( "setLineCoding failed " + ret );
            return false;
        }
        return true;
    }
    
	/**
	 * receiveDetached
	 * @param UsbDevice device
	 */
   	protected void receiveDetached( UsbDevice device ) {
	   	log_d( "receiveDetached" );
		if ( !matchDevice( device ) ) return;
		closeDevice();
		notifyDetached( device );
   	}

	/**
	 * receivePermission
	 * @param UsbDevice device
	 */ 
	protected void	receivePermission( UsbDevice device ) {
	   	log_d( "receivePermission" );
	   	boolean ret = findDeviceCdc( device );
	   	if ( ret ) {
        	notifyAttached( device );
        }
   	}
   	
}
