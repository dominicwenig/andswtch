package sta.andswtch.network;

public interface IConnectionManager {

	public void getUpdate();

	public void sendReceive(String command);

	public void updateDatastructure(String response);

	public void errorAlert();
	


}
