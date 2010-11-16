package sta.andswtch.extensionLead;

import java.nio.ByteBuffer;

public class CommandGenerator {

	private Config config;

	private static final String TAG = CommandGenerator.class.getName();

	private static final String GET_STATUS = "wer da?";

	public CommandGenerator(Config config) {
		super();
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

		byte[] begin = "St_".getBytes();
		byte[] byteStatus = evaluateOnBoolean(on).getBytes();
		byte[] byteId = Integer.toString(id).getBytes();
		byte[] byteTime = {0,3}; //TODO change to evaluate the string
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

	private byte[] intToUnsignedShort(int time) {

		String timeString = Integer.toBinaryString(time);
		StringBuffer timeBuf = new StringBuffer(timeString);

		while (timeBuf.length() < 16) {
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

		return byteBuffer.array();
	}

	public String generateStatusUpdateCommand() {

		// "wer da?"
		return GET_STATUS;
	}

	public byte[] generateSwitchAllCommand(boolean on) {
		
		byte powerPointBinaryMask;
		
		if(on){
			powerPointBinaryMask = 127;
		}
		else {
			powerPointBinaryMask =0;
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
