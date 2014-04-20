package uk.co.itstherules.yawf.model.persistence;

import java.text.MessageFormat;
import java.util.Date;

import uk.co.itstherules.yawf.model.IdentityDeleteable;

public class ObjectCacheLog implements IdentityDeleteable<ObjectCacheLog>, Comparable<ObjectCacheLog> {


	private static final int SAME = 0;
	private static final int NEGATIVE = -1;
	private static final int POSITIVE = 1;
	
	private final ObjectCacheAction action;
	private final String description;
	private final Date timeOfEntry;
	private final Class<?> type;
	private String identity;
	private final Boolean archived = Boolean.FALSE;

	public ObjectCacheLog(ObjectCacheAction action, String description, Class<?> type, Date timeOfEntry) {
		this.action = action;
		this.description = description;
		this.type = type;
		this.timeOfEntry = timeOfEntry;
    }
	
	public Class<?> getType() {
		return type;
	}
	
	public ObjectCacheAction getAction() {
    	return action;
    }

	public String getDescription() {
    	return description;
    }

	public Date getTimeOfEntry() {
    	return timeOfEntry;
    }
	
	public Boolean getArchived() {
		return archived;
	}

	public int compareTo(ObjectCacheLog other) {
		Date thisDate = this.getTimeOfEntry();
		Date otherDate = other.getTimeOfEntry();
		if(thisDate.before(otherDate)) {
			return POSITIVE;
		}
		else if(thisDate.after(otherDate)) {
			return NEGATIVE;
		} else {
			return SAME;
		}
    }

	public void setIdentity(String identity) {
	    this.identity = identity;
    }

	public String getIdentity() {
		return identity;
	}

	public void delete() { throw new RuntimeException("Never implemented"); }

	public Boolean getDeleted() {
		return Boolean.FALSE;
	}

	public void updateTimestamp() { }

	public Boolean getInvisible() {
		return Boolean.FALSE;
    }

	public Date getUpdated() {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public void archive() {
    }

	public Date getAdded() { return getTimeOfEntry(); }

	public ObjectCacheLog defaultSetup(ObjectCache objectCache) {
	    return this;
    }

	@Override
    public void makeInvisible() {
    }

	@Override
    public void activate() {
    }

	@Override
    public Boolean getActive() {
	    return null;
    }
	
	@Override
	public String toString() {
	    return MessageFormat.format("{0}, {1}, {2}, {3}", getTimeOfEntry(), getAction(), getDescription(), getType().getCanonicalName());
	}

	@Override
    public Boolean getPending() {
	    return null;
    }

	@Override
    public void pending() {
    }

	@Override
    public int getSortOrder() { return 0; }

	@Override
    public void setSortOrder(Integer sortOrder) { }
}