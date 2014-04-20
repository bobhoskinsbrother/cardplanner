package uk.co.itstherules.yawf.model.persistence;

public class ObjectCacheActionNotAuthorized extends RuntimeException {
	private static final long serialVersionUID = 8943289226869728447L;
	public ObjectCacheActionNotAuthorized(String exception) { super(exception); }
}
