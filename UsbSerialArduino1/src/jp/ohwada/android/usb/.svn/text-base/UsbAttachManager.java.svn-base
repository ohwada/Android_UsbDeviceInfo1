package jp.ohwada.android.usb;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

/**
 * Usb Attach Manager
 */	
public class UsbAttachManager {

	// debug
	protected String TAG_SUB = "UsbAttachManager";

	// action
	private static final String ACTION_USB_PERMISSION =
    	"jp.ohwada.android.usb.USB_PERMISSION";

	// PendingIntent
	private static final int PERM_REQUEST_CODE = 0;
	private static final int PERM_FLAGS = 0;
    	
	// object
	protected Context mContext;
	protected UsbManager mUsbManager;

	// callback
    private OnChangedListener mChangedListener = null;
    
    /**
     * The callback interface 
     */
    public interface OnChangedListener {
        public void onAttached( UsbDevice device );
        public void onDetached( UsbDevice device );
    }

    /**
	 * constructor
	 * @param Context context
     */	    
	public UsbAttachManager( Context context ) {
		mContext = context;
		mUsbManager = (UsbManager) context.getSystemService( Context.USB_SERVICE );	
	}
					
    /**
	 * registerAttached
     */	
	protected void registerAttached() {
		IntentFilter filter = new IntentFilter( 
			UsbManager.ACTION_USB_DEVICE_ATTACHED );
		mContext.registerReceiver( mReceiver, filter );
	}

    /**
	 * registerDetached
     */	
	protected void registerDetached() {
		IntentFilter filter = new IntentFilter( 
			UsbManager.ACTION_USB_DEVICE_DETACHED );
		mContext.registerReceiver( mReceiver, filter );
	}

    /**
	 * registerPermission
     */	
	protected void registerPermission() {
		IntentFilter filter = new IntentFilter(
			ACTION_USB_PERMISSION );
		mContext.registerReceiver( mReceiver, filter );
	}

    /**
	 * unregister
     */	
	protected void unregister() {
		mContext.unregisterReceiver( mReceiver );
	}

	/**
	 * execAttached
	 * @param UsbDevice device 
	 */  
	protected void execAttached( UsbDevice device ) {
	   	log_d( "execAttached" );
	 	if ( device == null ) return;
	 	Intent intent = new Intent( ACTION_USB_PERMISSION );
	 	PendingIntent pending = PendingIntent.getBroadcast( mContext, PERM_REQUEST_CODE, intent, PERM_FLAGS );
		mUsbManager.requestPermission( device, pending );
	}

// --- callback ---		
     /**
     * setOnChangedListener
     * @param OnChangedListener listener
     */
    public void setOnChangedListener( OnChangedListener listener ) {
        mChangedListener = listener;
    }

	/**
	 * notifyAttached
	 * @param UsbDevice device
	 */
	protected void notifyAttached( UsbDevice device ) {
		if (( mChangedListener != null )&&( device != null )) {
			mChangedListener.onAttached( device );
		}
	}

	/**
	 * notifyDetached
	 * @param UsbDevice device
	 */
	protected void notifyDetached( UsbDevice device ) {
		if (( mChangedListener != null )&&( device != null )) {
			mChangedListener.onDetached( device );
		}
	}

// --- BroadcastReceiver ---	
	/**
	 * --- BroadcastReceiver ---
	 */
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
    	public void onReceive( Context context, Intent intent ) {
   			String action = intent.getAction();
   			log_d( "onReceive " + action );
   			UsbDevice device = intent.getParcelableExtra( UsbManager.EXTRA_DEVICE );
 	   		if ( device == null ) return;
			if ( UsbManager.ACTION_USB_DEVICE_ATTACHED.equals( action )) {
   				receiveAttached( device );
   			} else if ( UsbManager.ACTION_USB_DEVICE_DETACHED.equals( action )) {
   				receiveDetached( device );
			} else if ( ACTION_USB_PERMISSION.equals( action )) {
			    synchronized ( this ) {
					boolean perm = intent.getBooleanExtra( UsbManager.EXTRA_PERMISSION_GRANTED, false );
					if ( perm ) {
   						receivePermission( device );
   					}	
   				}	
   			} 
    	}
	};

	/**
	 * receiveAttached
	 * @param UsbDevice device
	 */
   	protected void	receiveAttached( UsbDevice device ) {
   		execAttached( device );
   	}

	/**
	 * receiveDetached
	 * @param UsbDevice device
	 */
   	protected void	receiveDetached( UsbDevice device ) {
   		// dummy
   	}

	/**
	 * receivePermission
	 * @param UsbDevice device
	 */ 
	protected void	receivePermission( UsbDevice device ) {
   		// dummy
   	}

	/**
	 * logcat
	 * @param String msg
	 */
	protected void log_d( String msg ) {
		if (UsbConstant.D) Log.d( UsbConstant.TAG, TAG_SUB + " " + msg );	
	}
	
}
