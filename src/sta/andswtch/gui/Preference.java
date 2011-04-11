package sta.andswtch.gui;

import sta.andswtch.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preference extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencescreen);
	}

}
