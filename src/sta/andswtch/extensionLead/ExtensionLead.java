package sta.andswtch.extensionLead;

import java.util.ArrayList;
import java.util.List;

import sta.andswtch.gui.AndSwtch;
import sta.andswtch.network.ConnectionManager;

public class ExtensionLead implements IExtensionLead {

	private List<PowerPoint> powerPoints;
	private Config config;
	private ConnectionManager connectionManager;
	private ResponseProcessor responseProcessor;
	private AndSwtch andSwtch;
	private int powerPointsCount;

	// is only for prototype v0.0 :), will be removed
	private String response = "nothing";

	// test static final variables
	private static final int testPowerPointsCount = 3;
	private static final String testHost = "192.168.178.21";
	private static final int testExtensionLeadSenderPort = 1133;
	private static final int testExtensionLeadReceiverPort = 1132;
	private static final String testUser = "admin";
	private static final String testPassword = "anel";

	public ExtensionLead(AndSwtch andSwtch) {
		this.andSwtch = andSwtch;
		init();
	}

	private void addPowerPoint(int id, String name, boolean enable, boolean on) {
		this.powerPoints.add(new PowerPoint(id, name, enable, on));
	}

	public void errorAlert() {
		// TODO Auto-generated method stub

	}

	public String getHost() {
		return this.config.getHost();
	}

	public String getPassword() {
		return this.config.getPassword();
	}

	public int getPortIn() {
		return this.config.getExtensionLeadSenderPort();
	}

	public int getPortOut() {
		return this.config.getExtensionLeadReceiverPort();
	}

	public String getPowerPointName(int id) {
		return this.powerPoints.get(id - 1).getName();
	}

	public int getPowerPointsCount() {
		return this.powerPointsCount;
	}

	public String getUser() {
		return this.config.getUser();
	}

	public void init() {
		
		this.powerPoints = new ArrayList<PowerPoint>();
		this.powerPointsCount = testPowerPointsCount;
		
		this.config = new Config(testHost, testExtensionLeadSenderPort,
				testExtensionLeadReceiverPort, testUser, testPassword);
		this.connectionManager = new ConnectionManager(this.config, this);
		
		for (int id = 1; id <= this.powerPointsCount; id++) {
			this.addPowerPoint(id - 1, "Nr. " + id, true, false);
		}
		this.responseProcessor = new ResponseProcessor(this.powerPoints);

		this.sendUpdateMessage();
	}

	public boolean isPowerPointEnable(int id) {
		return this.powerPoints.get(id - 1).isEnabled();
	}

	public boolean isPowerPointOn(int id) {
		return this.powerPoints.get(id - 1).isOn();
	}

	public void switchState(int id) {
		boolean on = this.powerPoints.get(id - 1).isOn();
		
		this.connectionManager.sendState(id, !on);
	}

	public void sendState(int id, boolean on, int time) {
		this.connectionManager.sendState(id, on, time);
	}

	public void sendStateAll(boolean on) {
		
	}

	public void sendStateAll(boolean on, int time) {

	}

	public void sendUpdateMessage() {
		this.connectionManager.sendUpdateRequest();
	}

	public void setConfig(String host, int portIn, int portOut, String user,
			String password) {
		if (this.config == null) {
			this.config = new Config(host, portIn, portOut, user, password);
		} else {
			this.config.setConfig(host, portIn, portOut, user, password);
		}
	}

	public void setPowerPointName(int id, String name) {
		this.powerPoints.get(id - 1).setName(name);
	}

	public void updateDatastructure(String response) {
		if (this.responseProcessor == null) {
			this.responseProcessor = new ResponseProcessor(this.powerPoints);
		}
		this.responseProcessor.processResponse(response);


		// for testing purpose we create a message box with the response string
		this.response = response;
		this.andSwtch.updateActivity();		
	}

	public String getResponse() {
		return response;
	}
	
}
