package sta.andswtch.network;

import java.net.InetAddress;

public interface IConnectionManager {
	
	public void setConfig(InetAddress host, int portIn, int portOut);
	 
	public InetAddress getHost();
	public int getPortIn();
	public int getPortOut();
	public String getUser();
	public String getPassword(); 

	public String getUpdate();
	public String sendReceive(String command);

	public void updateDatastructure(String response);

	public void errorAlert();
	
}
