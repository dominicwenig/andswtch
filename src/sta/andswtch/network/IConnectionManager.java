package sta.andswtch.network;

public interface IConnectionManager {

	public abstract void errorAlert(int errorMessageId);

	public abstract void sendWithoutReceive(byte[] command);

	/**
	 * send a string to the extensionlead
	 * 
	 * @param command
	 */
	public abstract void sendAndReceive(String command);

	/**
	 * send a command as byte [] to the extension lead. this is necessary
	 * because of the bad protocol of the extension lead!
	 * 
	 */
	public abstract void sendAndReceive(byte[] command);

	/**
	 * send the response to the ExtensionLead object to update the
	 * data structure.
	 */
	public abstract void updateDatastructure(String response);

}