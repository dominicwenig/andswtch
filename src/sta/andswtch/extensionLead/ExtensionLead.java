package sta.andswtch.extensionLead;

import java.util.List;

import sta.andswtch.network.ConnectionManager;
import sta.andswtch.network.ResponseProcessor;

public class ExtensionLead implements IExtensionLead {

	private List<PowerPoint> powerPoints;
	private Config config;
	private ConnectionManager connectionManager;
	private ResponseProcessor responseProcessor;
	private int powerPointsCount;

	// test static final variables
	private static final int testPowerPointsCount = 3;
	private static final String testHost = "127.0.0.1";
	private static final int testPortIn = 1133;
	private static final int testPortOut = 1132;
	private static final String testUser = "admin";
	private static final String testPassword = "anel";

	public ExtensionLead() {
		init();
	}

	private void addPowerPoint(int id, String name, boolean enable, boolean on) {
		this.powerPoints.add(new PowerPoint(id, name, enable, on));
	}

	@Override
	public void errorAlert() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getHost() {
		return this.config.getHost();
	}

	@Override
	public String getPassword() {
		return this.config.getPassword();
	}

	@Override
	public int getPortIn() {
		return this.config.getPortIn();
	}

	@Override
	public int getPortOut() {
		return this.config.getPortOut();
	}

	@Override
	public String getPowerPointName(int id) {
		return this.powerPoints.get(id).getName();
	}

	@Override
	public int getPowerPointsCount() {
		return this.powerPointsCount;
	}

	@Override
	public String getUser() {
		return this.config.getUser();
	}

	@Override
	public void init() {
		this.powerPointsCount = testPowerPointsCount;
		this.config = new Config(testHost, testPortIn, testPortOut, testUser,
				testPassword);
		this.connectionManager = new ConnectionManager(this.config);

		for (int id = 0; id < this.powerPointsCount; id++) {
			this.addPowerPoint(id, "pP_0" + id, true, false);
		}
		this.responseProcessor = new ResponseProcessor(this.powerPoints);

		this.sendUpdateMessage();
	}

	@Override
	public boolean isPowerPointEnable(int id) {
		return this.powerPoints.get(id).isEnabled();
	}

	@Override
	public boolean isPowerPointOn(int id) {
		return this.powerPoints.get(id).isOn();
	}

	@Override
	public void sendState(int id, boolean on) {
		// TODO depends on the response something like that
		// this.powerPoints.get(id).setState(on);

	}

	@Override
	public void sendState(int id, boolean on, int time) {
		// TODO depends on the response something like that
		// after the specified time
		// this.powerPoints.get(id).setState(on);

	}

	@Override
	public void sendStateAll(boolean on) {
		// TODO depends on the response something like that
		// for(int id = 0; id < this.powerPointsCount; id++) {
		// this.powerPoints.get(id).setState(on);
		// }

	}

	@Override
	public void sendStateAll(boolean on, int time) {
		// TODO depends on the response something like that
		// after the specified time
		// for(int id = 0; id < this.powerPointsCount; id++) {
		// this.powerPoints.get(id).setState(on);
		// }

	}

	@Override
	public void sendUpdateMessage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConfig(String host, int portIn, int portOut, String user,
			String password) {

		if (this.config == null) {
			this.config = new Config(host, portIn, portOut, user, password);
		} else {
			this.config.setConfig(host, portIn, portOut, user, password);
		}
	}

	@Override
	public void setPowerPointName(int id, String name) {
		this.powerPoints.get(id).setName(name);
	}

	@Override
	public void updateDatastructure(String response) {
		if (this.responseProcessor == null) {
			this.responseProcessor = new ResponseProcessor(this.powerPoints);
		}
		this.responseProcessor.processResponse(response);
	}

}
