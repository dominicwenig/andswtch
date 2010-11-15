package sta.andswtch.network;

import java.io.IOException;

import sta.andswtch.extensionLead.Config;
import sta.andswtch.extensionLead.ExtensionLead;
import android.util.Log;

public class ConnectionManager implements IConnectionManager {
	
	private static final String TAG = ConnectionManager.class.getName();
	
	private Config config;
	private Sender sender;
	private Receiver receiver;
	private ExtensionLead extLead;



	public ConnectionManager(Config config, ExtensionLead extLead) {
		this.config = config;
		this.extLead = extLead;
		this.sender = new Sender();
		this.receiver = new Receiver(this);
	}

	public void errorAlert() {
		Log.e(TAG, "Server timed out...");
	}

	private void send(byte [] command){
		
		Log.v(TAG, "try to send a packet with the command: "
				+ new String(command) + "to " + config.getHost() + ":"
				+ config.getExtensionLeadReceiverPort());
		try {
			this.sender.send(this.config.getHost(),
					this.config.getExtensionLeadReceiverPort(), command);
		} catch (IOException e) {
			// TODO better error handling
			e.printStackTrace();
			Log.e(TAG, "failed to send the packet with the following command : "
					+ command + "to " + config.getHost() + ":"
					+ config.getExtensionLeadReceiverPort());
		}
		
	}
	
	private void startReceive(){
		Log.d(TAG, "start a server to receive the response");
		try {
			this.receiver.start(this.config.getExtensionLeadSenderPort());
		} catch (IOException e) {
			Log.e(TAG, "Error: could not start the server, exception: "
					+ e.getMessage());
			e.printStackTrace();
		}
		
	}
	/**
	 * send a string to the extensionlead
	 * 
	 * @param command
	 */
	public void sendAndReceive(String command) {
		send(command.getBytes());
		startReceive();
	}
	
	/**
	 * send a command as byte [] to the extension lead. this is necessary because of the fucking bad protocol of the extension lead!
	 * 
	 */
	public void sendAndReceive(byte[] command) {
		send(command);
		startReceive();
	}
	
	
	/**
	 * send the response to the ExtensionLead object to update the datastructure.
	 */
	public void updateDatastructure(String response) {
		this.extLead.updateDatastructure(response);
	}
	

}
