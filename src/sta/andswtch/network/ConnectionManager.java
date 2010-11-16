package sta.andswtch.network;

import java.io.IOException;
import java.net.SocketException;

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

	public void errorAlert(String errorMessage) {
		extLead.errorOccured(errorMessage);
	}

	private void send(byte[] command) throws IOException {

		Log.v(TAG,
				"try to send a packet with the command: " + new String(command)
						+ "to " + config.getHost() + ":"
						+ config.getExtensionLeadReceiverPort());
			this.sender.send(this.config.getHost(),
					this.config.getExtensionLeadReceiverPort(), command);
		



	}

	private void startReceive() {
		Log.d(TAG, "start a server to receive the response");
		try {
			this.receiver.start(this.config.getExtensionLeadSenderPort());
		} catch (IOException e) {
			errorAlert("failed to start server");
			Log.e(TAG,
					"Error: could not start the server, exception: "
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
		sendAndReceive(command.getBytes());
		
	}

	/**
	 * send a command as byte [] to the extension lead. this is necessary
	 * because of the fucking bad protocol of the extension lead!
	 * 
	 */
	public void sendAndReceive(byte[] command) {
		try {
			send(command);
			startReceive();
		}catch (IOException e) {
			e.printStackTrace();
			errorAlert("failed to send the command, are you connected to a network?");
			Log.e(TAG,
					"failed to send the packet with the following command : "
							+ new String(command) + "to " + config.getHost() + ":"
							+ config.getExtensionLeadReceiverPort()+" error message: "+ e.getMessage());
		}
	}

	/**
	 * send the response to the ExtensionLead object to update the
	 * datastructure.
	 */
	public void updateDatastructure(String response) {
		this.extLead.updateDatastructure(response);
	}

}
