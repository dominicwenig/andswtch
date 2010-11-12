package sta.andswtch.extensionLead;

import java.util.List;

public class ResponseProcessor {

	private List<PowerPoint> powerPoints;

	public ResponseProcessor(List<PowerPoint> powerPoints) {
		this.powerPoints = powerPoints;
	}

	public void processResponse(String string) {
		// TODO evaluate the string, if necessary
		// call the setEnabled and setState methods

	}

	private void setEnabled(int id, boolean enabled) {
		this.powerPoints.get(id).setEnable(enabled);
	}

	private void setState(int id, boolean on) {
		this.powerPoints.get(id).setState(on);
	}

}
