package sta.andswtch.network;

import java.io.IOException;

import sta.andswtch.extensionLead.Config;
import sta.andswtch.extensionLead.ExtensionLead;
import android.util.Log;

public class ConnectionManager implements IConnectionManager {

	private static final String TAG = ConnectionManager.class.getName();
	
	private ExtensionLead extLead;
	private Config config;
	private Sender sender;
	private Receiver receiver;

	public ConnectionManager(Config config, ExtensionLead extLead) {
		this.config = config;
		this.extLead = extLead;

		this.sender = new Sender();
		this.receiver = new Receiver(this);
	}

	public void errorAlert(String errorMessage) {
		this.extLead.errorOccured(errorMessage);
	}

	private void send(byte[] command) throws IOException {

		Log.v(TAG,
				"try to send a packet with the command: " + new String(command)
						+ "to " + config.getHost() + ":"
						+ config.getApplicationSenderPort());
		this.sender.send(this.config.getHost(),
				this.config.getApplicationSenderPort(), command);
	}

	public void sendWithoutReceive(byte[] command) {
		try {
			this.send(command);
		} catch (IOException e) {
			e.printStackTrace();
			this.errorAlert("failed to send the command, are you connected to a network?");
			Log.e(TAG,
					"failed to send the packet with the following command : "
							+ new String(command) + "to " + config.getHost()
							+ ":" + config.getApplicationSenderPort()
							+ " error message: " + e.getMessage());
		}
	}

	private void startReceive() {
		Log.d(TAG, "start a server to receive the response");
		try {
			this.receiver.start(this.config.getApplicationReceiverPort());
		} catch (IOException e) {
			this.errorAlert("failed to start server");
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
	 * because of the bad protocol of the extension lead!
	 * 
	 */
	public void sendAndReceive(byte[] command) {
		try {
			this.startReceive();
			this.send(command);
		} catch (IOException e) {
			e.printStackTrace();
			this.errorAlert("failed to send the command, are you connected to a network?");
			Log.e(TAG,
					"failed to send the packet with the following command : "
							+ new String(command) + "to " + config.getHost()
							+ ":" + config.getApplicationSenderPort()
							+ " error message: " + e.getMessage());
		}
	}

	/**
	 * send the response to the ExtensionLead object to update the
	 * data structure.
	 */
	public void updateDatastructure(String response) {
		this.extLead.updateDatastructure(response);
	}

}
