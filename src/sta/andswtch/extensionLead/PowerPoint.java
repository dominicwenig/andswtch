package sta.andswtch.extensionLead;

public class PowerPoint {

	private String name;
	private int id;
	private boolean enabled;
	private boolean on;

	public PowerPoint(int id, String name, boolean enable, boolean on) {
		this.id = id;
		this.name = name;
		this.enabled = enable;
		this.on = on;
	}

	public void setState(boolean on) {
		this.on = on;
	}

	public void setEnable(boolean enabled) {
		this.enabled = enabled;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean isOn() {
		return this.on;
	}

}
