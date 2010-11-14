package sta.andswtch.network;

import java.io.IOException;
import java.nio.ByteBuffer;

import sta.andswtch.extensionLead.Config;
import sta.andswtch.extensionLead.ExtensionLead;
import android.util.Log;

public class ConnectionManager implements IConnectionManager {

	private Config config;
	private Sender sender;
	private Receiver receiver;
	private ExtensionLead extLead;

	private static final String TAG = ConnectionManager.class.getName();
	private static final String GET_STATUS = "wer da?";
	private static final String ON = "on";
	private static final String OFF = "off";

	public ConnectionManager(Config config, ExtensionLead extLead) {
		this.config = config;
		this.extLead = extLead;
		this.sender = new Sender();
		this.receiver = new Receiver(this);
	}

	public void errorAlert() {
		Log.e(TAG, "Server timed out...");
	}

	public void sendUpdateRequest() {
		this.sendAndReceive(GET_STATUS);
	}

	public void sendAndReceive(String command) {

		Log.v(TAG, "try to send a packet with the command: "
				+ command + "to " + config.getHost() + ":"
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
		
		Log.d(TAG, "start a server to receive the response");
		try {
			this.receiver.start(this.config.getExtensionLeadSenderPort());
		} catch (IOException e) {
			Log.e(TAG, "Error: could not start the server, exception: "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void updateDatastructure(String response) {
		this.extLead.updateDatastructure(response);
	}
	
	public void sendState(int id, boolean on) {
		this.sendState(id, on, 0);
	}
	
	public void sendState(int id, boolean on, int time) {
		String status;
		if (on) {
			status = ON;
		} else {
			status = OFF;
		}
		
		if(time != 0) {
			
			ByteBuffer buf = intToUnsignedShort(time);
			
			Log.e(TAG, "BinaryString: " + Integer.toBinaryString(Integer.valueOf(time)));
			Log.e(TAG, "ByteBuffer: " + buf.get(1) + " " + buf.get(0));
			
			sendAndReceive("St_" + status + Integer.toString(id) + 0x00 + 0x00
					+ this.config.getUser() + this.config.getPassword());
		} else {
			sendAndReceive("Sw_" + status + Integer.toString(id)
					+ this.config.getUser() + this.config.getPassword());
		}
		
	}
	
	private ByteBuffer intToUnsignedShort(int time) {
		String timeString = Integer.toBinaryString(time);
		StringBuffer timeBuf = new StringBuffer(timeString);
		
		while(timeBuf.length() < 16) {
			timeBuf.append('0');
	    }
		
	    ByteBuffer byteBuffer = ByteBuffer.allocate(2);
	    byte eachByte;

	    for (int i = 0; i < 2; i++) {
	    	eachByte = 0x00;
	        for (int bitNumber = 7; bitNumber >= 0; bitNumber--) {
	        	if (timeBuf.charAt((i * 8) + (7 - bitNumber)) == '1') {
	               // sets the bit at the position bitNumber to 1
	               eachByte |= (0x01 << bitNumber);
	        	}
	        }
	        byteBuffer.put((byte) eachByte);
	    }
	    
	    return byteBuffer;
	}
}
