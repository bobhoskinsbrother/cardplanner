package uk.co.itstherules.yawf.model;

import java.util.Date;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public interface IdentityDeleteable<T> extends Saveable, Deleteable {
	
	T defaultSetup(ObjectCache objectCache);
	void setIdentity(String identity); 
	String getIdentity();
	
	int getSortOrder();
	void setSortOrder(Integer sortOrder);

	Boolean getDeleted();

    Boolean getPending();
	void pending();
	
	Boolean getInvisible();
	void makeInvisible();
	
	Boolean getArchived();
	void archive();
	
	Boolean getActive();
	void activate();

    Date getUpdated();
	Date getAdded();
	
}