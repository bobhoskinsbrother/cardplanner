package uk.co.itstherules.yawf.model;

import java.util.Calendar;
import java.util.Date;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public final class SimpleEntityModel implements Entity<SimpleEntityModel> {
	
	private String identity;
	private String title;

	public SimpleEntityModel(String identity, String title) {
		this.identity = identity;
		this.title = title;
    }

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getIdentity() {
		return identity;
	}

	public void delete() {
	}

	public Boolean getDeleted() {
		return Boolean.FALSE;
	}

	public void updateTimestamp() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {}

	public Boolean getInvisible() {
		return Boolean.FALSE;
    }

	public void makeInvisible() {
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

    public SimpleEntityModel defaultSetup(ObjectCache objectCache) {
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
