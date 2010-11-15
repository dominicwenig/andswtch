package sta.andswtch.extensionLead;

import java.nio.ByteBuffer;

import android.util.Log;

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
	private String evaluateOnBoolean(boolean on){
		if(on){
			return "on";
		}
		else {
			return "off";
		}
	}
	
	public String generateSwitchCommand(int id, boolean on) {
		
		//"Sw_[on|off][id][user][password]"
		return ("Sw_"+evaluateOnBoolean(on)+Integer.toString(id)+this.config.getUser()+this.config.getPassword());
	}
	
	public String generateSwitchDelayedCommand(int id, boolean on, int time) {
		
		/*
		 * "St_[on|off][id][time][user][password]"
		 * time as 16 bit integer!!
		 */
		return ("St_"+evaluateOnBoolean(on)+id+evalueateTime(time)+this.config.getUser()+this.config.getPassword());
	}
	

	
	
	/**
	 * evaluates the time and converts it to a 16 bit integer
	 * 
	 * @param time
	 * @return
	 */
	private String evalueateTime(int time) {
		//maybe string has to be changed to byte[] ---> and then we have to work with byte array all the time!!!
		
		ByteBuffer buf = intToUnsignedShort(time);
		
		Log.e(TAG, "BinaryString: " + Integer.toBinaryString(Integer.valueOf(time)));
		Log.e(TAG, "ByteBuffer: " + buf.get(1) + " " + buf.get(0));
		
		//this will not work, its just to get it compile right now
		return (buf.toString());

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
	
	
	public String generateStatusUpdateCommand() {
		
		//"wer da?"
		return GET_STATUS;
	}
	
}
