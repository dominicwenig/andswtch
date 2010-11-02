package sta.andswtch.network;

public interface IConnectionManager {
	
	public String getUpdate();
	
	public String sendReceive(String command);

	public void updateDatastructure(String response);

	public void errorAlert();

}
