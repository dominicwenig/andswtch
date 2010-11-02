package sta.andswtch.network;

public class ConnectionManager implements IConnectionManager {

	private String host;
	private int portIn;
	private int portOut;
	private String user;
	private String password;

	public ConnectionManager(String host, int portIn, int portOut, String user,
			String password) {
		this.host = host;
		this.portIn = portIn;
		this.portOut = portOut;
		this.user = user;
		this.password = password;
	}

	@Override
	public void errorAlert() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public int getPortIn() {
		return this.portIn;
	}

	@Override
	public int getPortOut() {
		return this.portOut;
	}

	@Override
	public String getUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUser() {
		return this.user;
	}

	@Override
	public String sendReceive(String command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfig(String host, int portIn, int portOut, String user,
			String password) {
		this.host = host;
		this.portIn = portIn;
		this.portOut = portOut;
		this.user = user;
		this.password = password;
	}

	@Override
	public void updateDatastructure(String response) {
		// TODO Auto-generated method stub

	}

}
