package sta.andswtch.gui;

import java.util.ArrayList;
import java.util.List;

import sta.andswtch.R;
import sta.andswtch.extensionLead.ExtensionLead;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AndSwtch extends Activity {

	// private AlertDialog alert;
	private ExtensionLead extLead;
	private List<Button> buttons;
	private Handler handlerEvent = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: {
				// for testing! if you want to see the response string
				// alert.setMessage(extLead.getResponse());
				// alert.show();
				for (int i = 1; i < buttons.size(); i++) {
					checkState(buttons.get(i - 1));
				}
				break;
			}
			case 2: {
				Context context = getApplicationContext();
				CharSequence text = extLead.getErrorMessage();
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

		
		
		// will be removed later, it is just a method to create a alert dialog
		/*
		 * AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 * builder.setMessage("dialog"); builder.setCancelable(false);
		 * builder.setTitle("notifier"); builder.setPositiveButton("Done", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int id) { dialog.dismiss(); } });
		 * alert = builder.create();
		 */
	}

	private void init() {
		this.buttons = new ArrayList<Button>();
		this.buttons.add((Button) findViewById(R.id.Button01));
		this.buttons.add((Button) findViewById(R.id.Button02));
		this.buttons.add((Button) findViewById(R.id.Button03));
		this.buttons.add((Button) findViewById(R.id.Button04));
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.extLead.sendUpdateMessage();
	}

	public void onOff(View v) {
		String tagString = (String) v.getTag();
		if (tagString != null) {
			int tag = Integer.parseInt(tagString);
			if (tag == 0) {
				// this.extLead.sendStateAll(true);
				// just for testing the timer
				this.extLead.sendState(2, false, 2);

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
	
	public void showErrorMessage() {
		Message msg = new Message();
		msg.what = 2;
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
}