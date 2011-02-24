package sta.andswtch.gui;

import sta.andswtch.R;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OptionsMenu extends Activity {
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionsmenu, menu);
		super.onCreateOptionsMenu(menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.settings: {
				Intent toLaunch = new Intent(this, Preference.class);
        		startActivity(toLaunch);
				return true;
			}
			case R.id.exit: {
				// works but recommended is to let Android worry about the memory management 
				//android.os.Process.killProcess(android.os.Process.myPid());
				moveTaskToBack(true);
			}
	    	default: {
	    		return super.onOptionsItemSelected(item);
	    	}
	    }
	}
	
}
