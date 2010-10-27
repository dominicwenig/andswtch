package dhbw.sta.andswtch;

import java.net.InetAddress;

public class ConnectionManager {
	
	private InetAddress host;
	private int portIn;
	private int portOut;
	private String user;
	private String password;
	
	public void ConnectionManager(InetAddress host, int portIn, int portOut) {
		
	}
	
	public void setConfig(InetAddress host, int portIn, int portOut) {
		
	}
	
	public InetAddress getHost() {
		return null;
	}
	
	public int getPortIn() {
		return 0;
	}
	
	public int getPortOut() {
		return 0;
	}
	
	public String getUser() {
		return null;
	}
	
	public String getPassword() {
		return null;
	}
	
	public String getUpdate() {
		return null;
	}
	
	public void send(String command) {
		
	}
	
	public String sendReceive(String command) {
		return null;
	}
	
}
