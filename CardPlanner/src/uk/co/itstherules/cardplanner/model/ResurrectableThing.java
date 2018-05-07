package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import java.util.Date;

public final class ResurrectableThing implements Entity<ResurrectableThing> {

	private final Class<? extends IdentityDeleteable<?>> theClass;
	private final String title;
	private final String identity;

	public ResurrectableThing(String identity, String title, Class<? extends IdentityDeleteable<?>> theClass) {
		this.identity = identity;
		this.title = title;
		this.theClass = theClass;
	}

	public Class<? extends IdentityDeleteable<?>> get() {
		return theClass;
	}

    @Override public ResurrectableThing defaultSetup(ObjectCache objectCache) {
        return this;
    }

    @Override public void setIdentity(String identity) {
    }

    public String getIdentity() {
		return identity;
	}

    @Override public int getSortOrder() {
        return 0;
    }

    @Override public void setSortOrder(Integer sortOrder) { }

    @Override public Boolean getDeleted() {
        return false;
    }

    @Override public void delete() {
    }

    @Override public Boolean getPending() {
        return false;
    }

    @Override public void pending() {
    }

    @Override public Boolean getInvisible() {
        return false;
    }

    @Override public void makeInvisible() {
    }

    @Override public Boolean getArchived() {
        return false;
    }

    @Override public void archive() {
    }

    @Override public Boolean getActive() {
        return false;
    }

    @Override public void activate() {
    }

    @Override public void updateTimestamp() {
    }

    @Override public Date getUpdated() {
        throw new UnsupportedOperationException();
    }

    @Override public Date getAdded() {
        throw new UnsupportedOperationException();
    }

    public String getTitle() {
		return title;
	}

    @Override public void setTitle(String title) {
    }
}