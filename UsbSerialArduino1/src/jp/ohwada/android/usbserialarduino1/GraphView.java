/**
 * 2014-08-01 K.OHWADA
 */ 

package jp.ohwada.android.usbserialarduino1;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * GraphView
 */ 
public class GraphView extends View {

	// debug
	private final static String TAG = "UsbSerial";
	private final static boolean D = true;
	
	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 1023;
	private static final int NUM_X = 100;
	private static final int MARGIN_LEFT = 8;
	private static final int MARGIN_RIGHT = 8;
	private static final int MARGIN_TOP = 8;
	private static final int MARGIN_BOTTOM = 8;
	private static final int LINE_WIDTH = 4;

	private Paint mPaintRect;
	private Paint mPaintLine; 
	private Rect mRect;
	private float mScaleX = 0;
	private float mScaleY = 0;	
	private List<Point> mLineList = new ArrayList<Point>();
	private int mXCount = 0;

    /**
     * === constractor ====
     */    
    public GraphView( Context context ) {
        super( context );
        initView();
    }

    /**
     * === constractor ====
     */    
    public GraphView( Context context, AttributeSet attrs ) {
        super( context, attrs );
        initView();
    }

    /**
     * === constractor ====
     */
    public GraphView( Context context, AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
        initView();
    }    

    /**
     * initView
     */
	private void initView() {
		mPaintRect = new Paint();
		mPaintRect.setColor( Color.BLACK );
		mPaintRect.setStyle( Style.STROKE );
		mPaintLine = new Paint();
		mPaintLine.setColor( Color.RED );
		mPaintLine.setStyle( Style.STROKE );
		mPaintLine.setStrokeWidth( LINE_WIDTH );
		mRect = new Rect( 0, 0, 0, 0 );
	}

    /** 
	 * === onWindowFocusChanged ===
	 */	
	@Override  
	public void onWindowFocusChanged( boolean hasFocus ) {  
		super.onWindowFocusChanged( hasFocus );  
		int width = getWidth();  
		int height = getHeight();
		mRect = new Rect( 
			MARGIN_LEFT, 
			MARGIN_TOP, 
			width - MARGIN_LEFT, 
			height - MARGIN_BOTTOM );	
		mScaleX = 
			(float)( width - MARGIN_LEFT - MARGIN_RIGHT ) /
			(float)NUM_X;
		mScaleY = 
			(float)( height - MARGIN_TOP - MARGIN_BOTTOM ) / 
			(float)( MAX_VALUE - MIN_VALUE );
		invalidate();
	} 

    /** 
      * === onDraw ===
      */	        
	@Override
    protected void onDraw( Canvas canvas ) {
    	// rect
        canvas.drawRect( mRect, mPaintRect );
        // line
		int x0 = 0;
		int y0 = 0;
		int x1 = 0;
		int y1 = 0;
		// draw, if plot points are two or more
		int size = mLineList.size();
		if ( size >= 2 ) {
			Point p = mLineList.get(0);
			x0 = p.x;
			y0 = p.y;
			// draw line with previous point
			for ( int i = 1; i < size; i++ ) {
				p = mLineList.get(i);
				x1 = p.x;
				y1 = p.y;	
    			canvas.drawLine( x0, y0, x1, y1, mPaintLine );
    			x0 = x1;
				y0 = y1;
			}
		}
	}

    /** 
      * setData
      * @param int value
      */
	public void setData( int value ) {
		// adjust value with minimum 
		if ( value < MIN_VALUE ) {
			value = MIN_VALUE;
		}
		// adjust value with maximum
		if ( value > MAX_VALUE ) {
			value = MAX_VALUE;
		}
		// clear plot point, if draw to the right end of screen.
		if ( mXCount > NUM_X ) {
			mXCount = 0;
			mLineList.clear();
		}
		// x : move one step to the right
		int x = (int)( mScaleX * (float)mXCount ) + MARGIN_LEFT;
		mXCount ++;
		// y : adjust value with scale
		int y = (int)( mScaleY * (float)( MAX_VALUE - value ) ) + MARGIN_TOP;
		mLineList.add( new Point( x, y ) );
		invalidate();
	}

	/**
	 * log_d
	 * @param String msg
	 */        
	@SuppressWarnings("unused")
	private void log_d( String msg ) {
		if (D) Log.d( TAG, msg );
	}
}

