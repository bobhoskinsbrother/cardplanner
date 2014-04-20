package uk.co.itstherules.yawf.delta;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public abstract class BaseDelta {

	protected final ObjectCache objectCache;
	private final int deltaNumber;

	public BaseDelta(ObjectCache objectCache, int deltaNumber) {
		this.objectCache = objectCache;
		this.deltaNumber = deltaNumber;
    }
	public int getDeltaNumber() { return deltaNumber; }
}
