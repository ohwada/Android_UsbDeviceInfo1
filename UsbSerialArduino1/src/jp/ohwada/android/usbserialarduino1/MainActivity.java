/**
 * 2014-08-01 K.OHWADA
 */ 

package jp.ohwada.android.usbserialarduino1;

import java.text.DecimalFormat;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * MainActivity
 */     
public class MainActivity extends CommonActivity {

	// PWM value
    private static final int PWM_MAX = 255;
    private static final int PWM_PROGRESS = 128;
    // PWM interval time 0.1 sec
     private static final int PWM_TIME = 100;	
            
	// UI
	private GraphView mGraphView;
	private TextView mTextViewSwitch;
	private Button mButtonLed;
	private ListView mListView;
	private ArrayAdapter<String> mAdapter;
	
	// PWM value format
	private DecimalFormat mFormatPwm = new DecimalFormat( "000" );
	// Time when send PWM
	private long mTimePwm = 0; 

	// LED Status
	private boolean isLed = false;

	/**
	 * === onCreate ===
	 */     
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		initManager();
		initView();

		mTextViewSwitch = (TextView) findViewById( R.id.TextView_switch );
		mTextViewSwitch.setText( R.string.label_on );
		mGraphView = (GraphView) findViewById( R.id.GraphView );
					
		mButtonLed = (Button) findViewById( R.id.Button_led );
		mButtonLed.setText( R.string.label_off );
		mButtonLed.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				execLed();
			}
		});
		
		SeekBar sbPwm = (SeekBar) findViewById( R.id.SeekBar_pwm );
        sbPwm.setMax( PWM_MAX );
        sbPwm.setProgress( PWM_PROGRESS );
        sbPwm.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {
				// dummy
            }
            @Override
            public void onProgressChanged( SeekBar seekBar, int progress, boolean fromTouch ) {
				execPwm( progress );
            }
            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {
				// dummy
            }
        });

		mListView = (ListView) findViewById( R.id.ListView );
		mAdapter = new ArrayAdapter<String>( 
			this, R.layout.report, R.id.TextView_report );
		mListView.setAdapter( mAdapter );
		
		showGraph();
	}

	/**
	 * === onCreateOptionsMenu ===
	 */
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.main, menu );
		return true;
    }
 
 	/**
	 * === onOptionsItemSelected ===
	 */
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
		switch ( item.getItemId() ) {
			case R.id.menu_log:
				showLog();
				return true;
			case R.id.menu_graph:
				showGraph();
				return true;
        }
        return super.onOptionsItemSelected( item );
    }

 	/**
	 * showGraph
	 */
	private void showGraph() {
		mGraphView.setVisibility( View.VISIBLE );
		mListView.setVisibility( View.GONE );
	}

 	/**
	 * showLog
	 */
	private void showLog() {
		mGraphView.setVisibility( View.GONE );
		mListView.setVisibility( View.VISIBLE );
	}

	/**
	 * execLed
	 */
	private void execLed() {
		if ( isLed ) {
			mButtonLed.setText( R.string.label_on );
			mButtonLed.setTextColor( Color.RED );
			sendLF( "L1" );
		} else {
			mButtonLed.setText( R.string.label_off );
			mButtonLed.setTextColor( Color.BLACK );
			sendLF( "L0" );
		}
		isLed = !isLed;
	}

	/**
	 * execPwm
	 * @param int progress
	 */
	private void execPwm( int progress ) {
		long time = SystemClock.elapsedRealtime();
		// send command, when 0.1 sec or more has passed since the last sending. 
		if ( time > mTimePwm + PWM_TIME ) {
			mTimePwm = time;
			sendLF( "P" + mFormatPwm.format( progress ) );
		}
	}

	/**
	 * execRecieve
	 * @param byte[] bytes
	 */	
	protected void execRecieve( byte[] bytes ) {
		String str = bytesToString( bytes );
		log_d( "execRecieve " + str );
		if ( str == null ) return;
		mAdapter.add( str );
		List<String> list = getListString( str );
		if ( list.size() == 0 ) return;
		for ( String s: list ) {
			execRecvMsg( s );
		}
	}

	/**
	 * execRecvMsg
	 * @param String str
	 */
	protected void execRecvMsg( String str ) {
		log_d( "execRecvMsg " + str );
		if ( str == null ) return;
		if ( str.startsWith( "B0" )) {
			mTextViewSwitch.setText( "Off" );	
			mTextViewSwitch.setTextColor( Color.BLACK );
		} else if ( str.startsWith( "B1" )) {
			mTextViewSwitch.setText( "On" );	
			mTextViewSwitch.setTextColor( Color.RED );
		} else if ( str.startsWith( "A" )) {
			int value = parseInt( str.substring( 1 ) );
			mGraphView.setData( value );
		}
	}

	/**
	 * parseInt
	 * @param String str
	 * @return int
	 */
	private int parseInt( String str ) {	
		int n = 0;
		try {
			n = Integer.parseInt( str.trim() );
		} catch ( Exception e ) {
           if (D) e.printStackTrace();
		}
		return n;
	}	

}
