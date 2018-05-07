package uk.co.itstherules.yawf.model;

import uk.co.itstherules.yawf.inbound.ValuesProvider;

public interface Identity {

    ValuesProvider provider();

    String getIdentity();

    Class<? extends IdentityDeleteable<?>> toInstantiate();

}
