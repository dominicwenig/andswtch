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
	private int sumSeconds = 1;
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 1;
	
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
		this.delayTime.setText("Delay: " + this.sumSeconds + " sec");
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
		         delayTime.setText("Delay: " + millisUntilFinished / 1000 + " sec");
		     }

		     public void onFinish() {
		         delayTime.setText("done!");
		         extLead.sendUpdateMessage();
		     }
		  }.start();
	}
	
	public void setDelayTime(int seconds) {
		this.delayTime.setText("Delay: " + seconds + " sec");
	}
	
	
	
	
	public void increase(View v) {
		int tmpSec = this.sumSeconds;
		if(v.getTag().equals("h_i")) {
			tmpSec += 60*60;
		}
		else if(v.getTag().equals("m_i")) {
			tmpSec += 60;
		}
		else if(v.getTag().equals("s_i")) {
			tmpSec++;
		}
		if(tmpSec < 65535) {
			if(v.getTag().equals("h_i")) {
				this.hours++;
				this.hoursEt.setText(""+this.hours);
			}
			else if(v.getTag().equals("m_i")) {
				this.minutes++;
				this.minutesEt.setText(""+this.minutes);
			}
			else if(v.getTag().equals("s_i")) {
				this.seconds++;
				this.secondsEt.setText(""+this.seconds);
			}
			this.sumSeconds = tmpSec;
			this.delayTime.setText("Delay: " + this.sumSeconds + " sec");
		}
	}
	
	public void decrease(View v) {
		int tmpSec = this.sumSeconds;
		if(v.getTag().equals("h_d")) {
			tmpSec -= 60*60;
		}
		else if(v.getTag().equals("m_d")) {
			tmpSec -= 60;
		}
		else if(v.getTag().equals("s_d")) {
			tmpSec--;
		}
		if(tmpSec > 0) {
			if(v.getTag().equals("h_d")) {
				this.hours--;
				this.hoursEt.setText(""+this.hours);
			}
			else if(v.getTag().equals("m_d")) {
				this.minutes--;
				this.minutesEt.setText(""+this.minutes);
			}
			else if(v.getTag().equals("s_d")) {
				this.seconds--;
				this.secondsEt.setText(""+this.seconds);
			}
			this.sumSeconds = tmpSec;
			this.delayTime.setText("Delay: " + this.sumSeconds + " sec");
		}
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
