package uk.co.itstherules.yawf.inbound.annotations.processor;

import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class CachedStringFieldModel {
	@QueryKey(value="delegate", cache=CacheInstruction.FromCache) private DelegateCachedStringFieldModel delegate;
	
	public DelegateCachedStringFieldModel getDelegate() {
	    return delegate;
    }

}