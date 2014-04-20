package uk.co.itstherules.yawf.inbound.annotations.processor;

import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

public class CachedStringFieldModelWithIllegalDelegate {
	@QueryKey(value="delegate", cache=CacheInstruction.FromCache) private IllegalDelegateModel delegate;
	
	public IllegalDelegateModel getDelegate() {
	    return delegate;
    }

}