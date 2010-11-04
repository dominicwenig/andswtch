package sta.andswtch.network;

import java.io.IOException;

import sta.andswtch.extensionLead.Config;
import sta.andswtch.extensionLead.ExtensionLead;
import sta.andswtch.gui.AndSwtch;
import android.content.ReceiverCallNotAllowedException;
import android.util.Log;

public class ConnectionManager implements IConnectionManager {

	private Config config;
	private Sender sender;
	private Receiver receiver;
	private ExtensionLead extLead;
	

	
	private static final String TAG = ConnectionManager.class.getName();

	
	private static final String GET_STATUS = "wer da?";

	public ConnectionManager(Config config, ExtensionLead extLead) {
		this.config = config;
	    this.extLead = extLead;
	    
		sender = new Sender();
		receiver = new Receiver(this);
	}

	public void errorAlert() {
		Log.e(TAG, "Server timed out...");

	}

	public void getUpdate() {
		sendReceive(GET_STATUS);
	}

	public void sendReceive(String command) {
		
		Log.v(TAG, "try to send a packet with the command: " +command + "to " + config.getHost()+":" + config.getPortIn());
		try {
			sender.send(config.getHost(), config.getPortIn(), command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "failed to send the packet with the following command : "+command + 
					"to " + config.getHost()+":" + config.getPortIn());
		}
		
		Log.d(TAG, "start a server to receive the response");
		try {
			receiver.start(config.getPortOut());
		} catch (IOException e) {
			Log.e(TAG, "Error: could not start the server, exception: "+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void updateDatastructure(String response) {
		
		extLead.updateDatastructure(response);
	}

	

}
