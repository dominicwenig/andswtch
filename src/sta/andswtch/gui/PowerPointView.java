package sta.andswtch.gui;

import sta.andswtch.R;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PowerPointView extends OptionsMenu {
	
	private int sec = 2;
	private TextView delayTime;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.powerpoint);
		
		this.init();
	}
	
	private void init() {
		this.delayTime = (TextView) findViewById(R.id.DelayTime);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		this.delayTime.setText("Delay: " + this.sec + " sec");
	}
	
	public void onOff(View v) {
		String tagString = (String) v.getTag();
		if (tagString != null) {
			int tag = Integer.parseInt(tagString);
			if (tag == 0) {
				//switch delayed
				//AndSwtch.extLead.sendState(2, false, this.sec);
			}
		}
	}
	
	public void decrease(View v) {
		if(this.sec > 0) {
			this.sec--;
			this.delayTime.setText("Delay: " + this.sec + " sec");
		}
	}
	
	public void increase(View v) {
		if(this.sec < 65535) {
			this.sec++;
			this.delayTime.setText("Delay: " + this.sec + " sec");
		}
	}
	
}
