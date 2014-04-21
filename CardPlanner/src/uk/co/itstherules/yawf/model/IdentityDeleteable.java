package uk.co.itstherules.yawf.model;

import java.util.Date;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public interface IdentityDeleteable<T> {
	
	T defaultSetup(ObjectCache objectCache);
	void setIdentity(String identity); 
	String getIdentity();
	
	int getSortOrder();
	void setSortOrder(Integer sortOrder);

	Boolean getDeleted();
	void delete();

	Boolean getPending();
	void pending();
	
	Boolean getInvisible();
	void makeInvisible();
	
	Boolean getArchived();
	void archive();
	
	Boolean getActive();
	void activate();

	void updateTimestamp(); 
	Date getUpdated();
	Date getAdded();
	
}