package sta.andswtch.extensionLead;

import sta.andswtch.gui.IAndSwtchViews;

public class ExtensionLeadManager {

	private static ExtensionLeadManager ExtLeadManager;
	private ExtensionLead ExtLead;
	
	private ExtensionLeadManager(IAndSwtchViews v) {
		this.ExtLead = new ExtensionLead(v);
	}
	
	public static ExtensionLeadManager getInstance(IAndSwtchViews v) {
		if(ExtLeadManager == null) {
			ExtLeadManager = new ExtensionLeadManager(v);
		}
		return ExtLeadManager;
	}

	public static ExtensionLeadManager getInstance() throws Exception {
		if(ExtLeadManager == null) {
			throw new Exception("FATAL ERROR! ExtensionLead was never initialized with a view context.");
		}
		return ExtLeadManager;
	}
	
	public ExtensionLead getExtLeadFromView(IAndSwtchViews v) {
		this.ExtLead.setCurrentView(v);
		return this.ExtLead;
	}
	
	public ExtensionLead getExtLead() {
		return this.ExtLead;
	}
}
