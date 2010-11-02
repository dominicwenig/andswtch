package sta.andswtch.gui;

import sta.andswtch.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AndSwtch extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onOff(View v) {
		TextView tv;
		int id = v.getId();
		if (id == findViewById(R.id.Button01).getId()) {
			tv = (TextView) findViewById(R.id.TextView01);
			tv.setText("clicked pP01");
		} else if (id == findViewById(R.id.Button02).getId()) {
			tv = (TextView) findViewById(R.id.TextView02);
			tv.setText("clicked pP02");
		} else if (id == findViewById(R.id.Button03).getId()) {
			tv = (TextView) findViewById(R.id.TextView03);
			tv.setText("clicked pP03");
		} else if (id == findViewById(R.id.Button04).getId()) {
			tv = (TextView) findViewById(R.id.TextView04);
			tv.setText("clicked eL");
		}

	}

}