package sta.andswtch.gui;

import java.util.ArrayList;
import java.util.List;

import sta.andswtch.R;
import sta.andswtch.extensionLead.ExtensionLead;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AndSwtch extends OptionsMenu {

	private ExtensionLead extLead;
	private List<Button> buttons;
	private int sec = 2;
	private TextView delaySec;
	
	private Handler handlerEvent = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1: {
					for (int i = 1; i <= buttons.size(); i++) {
						checkState(buttons.get(i - 1));
					}
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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);

		// create the extension lead
		this.extLead = new ExtensionLead(this);
		this.init();

	}

	private void init() {
		this.buttons = new ArrayList<Button>();
		this.buttons.add((Button) findViewById(R.id.Button01));
		this.buttons.add((Button) findViewById(R.id.Button02));
		this.buttons.add((Button) findViewById(R.id.Button03));
		
		this.delaySec = (TextView) findViewById(R.id.TextView04);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.extLead.sendUpdateMessage();
		this.delaySec.setText("Delay: " + this.sec + " sec");
	}

	public void onOff(View v) {
		String tagString = (String) v.getTag();
		if (tagString != null) {
			int tag = Integer.parseInt(tagString);
			if (tag == 0) {
				//switch delayed
				this.extLead.sendState(2, false, this.sec);

			} else if (tag == 9) {
				this.extLead.sendStateAll(true);
			} else if (tag == 10) {
				this.extLead.sendStateAll(false);
			} else {
				this.extLead.switchState(tag);
			}
		}
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

	private void checkState(Button btn) {
		if (this.extLead
				.isPowerPointOn(Integer.parseInt((String) btn.getTag()))) {
			this.setOn(btn);
		} else {
			this.setOff(btn);
		}
	}

	private void setOn(Button btn) {
		btn.setBackgroundColor(Color.GREEN);
		btn.setText("ON");
	}

	private void setOff(Button btn) {
		btn.setBackgroundColor(Color.RED);
		btn.setText("OFF");
	}
	
	public void decrease(View v) {
		if(this.sec > 0) {
			this.sec--;
			this.delaySec.setText("Delay: " + this.sec + " sec");
		}
	}
	
	public void increase(View v) {
		if(this.sec < 65535) {
			this.sec++;
			this.delaySec.setText("Delay: " + this.sec + " sec");
		}
	}
	
}
