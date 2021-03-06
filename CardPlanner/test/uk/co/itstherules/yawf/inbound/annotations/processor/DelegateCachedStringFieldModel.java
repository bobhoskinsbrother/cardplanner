package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.util.Date;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class DelegateCachedStringFieldModel implements IdentityDeleteable<DelegateCachedStringFieldModel> {
	
	@QueryKey(value="identity") private String identity;
	@QueryKey(value="stringValue") private String stringValue;
	
	public String getIdentity() {
    	return identity;
    }
	public String getStringValue() {
    	return stringValue;
    }
	public Boolean getDeleted() {
	    return Boolean.FALSE;
    }
	public void delete() {
    }
	public Boolean getInvisible() {
	    return Boolean.FALSE;
    }
	public void makeInvisible() {
    }
	public void updateTimestamp() {
    }
	public Date getUpdated() {
	    return null;
    }
	public void setIdentity(String identity) {
    }
	public Boolean getArchived() {
	    return null;
    }
	public void archive() {
    }
	public Date getAdded() {
	    return null;
    }
	public DelegateCachedStringFieldModel defaultSetup(ObjectCache objectCache) {
	    return this;
    }
	@Override
    public void activate() {
    }
	@Override
    public Boolean getActive() {
	    return Boolean.TRUE;
    }
	@Override
    public Boolean getPending() {
	    return Boolean.FALSE;
    }
	@Override
    public void pending() {
    }
	@Override
    public int getSortOrder() {
	    return 0;
    }
	@Override
    public void setSortOrder(Integer sortOrder) {
    }
	
}