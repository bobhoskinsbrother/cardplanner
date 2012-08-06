package uk.co.itstherules.yawf.model;

public interface FileIdentityDeleteable<T> extends IdentityDeleteable<T> {
	void write();
}