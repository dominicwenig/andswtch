package sta.andswtch.extensionLead;

public interface IExtensionLead {

	public abstract String getHost();

	public abstract String getPassword();

	public abstract int getPortIn();

	public abstract int getPortOut();

	public abstract String getPowerPointName(int id);

	public abstract int getPowerPointsCount();

	public abstract String getUser();

	public abstract boolean isPowerPointEnable(int id);

	public abstract boolean isPowerPointOn(int id);

	public abstract void switchState(int id);

	public abstract void sendState(int id, boolean on, int time);

	public abstract void sendStateAll(boolean on);

	public abstract void sendStateAll(boolean on, int time);

	public abstract void sendUpdateMessage();

	public abstract void setConfig(String host, int portIn, int portOut,
			String user, String password);

	public abstract void setPowerPointName(int id, String name);

	public abstract void updateDatastructure(String response);

	public abstract void errorOccured(String message);

}