package sta.andswtch.gui;

import java.util.ArrayList;
import java.util.List;

import sta.andswtch.R;
import sta.andswtch.extensionLead.ExtensionLead;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AndSwtch extends OptionsMenu {

	private ExtensionLead extLead;
	private List<ToggleButton> buttons;
	
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
		this.buttons = new ArrayList<ToggleButton>();
		this.buttons.add((ToggleButton) findViewById(R.id.Button01));
		this.buttons.add((ToggleButton) findViewById(R.id.Button02));
		this.buttons.add((ToggleButton) findViewById(R.id.Button03));
		
		for(int i = 0; i < this.extLead.getPowerPointsCount(); i++) {
			this.buttons.get(i).setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.extLead.sendUpdateMessage();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(0, 1, 0, R.string.om_refresh);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 1: {
				this.extLead.sendUpdateMessage();
				return true;
			}
			default: {
				return super.onOptionsItemSelected(item);
			}
		}
	}

	public void onOff(View v) {
		String tagString = (String) v.getTag();
		if (tagString != null) {
			int tag = Integer.parseInt(tagString);
			// tag 1 - 8 for each power point
			// tag 9 for all on and tag 10 for all off
			if (tag == 9) {
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

	private void checkState(ToggleButton btn) {
		if (this.extLead.isPowerPointOn(Integer.parseInt((String) btn.getTag()))) {
			this.setOn(btn);
		} else {
			this.setOff(btn);
		}
	}

	private void setOn(ToggleButton btn) {
		btn.setChecked(true);
		//btn.setBackgroundColor(Color.GREEN);
		//btn.setText("ON");
	}

	private void setOff(ToggleButton btn) {
		btn.setChecked(false);
		//btn.setBackgroundColor(Color.RED);
		//btn.setText("OFF");
	}
	
	public void switchToPowerPoint(View v) {
		Intent toLaunch = new Intent(v.getContext(), PowerPointView.class);
		//toLaunch.putExtra("Test", "x");
		startActivity(toLaunch);
	}
	
}
