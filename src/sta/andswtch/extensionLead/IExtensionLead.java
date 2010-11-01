package sta.andswtch.extensionLead;

import java.net.InetAddress;

public interface IExtensionLead {
	
	public void init();
	
	public void sendUpdateMessage();

	public void sendState(int id, boolean on);
	public void sendState(int id, boolean on, int time);
	public void sendStateAll(boolean on);
	public void sendStateAll(boolean on, int time);
	 
	public void updateDatastructure(String response);
	public void errorAlert();

	public void setPowerPointName(int id, String name);
	public int getPowerPointsCount();
	public String getPowerPointName(int id);
	public boolean isPowerPointOn(int id);
	public boolean isPowerPointEnable(int id);
	 
	public void setConfig(InetAddress host, int portIn, int portOut, String user, String password);
	public InetAddress getHost();
	public int getPortIn();
	public int getPortOut();
	public String getUser();
	public String getPassword();
	
}
