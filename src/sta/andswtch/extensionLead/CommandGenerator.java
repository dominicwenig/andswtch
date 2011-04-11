package sta.andswtch.extensionLead;

import java.nio.ByteBuffer;
import java.util.List;

import android.util.Log;

public class CommandGenerator {

	private static final String TAG = CommandGenerator.class.getName();
	private static final String GET_STATUS = "wer da?";

	private Config config;

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

		// "St_[on|off][id][delay as 16bit integer][user][password]

		byte[] begin = "St_".getBytes();
		byte[] byteStatus = evaluateOnBoolean(on).getBytes();
		byte[] byteId = Integer.toString(id).getBytes();
		byte[] byteTime = intTo16BitInteger(time); // TODO change to evaluate
													// the string
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
		// mask the integer to have only the least 16 bits
		value = value & 65535;

		// store the least 2 bytes of the integer into a byte array
		byte[] response = new byte[] { (byte) (value >>> 8), (byte) value };

		Log.d(TAG,
				"the evaluated time is: (binary reprazentation of a 16 bit integer)"
						+ (Integer.toBinaryString((int) (response[0]))) + " "
						+ (Integer.toBinaryString((int) (response[1]))));

		return response;
	}

	public String generateStatusUpdateCommand() {
		// "wer da?"
		return GET_STATUS;
	}

	public byte[] generateSwitchAllCommand(boolean on,
			List<PowerPoint> powerPoints) {

		// "Sw[binary mask e.g. 11111111 (dec 127) for "all on"][user][password]

		int intPowerPointBinaryMask = 0;

		for (int i = powerPoints.size() - 1; i >= 0; i--) {
			PowerPoint p = powerPoints.get(i);
			// shift all the bits 1 to the left to add a value
			intPowerPointBinaryMask = intPowerPointBinaryMask << 1;
			// lsb is now 0

			if (p.isEnabled()) {
				// change lsb to 1 if pp is enabled and we want to switch on
				if (on) {
					intPowerPointBinaryMask += 1;
				}
			} else {
				// change lsb to 1 if pp is disabled and the current state is
				// enabled
				if (p.isOn()) {
					intPowerPointBinaryMask += 1;
				}
			}
		}

		Log.d(TAG,
				"The powerpointBinarymask is "
						+ Integer.toBinaryString(intPowerPointBinaryMask));

		byte powerPointBinaryMask = (byte) intPowerPointBinaryMask;

		ByteBuffer buf = ByteBuffer.allocate(100);

		buf.put("Sw".getBytes());
		buf.put(powerPointBinaryMask);
		buf.put(this.config.getUser().getBytes());
		buf.put(this.config.getPassword().getBytes());

		buf.flip();

		return buf.array();
	}
}
