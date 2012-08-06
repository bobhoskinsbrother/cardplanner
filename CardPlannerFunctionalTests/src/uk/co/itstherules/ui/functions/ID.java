package uk.co.itstherules.ui.functions;

import uk.co.itstherules.yawf.model.Identity;
import uk.co.itstherules.yawf.model.IdentityDeleteable;

public final class ID implements Identity {
	private final String identity;
	private final Class<? extends IdentityDeleteable<?>> toInstantiate;
	public ID(String identity, Class<? extends IdentityDeleteable<?>> toInstantiate) {
		this.identity = identity;
		this.toInstantiate = toInstantiate;
    }
	public String getIdentity() { return identity; }
	public Class<? extends IdentityDeleteable<?>> toInstantiate() { return toInstantiate; }
	
}

