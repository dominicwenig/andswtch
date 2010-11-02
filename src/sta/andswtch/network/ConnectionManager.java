package sta.andswtch.network;

import sta.andswtch.extensionLead.Config;

public class ConnectionManager implements IConnectionManager {

	private Config config;

	public ConnectionManager(Config config) {
		this.config = config;
	}

	@Override
	public void errorAlert() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendReceive(String command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDatastructure(String response) {
		// TODO Auto-generated method stub

	}

}
