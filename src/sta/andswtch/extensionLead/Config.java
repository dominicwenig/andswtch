package sta.andswtch.extensionLead;

import sta.andswtch.R;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Config extends Application {

	private static final String TAG = Config.class.getName();
	
	private Context context;
	private SharedPreferences preferences;
	
	public Config(Context context){
		this.context = context;
		this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	private int intValueOfString(String s){
		int t = 0;
		try {
			t = Integer.parseInt (s);
		} catch (Exception E) {
			//es war keine Zahl
		}
		return t;
	}
	
	public String getHost() {
		String response = this.preferences.getString("IP", this.context.getString(R.string.ps_ipaddress_def));
		Log.d(TAG, "Host is:" + response);
		return response;
	}

	public int getApplicationSenderPort() {
		String senderPort= this.preferences.getString("SenderPort", this.context.getString(R.string.ps_senderport_def));
		Log.d(TAG, "Sender Port is:" + senderPort);
		return intValueOfString(senderPort);
	}

	public int getApplicationReceiverPort() {
		String receiverPort = this.preferences.getString("ReceiverPort", this.context.getString(R.string.ps_receiverport_def));
		Log.d(TAG, "Receiver Port is:" + receiverPort);
		return intValueOfString(receiverPort);
	}

	public String getUser() {
		String response = this.preferences.getString("Username", this.context.getString(R.string.ps_username_def));
		Log.d(TAG, "Username is:" + response);
		return response;
	}

	public String getPassword() {
		String response = this.preferences.getString("Password", this.context.getString(R.string.ps_password_def));
		Log.d(TAG, "Password is:" + response);
		return response;
	}

}
