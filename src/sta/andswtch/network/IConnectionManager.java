package sta.andswtch.network;

public interface IConnectionManager {

	public void setConfig(String host, int portIn, int portOut, String user,
			String password);

	public String getHost();

	public int getPortIn();

	public int getPortOut();

	public String getUser();

	public String getPassword();

	public String getUpdate();

	public String sendReceive(String command);

	public void updateDatastructure(String response);

	public void errorAlert();

}
