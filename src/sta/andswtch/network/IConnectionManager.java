package sta.andswtch.network;

public interface IConnectionManager {

	public void sendUpdateRequest();

	public void sendState(int id, boolean on, int time);

	public void sendState(int id, boolean on);

	public void sendAndReceive(String command);

	public void updateDatastructure(String response);

	public void errorAlert();

}
