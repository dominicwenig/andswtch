package sta.andswtch.gui;


import sta.andswtch.R;
import sta.andswtch.db.PowerPointDbAdapter;
import sta.andswtch.db.PowerPointRow;
import sta.andswtch.extensionLead.ExtensionLead;
import sta.andswtch.extensionLead.ExtensionLeadManager;
import sta.andswtch.gui.timepicker.TimePicker;
import sta.andswtch.gui.timepicker.TimePickerDialog;
import sta.andswtch.gui.timepicker.Util;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PowerPointView extends OptionsMenu implements IAndSwtchViews {

	private PowerPointDbAdapter ppDbHelper;

	private ExtensionLeadManager extLeadManager;
	private ExtensionLead extLead;
	private TextView name;
	private TextView counter;
	private ToggleButton onOff;
	private Button onOffDelay;
	private CountDownTimer countDownTimer = null;
	private CountDownTimer endTimeCounter = null;
	private int onOffTag;
	private int sumSeconds = 0;
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;
	TimePickerDialog timePickerDialog = null;

	private final String TAG = PowerPointView.class.getName();

	static final int TIME_DIALOG_ID = 0;

	private Handler handlerEvent = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: {
				checkState();
				break;
			}
			case 2: {
				Context context = getApplicationContext();
				CharSequence text = (String) msg.obj;
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
			default: {
				super.handleMessage(msg);
				break;
			}
			}
			setName();
		}
	};

	public void selectTime(View v) {
		showDialog(TIME_DIALOG_ID);
		if (timePickerDialog != null) {
			timePickerDialog.updateTime(this.hours, this.minutes, this.seconds);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.powerpoint);

		this.extLeadManager = ExtensionLeadManager.getInstance(this);
		this.extLead = this.extLeadManager.getExtLeadFromView(this);
		this.init();
		this.checkState();
	}

	@Override
	public void onStart() {
		super.onStart();
		this.extLead.sendUpdateMessage();
	}

	@Override
	public void onResume() {
		super.onResume();
		setDelayTime(this.sumSeconds);
		this.extLead.startAutoRefreshRunning();
	}

	@Override
	public void onPause() {
		super.onPause();
		this.extLead.stopAutoRefreshRunning();
	}

	private void init() {
		this.name = (TextView) findViewById(R.id.ppName);
		this.onOff = (ToggleButton) findViewById(R.id.onOff);
		this.onOffDelay = (Button) findViewById(R.id.onOffDelay);
		this.counter = (TextView) findViewById(R.id.counter);

		Bundle extra = getIntent().getExtras();
		String tagString = extra.getString("powerPoint");
		if (tagString != null) {
			this.onOff.setTag(tagString);
			this.onOffTag = Integer.parseInt(tagString);
			Log.d(TAG, "Tag of this view is " + onOffTag);
			this.name.setText(this.extLead.getPowerPointName(this.onOffTag));
		}
		else{
			Log.e(TAG, "Tag of ppwerPointView is null");
		}
		String name = extra.getString("name");
		this.setTitle("AndSwtch - " + name);

		// open the database
		ppDbHelper = new PowerPointDbAdapter(this);
		ppDbHelper.open();

		PowerPointRow row = ppDbHelper.fetchPowerPointRow(onOffTag);
		// get the values from the response object
		hours = row.getHours();
		minutes = row.getMinutes();
		seconds = row.getSeconds();
		sumSeconds = hours * 60 * 60 + minutes * 60 + seconds;
		// get the last end time
		Time endTime = row.getEndTime();
		Log.d(TAG, "end time from db is" + endTime.format("%H:%M:%S"));
		// get the current time
		Time now = new Time();
		now.setToNow();
		Log.d(TAG, "the time now is" + now.format("%H:%M:%S"));
		// resume to the countdown if the endtime is in the future, display the
		// last selected countdown time otherwise
		if (now.before(endTime)) {
			long secDif = (endTime.toMillis(true) - now.toMillis(true)) / 1000;
			Log.d(TAG, "the time difference for the delay from the database is"
					+ secDif + "seconds");
			setDelayTime((int) secDif);
			setEndTime((int) secDif);
			this.startTimer((int) secDif);
		} else {
			
			setDelayTime(sumSeconds);
			startEndTimeCounter();
		}

		
		Log.d(TAG, "End time is: " + endTime);

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			timePickerDialog = new TimePickerDialog(this, mTimeSetListener,
					hours, minutes, seconds);
			return timePickerDialog;
		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hour, int minute,
				int second, boolean cancel) {
			if (!cancel) {
				hours = hour;
				minutes = minute;
				seconds = second;
				sumSeconds = seconds + minutes * 60 + hours * 60 * 60;
				setDelayTime(sumSeconds);
				setEndTime(sumSeconds);
				offDelayed();
			}
		}
	};

	public Context getAppContext() {
		return this.getApplicationContext();
	}

	public void updateActivity() {
		Message msg = new Message();
		msg.what = 1;
		this.handlerEvent.sendMessage(msg);
	}

	public void showErrorMessage(String message) {
		Message msg = new Message();
		msg.what = 2;
		msg.obj = message;
		this.handlerEvent.sendMessage(msg);
	}

	public void onOff(View v) {
		String tagString = (String) v.getTag();
		if (tagString != null) {
			int tag = Integer.parseInt(tagString);
			if (tag == 0) {
				// switch delayed
				// set sumseconds new because we want to reset it!
				sumSeconds = hours * 60 * 60 + minutes * 60 + seconds;
				offDelayed();
			} else {
				this.extLead.switchState(tag);
			}
		}
	}

	private void offDelayed() {
		this.extLead.sendState(this.onOffTag, false, this.sumSeconds);
		ppDbHelper.updatePowerPointRow(onOffTag, sumSeconds, hours, minutes,
				seconds);

		startTimer(sumSeconds);
	}

	public void startTimer(int secsToGo) {
		if (countDownTimer != null) {
			countDownTimer.cancel();
		}
		if(endTimeCounter!=null){
			endTimeCounter.cancel();
		}
		onOffDelay.setText(R.string.restartDelay);
		setEndTime(secsToGo);
		countDownTimer = new CountDownTimer(secsToGo * 1000, 1000) {

			public void onTick(long millisUntilFinished) {
				setDelayTime((int) (millisUntilFinished / 1000));
			}

			public void onFinish() {
				setDelayTime(sumSeconds);
				startEndTimeCounter();
				extLead.sendUpdateMessage();
				onOffDelay.setText(R.string.onOffDelay);
			}

		}.start();
	}

	public void setDelayTime(int seconds) {
		String hour = Util.pad(seconds / (60 * 60));
		String min = Util.pad((seconds % (60 * 60) / 60));
		String sec = Util.pad((seconds % (60)));

		this.counter.setText(hour + ":" + min + ":" + sec);
	}

	@Override
	protected void onDestroy() {
		ppDbHelper.close();
		super.onDestroy();
	}

	private void checkState() {
		if (this.extLead.isPowerPointOn(this.onOffTag)) {
			this.setOn();
		} else {
			this.setOff();
		}
	}

	private void setOn() {
		this.onOff.setChecked(true);
	}

	private void setOff() {
		this.onOff.setChecked(false);
	}

	private void setName() {
		if (this.extLead.getName() != "")
			this.setTitle("AndSwtch - " + this.extLead.getName());
	}
	
	public void stopTimer(View v){
		if(countDownTimer!=null){
			countDownTimer.cancel();
		}
		startEndTimeCounter();
		setDelayTime(sumSeconds);
		onOffDelay.setText(R.string.onOffDelay);
		//set the timer in extlead timer to 0 to reset it
		extLead.sendState(this.onOffTag, false, 0);
		//set the end time in the db to now
		ppDbHelper.updatePowerPointRow(onOffTag, 0, hours, minutes,
				seconds);
		
	}
	
	private void startEndTimeCounter(){
		endTimeCounter = new CountDownTimer(60*60*1000,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				setEndTime(sumSeconds);
			}
			
			@Override
			public void onFinish() {
				TextView endTimeValueTV = (TextView) findViewById(R.id.endTimeValue);
				endTimeValueTV.setText("");
				TextView endTimeTV = (TextView) findViewById(R.id.endTime);
				endTimeTV.setText("");
				
			}
		}.start();
	}
	
	

	private void setEndTime(int sumSeconds) {
		TextView endTimeValueTV = (TextView) findViewById(R.id.endTimeValue);
		endTimeValueTV.setText(Util.secToFormattedEndTimeFromNow(sumSeconds));
	}
}