package uk.co.itstherules.yawf.model;

import java.util.Calendar;
import java.util.Date;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public final class NullEntity implements Entity<NullEntity> {

	public void setIdentity(String identity) {
    }

	public String getIdentity() {
		return "Null";
    }

	public void delete() {
    }

	public Boolean getDeleted() {
		return Boolean.TRUE;
    }

	public Boolean getInvisible() {
		return Boolean.TRUE;
    }

	public void makeInvisible() {
    }

	public Date getUpdated() {
		return getAdded();
    }

	public void updateTimestamp() {
    }

	public String getTitle() {
		return getIdentity();
    }

	public void setTitle(String title) {
    }

	public Boolean getArchived() {
	    return Boolean.FALSE;
    }

	public void archive() {
    }

	public Date getAdded() {
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, 0);
		instance.set(Calendar.MONTH, 0);
		instance.set(Calendar.DATE, 0);
		instance.set(Calendar.HOUR, 0);
		instance.set(Calendar.MINUTE, 0);
		instance.set(Calendar.SECOND, 0);
		instance.set(Calendar.MILLISECOND, 0);
		return instance.getTime();
    }

	public NullEntity defaultSetup(ObjectCache objectCache) {
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
    public int getSortOrder() { return 0; }

	@Override
    public void setSortOrder(Integer sortOrder) {
    }
}
