package sta.andswtch.gui;

import sta.andswtch.R;
import sta.andswtch.extensionLead.ExtensionLead;
import sta.andswtch.extensionLead.ExtensionLeadManager;
import sta.andswtch.gui.timepicker.TimePicker;
import sta.andswtch.gui.timepicker.TimePicker.OnTimeChangedListener;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PowerPointView extends OptionsMenu implements IAndSwtchViews {
	
	private ExtensionLeadManager extLeadManager;
	private ExtensionLead extLead;
	private TextView name;
	private EditText hoursEt;
	private EditText minutesEt;
	private EditText secondsEt;
	private TextView delayTime;
	private ToggleButton onOff;
	private int onOffTag;
	private int sumSeconds = 0;
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;
	
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
		}
	};
	

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.powerpoint);
		
		this.extLeadManager = ExtensionLeadManager.getInstance(this);
		this.extLead = this.extLeadManager.getExtLeadFromView(this);
		this.init();
		this.checkState();
	}
	
	private void init() {
		this.name = (TextView) findViewById(R.id.ppName);
		this.delayTime = (TextView) findViewById(R.id.DelayTime);
		this.onOff = (ToggleButton) findViewById(R.id.onOff);

		Bundle extra = getIntent().getExtras();
		String tagString = extra.getString("powerPoint");
		if (tagString != null) {
			this.onOff.setTag(tagString);
			this.onOffTag = Integer.parseInt(tagString);
			this.name.setText(this.extLead.getPowerPointName(this.onOffTag));
		}
		
		TimePicker timePicker= (TimePicker) findViewById(R.id.timePicker);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			public void onTimeChanged(TimePicker view, int hour, int minute, int second) {
				hours = hour;
				minutes = minute;
				seconds = second;
				sumSeconds = seconds + minutes*60 + hours * 60*60;
				setDelayTime(sumSeconds);
				
			}
		});
		
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setDelayTime(this.sumSeconds);
	}
	
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
				//switch delayed
				this.extLead.sendState(this.onOffTag, false, this.sumSeconds);
				startTimer();
			}
			else {
				this.extLead.switchState(tag);
			}
		}

		
		
		
	}
	
	public void startTimer(){
		new CountDownTimer(sumSeconds*1000, 1000) {

		     public void onTick(long millisUntilFinished) {
		    	 setDelayTime((int)(millisUntilFinished/1000));
		     }

		     public void onFinish() {
		         setDelayTime(sumSeconds);
		         extLead.sendUpdateMessage();
		     }
		  }.start();
	}
	
	public void setDelayTime(int seconds) {
		int hour = seconds/(60*60);
		int min = (seconds%(60*60)/60);
		int sec = (seconds%(60));
		
		this.delayTime.setText("Delay: " + hour+ ":"+ min +":"+ sec);
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
}
