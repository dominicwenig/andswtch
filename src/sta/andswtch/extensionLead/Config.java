package sta.andswtch.extensionLead;

import sta.andswtch.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Config {

	private static final String TAG = Config.class.getName();
	
	Context context;
	SharedPreferences preferrences;

	
	public Config(Context context){
		this.context = context;
		preferrences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	private String getResourceString(int resID){
		return context.getString(resID);
	}
	
	private int intValueOfString(String s){
		int t = 0;
		try {
		t = Integer.parseInt (s);
		} catch (Exception E){
		//es war keine Zahl
		}
		return t;
	}
	
	public String getHost() {
		String response = preferrences.getString("IP", getResourceString(R.string.ps_ipaddress_def));
		
		Log.d(TAG, "Host is:" +response);
		return response;
	}

	public int getExtensionLeadSenderPort() {
		String senderPort= preferrences.getString("SenderPort", getResourceString(R.string.ps_senderport_def));
		Log.d(TAG, "Sender Port is:" +senderPort);
		return intValueOfString(senderPort);
	}

	public int getExtensionLeadReceiverPort() {
		String receiverPort = preferrences.getString("ReceiverPort", getResourceString(R.string.ps_receiverport_def));
		Log.d(TAG, "Receiver Port is:" +receiverPort);
		return intValueOfString(receiverPort);
	}

	public String getUser() {
		String response = preferrences.getString("Username", getResourceString(R.string.ps_username_def));
		Log.d(TAG, "Username is:" +response);
		return response;
	}

	public String getPassword() {
		String response = preferrences.getString("Password", getResourceString(R.string.ps_password_def));
		Log.d(TAG, "Password is:" +response);
		return response;
	}

}
