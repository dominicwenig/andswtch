package sta.andswtch.network;

import java.net.InetAddress;

public class ConnectionManager implements IConnectionManager {
	
	private InetAddress host;
	private int portIn;
	private int portOut;
	private String user;
	private String password;
	
	public ConnectionManager(InetAddress host, int portIn, int portOut) {
		
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
	public String getUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendReceive(String command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfig(InetAddress host, int portIn, int portOut) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDatastructure(String response) {
		// TODO Auto-generated method stub
		
	}
	
}
