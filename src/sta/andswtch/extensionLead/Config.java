package sta.andswtch.extensionLead;

public class Config {

	private String host;

	private int extensionLeadSenderPort;
	private int extensionLeadReceiverPort;
	private String user;
	private String password;

	public Config(String host, int extensionLeadSenderPort,
			int extensionLeadReceiverPort, String user, String password) {
		this.host = host;
		this.extensionLeadSenderPort = extensionLeadSenderPort;
		this.extensionLeadReceiverPort = extensionLeadReceiverPort;
		this.user = user;
		this.password = password;
	}

	public void setConfig(String host, int extensionLeadSenderPort,
			int extensionLeadReceiverPort, String user, String password) {
		this.host = host;
		this.extensionLeadSenderPort = extensionLeadSenderPort;
		this.extensionLeadReceiverPort = extensionLeadReceiverPort;
		this.user = user;
		this.password = password;
	}

	public String getHost() {
		return this.host;
	}

	public int getExtensionLeadSenderPort() {
		return this.extensionLeadSenderPort;
	}

	public int getExtensionLeadReceiverPort() {
		return this.extensionLeadReceiverPort;
	}

	public String getUser() {
		return this.user;
	}

	public String getPassword() {
		return this.password;
	}

}
