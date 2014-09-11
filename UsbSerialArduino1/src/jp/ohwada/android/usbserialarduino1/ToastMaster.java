/**
 * 2014-08-01 K.OHWADA
 */ 

package jp.ohwada.android.usbserialarduino1;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

/**
 * ToastMaster
 */
public class ToastMaster {

    private static Toast sToast = null;

	/**
	 * === Constructor ===
	 */
    private ToastMaster() {
		// dummy
    }

	/**
	 * setToast
	 * @param Toast toast
	 */
    public static void setToast( Toast toast ) {
        if (sToast != null) {
            sToast.cancel();
        }    
        sToast = toast;
    }

	/**
	 * cancelToast
	 */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
        }    
        sToast = null;
    }

	/**
     * Make a standard toast that just contains a text view.
     * @param context  The context to use.  
     * @param text     The text to show.  Can be formatted text.
     * @param duration How long to display the message.  
	 * @return Toast
     */
    public static Toast makeText( Context context, CharSequence text, int duration ) {
		return Toast.makeText( context, text, duration );
	}

    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param context  The context to use. 
     * @param resId    The resource id of the string resource to use.  Can be formatted text.
     * @param duration How long to display the message. 
     * @return Toast
     */
	public static Toast makeText( Context context, int resId, int duration )
    		throws Resources.NotFoundException {
        return makeText( context, context.getResources().getText(resId), duration );		
	} 

	/**
     * Show a standard toast that just contains a text view.
     * @param context  The context to use.  
     * @param text     The text to show.  Can be formatted text.
     * @param duration How long to display the message.  
     */
    public static void showText( Context context, CharSequence text, int duration ) {
		Toast toast = makeText( context, text, duration );
        setToast( toast );
        toast.show();
	}

    /**
     * Show a standard toast that just contains a text view with the text from a resource.
     * @param context  The context to use. 
     * @param resId    The resource id of the string resource to use.  Can be formatted text.
     * @param duration How long to display the message. 
     * @return Toast
     */
    public static void showText( Context context, int resId, int duration ) {
		Toast toast = makeText( context, resId, duration );
        setToast( toast );
        toast.show();	
	} 
}
