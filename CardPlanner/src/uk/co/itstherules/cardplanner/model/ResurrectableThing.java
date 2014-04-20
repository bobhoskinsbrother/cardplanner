package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.model.IdentityDeleteable;

public final class ResurrectableThing {

	private final Class<? extends IdentityDeleteable<?>> theClass;
	private final String title;
	private final String identity;

	public ResurrectableThing(String identity, String title, Class<? extends IdentityDeleteable<?>> theClass) {
		this.identity = identity;
		this.title = title;
		this.theClass = theClass;
	}

	public Class<? extends IdentityDeleteable<?>> get() {
		return theClass;
	}
	
	public String getIdentity() {
		return identity;
	}

	public String getTitle() {
		return title;
	}
}