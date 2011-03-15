package sta.andswtch.extensionLead;

import sta.andswtch.gui.IAndSwtchViews;

public class ExtensionLeadManager {

	private static ExtensionLeadManager ExtLeadManager = null;
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

	
	public ExtensionLead getExtLeadFromView(IAndSwtchViews v) {
		this.ExtLead.setCurrentView(v);
		return this.ExtLead;
	}
}
