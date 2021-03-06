package uk.co.itstherules.cardplanner.controller;

import java.util.Calendar;
import java.util.Date;

import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

final class IdentityDeleteableImpl implements IdentityDeleteable<IdentityDeleteableImpl> {
    public void updateTimestamp() {}

    public void makeInvisible() {}

    public Boolean getInvisible() { 
	    return Boolean.FALSE;
   	}

    public Boolean getDeleted() {
    	return Boolean.FALSE;
    }

    public void delete() { }

    public String getIdentity() {return "";}

    public void setIdentity(String identity) {
    }

	public Date getUpdated() {
		return Calendar.getInstance().getTime();
    }

	public Boolean getArchived() {
	    return Boolean.FALSE;
    }

	public void archive() {
    }

	public Date getAdded() {
		return Calendar.getInstance().getTime();
    }

    public IdentityDeleteableImpl defaultSetup(ObjectCache objectCache) {
	    return this;
    }

	public void activate() {
    }

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