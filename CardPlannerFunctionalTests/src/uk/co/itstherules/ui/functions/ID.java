package uk.co.itstherules.ui.functions;

import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.Identity;
import uk.co.itstherules.yawf.model.IdentityDeleteable;

import java.util.Collections;

public final class ID implements Identity {
	private final String identity;
	private final Class<? extends IdentityDeleteable<?>> toInstantiate;
	public ID(String identity, Class<? extends IdentityDeleteable<?>> toInstantiate) {
		this.identity = identity;
		this.toInstantiate = toInstantiate;
    }

    @Override public ValuesProvider provider() { return new MapValuesProvider(Collections.EMPTY_MAP); }

    public String getIdentity() { return identity; }
	public Class<? extends IdentityDeleteable<?>> toInstantiate() { return toInstantiate; }
	
}

