package sta.andswtch.extensionLead;

public interface IExtensionLead {


	public abstract void switchState(int id);

	public abstract void sendState(int id, boolean on, int time);

	public abstract void sendStateAll(boolean on);

	public abstract void sendUpdateMessage();

	public abstract void updateDatastructure(String response);

	public abstract void errorOccured(int message);

	public abstract boolean isPowerPointOn(int id);

	public abstract boolean isPowerPointEnable(int id);

	public abstract void setPowerPointName(int id, String name);

	public abstract String getPowerPointName(int id);

	public abstract String getHost();

	public abstract int getPortIn();

	public abstract int getPortOut();

	public abstract String getPassword();

	public abstract String getUser();

}