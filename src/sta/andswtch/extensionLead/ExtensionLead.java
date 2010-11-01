package sta.andswtch.extensionLead;

import java.net.InetAddress;
import java.util.List;


import sta.andswtch.network.ConnectionManager;

public class ExtensionLead implements IExtensionLead {
	
	private List<PowerPoint> powerPoints; 
	private ConnectionManager connectionManager;
	
	public ExtensionLead() {
		init();
	}
	
	private void addPowerPoint(int id, String name, boolean enable, 
			boolean on, ConnectionManager connectionManager) {
		
	}

	@Override
	public void errorAlert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InetAddress getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPortIn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPortOut() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPowerPointName(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPowerPointsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPowerPointEnable(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPowerPointOn(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendState(int id, boolean on) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendState(int id, boolean on, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendStateAll(boolean on) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendStateAll(boolean on, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendUpdateMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConfig(InetAddress host, int portIn, int portOut,
			String user, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPowerPointName(int id, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDatastructure(String response) {
		// TODO Auto-generated method stub
		
	}
		
}
