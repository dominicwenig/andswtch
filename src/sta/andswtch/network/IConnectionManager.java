package sta.andswtch.network;

public interface IConnectionManager {

	public void sendAndReceive(String command);

	public void updateDatastructure(String response);

	public void errorAlert();

}
