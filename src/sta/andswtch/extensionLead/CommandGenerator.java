package sta.andswtch.extensionLead;

import java.nio.ByteBuffer;

import android.util.Log;

public class CommandGenerator {

	private Config config;
	private static final String TAG = CommandGenerator.class.getName();
	private static final String GET_STATUS = "wer da?";

	public CommandGenerator(Config config) {
		this.config = config;
	}

	/**
	 * evaluates the boolean
	 * 
	 * @param on
	 * @return "on" if boolean is true, "off" otherwise
	 */
	private String evaluateOnBoolean(boolean on) {
		if (on) {
			return "on";
		} else {
			return "off";
		}
	}

	public String generateSwitchCommand(int id, boolean on) {

		// "Sw_[on|off][id][user][password]"
		return ("Sw_" + evaluateOnBoolean(on) + Integer.toString(id)
				+ this.config.getUser() + this.config.getPassword());
	}

	public byte[] generateSwitchDelayedCommand(int id, boolean on, int time) {

		// TODO this is very bad :D - change that
		// TODO evaluate, if only switching off is supported by the
		// anel-extensionLead and take out the boolean if necessary
		
		// "SW_[on|off][id][delay as 16bit integer][user][password]

		byte[] begin = "St_".getBytes();
		byte[] byteStatus = evaluateOnBoolean(on).getBytes();
		byte[] byteId = Integer.toString(id).getBytes();
		byte[] byteTime = intTo16BitInteger(time); //TODO change to evaluate the string
		byte[] user = this.config.getUser().getBytes();
		byte[] password = this.config.getPassword().getBytes();

		ByteBuffer buf = ByteBuffer.allocate(500);

		buf.put(begin);
		buf.put(byteStatus);
		buf.put(byteId);
		buf.put(byteTime);
		buf.put(user);
		buf.put(password);

		buf.flip();

		return buf.array();

	}

	private byte[] intTo16BitInteger(int value) {
		//mask the integer to have only the least 16 bits
		value = value & 65535;
		
		//store the least 2 bytes of the integer into a byte array
		byte[] response = new byte[] {
                (byte)(value >>> 8),
                (byte)value};
		
		Log.d(TAG, "the evaluated time is: (binary reprazentation of a 16 bit integer)" +
				(Integer.toBinaryString((int)(response[0]))) + " " +
				(Integer.toBinaryString((int)(response[1]))));
		
		return response;
	}

	public String generateStatusUpdateCommand() {
		// "wer da?"
		return GET_STATUS;
	}

	public byte[] generateSwitchAllCommand(boolean on) {
		
		// "Sw[binary mask e.g. 11111111 (dec 127) for "all on"][user][password]
		
		byte powerPointBinaryMask;
		
		if(on) {
			powerPointBinaryMask = 127;
			// 11111111 --> all on
		}
		else {
			powerPointBinaryMask = 0;
			// 00000000 --> all off
		}
		
		ByteBuffer buf = ByteBuffer.allocate(100);
		
		buf.put("Sw".getBytes());
		buf.put(powerPointBinaryMask);
		buf.put(this.config.getUser().getBytes());
		buf.put(this.config.getPassword().getBytes());
		
		buf.flip();
		
		return buf.array();
	}
}
