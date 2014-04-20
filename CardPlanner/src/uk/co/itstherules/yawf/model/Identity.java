package uk.co.itstherules.yawf.model;


public interface Identity {
	String getIdentity();
	Class<? extends IdentityDeleteable<?>> toInstantiate();
}
