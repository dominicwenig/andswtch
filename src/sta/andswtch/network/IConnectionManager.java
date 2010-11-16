package sta.andswtch.network;

public interface IConnectionManager {

	public void sendAndReceive(byte[] command);

	public void updateDatastructure(String response);

	public void errorAlert(String errorMessage);

}
