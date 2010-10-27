package dhbw.sta.andswtch;

import java.net.InetAddress;
import java.util.List;

public class ExtensionLead {
	
	private List<PowerPoint> powerPoints; 
	private ConnectionManager connectionManager;
	
	public void init() {
	
	}
	
	private void addPowerPoint(int id, String name, boolean enable, boolean on, ConnectionManager connectionManager) {
		
	}
	
	public void update() {
		
	}
	
	public void changeEnabled(int id, boolean enabled) {
		
	}
	
	public void changeState(int id, boolean on) {
		
	}
	
	public void changeState(int id, boolean on, int time){
		
	}
	
	public void changeStateAll(boolean on) {
		
	}
	
	public void changeStateAll(boolean on, int time) {
		
	}
	
	public void setPowerPointName(int id, String name) {
		
	}
	
	public int getPowerPointsCount() {
		return 0;
	}
	
	public String getPowerPointName(int id) {
		return null;	
	}
	
	public boolean isPowerPointOn(int id) {
		return false;
	}
	
	public boolean isPowerPointEnable(int id) {
		return false;
	}
	
	public void setConfig(InetAddress host, int portIn, int portOut, String user, String password) {
		
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
	
}
