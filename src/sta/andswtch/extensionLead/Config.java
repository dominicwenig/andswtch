package sta.andswtch.extensionLead;

public class Config {

	private String host;
	private int portIn;
	private int portOut;
	private String user;
	private String password;

	public Config(String host, int portIn, int portOut, String user,
			String password) {
		this.host = host;
		this.portIn = portIn;
		this.portOut = portOut;
		this.user = user;
		this.password = password;
	}

	public void setConfig(String host, int portIn, int portOut, String user,
			String password) {
		this.host = host;
		this.portIn = portIn;
		this.portOut = portOut;
		this.user = user;
		this.password = password;
	}

	public String getHost() {
		return this.host;
	}

	public int getPortIn() {
		return this.portIn;
	}

	public int getPortOut() {
		return this.portOut;
	}

	public String getUser() {
		return this.user;
	}

	public String getPassword() {
		return this.password;
	}

}
