package sta.andswtch.gui;

import sta.andswtch.R;
import sta.andswtch.extensionLead.ExtensionLead;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class PowerPointView extends OptionsMenu {
	
	private ExtensionLead extLead;
	private TextView name;
	private EditText hoursEt;
	private EditText minutesEt;
	private EditText secondsEt;
	private TextView delayTime;
	private ToggleButton onOff;
	private int sec = 0;
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.powerpoint);
		
		this.init();
	}
	
	private void init() {
		this.name = (TextView) findViewById(R.id.ppName);
		this.delayTime = (TextView) findViewById(R.id.DelayTime);
		this.onOff = (ToggleButton) findViewById(R.id.onOff);
		this.hoursEt = (EditText) findViewById(R.id.hoursEt);
		this.minutesEt = (EditText) findViewById(R.id.minutesEt);
		this.secondsEt = (EditText) findViewById(R.id.secondsEt);

		Bundle extra = getIntent().getExtras();
		String tagString = extra.getString("powerPoint");
		if (tagString != null) {
			this.onOff.setTag(tagString);
			//int tag = Integer.parseInt(tagString);
			//this.name.setText(this.extLead.getPowerPointName(tag));
			this.name.setText(tagString);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//this.delayTime.setText("Delay: " + this.sec + " sec");
	}
	
	public void onOff(View v) {
		String tagString = (String) v.getTag();
		if (tagString != null) {
			int tag = Integer.parseInt(tagString);
			if (tag == 0) {
				//switch delayed
				//this.extLead.sendState(this.onOff.getTag(), false, this.sec);
			}
			else {
				//this.extLead.switchState(tag);
			}
		}
	}
	
	public void increase(View v) {
		int tmpSec = this.sec;
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
			this.sec = tmpSec;
			this.delayTime.setText("Delay: " + this.sec + " sec");
		}
	}
	
	public void decrease(View v) {
		int tmpSec = this.sec;
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
			this.sec = tmpSec;
			this.delayTime.setText("Delay: " + this.sec + " sec");
		}
	}
	
}
