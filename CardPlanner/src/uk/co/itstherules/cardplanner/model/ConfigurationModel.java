package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity 
public final class ConfigurationModel extends IdentifiableDeleteableModel<ConfigurationModel> {

	@QueryKey("firstRun") private Boolean firstRun = Boolean.TRUE;
	
	public ConfigurationModel defaultSetup(ObjectCache objectCache) {
		return this;
	}  
	
	public Boolean getFirstRun() {
		return firstRun;
	}
	
	public Boolean getActive() { return Boolean.TRUE; }
	public Boolean getExpired() { return Boolean.FALSE; }
	public Boolean getLocked() { return Boolean.FALSE; }
	public Boolean getPending() {  return Boolean.FALSE; }
	public Boolean getArchived() { return Boolean.FALSE; }
	public Boolean getDeleted() { return Boolean.FALSE; }
	public Boolean getInvisible() { return Boolean.FALSE; }

	public void activate() { }
	public void archive() { }
	public void delete() { }
	public void makeInvisible() { }
	public void expire() { }
	public void lock() { }
	public void pending() { }

	public void firstRunComplete() { this.firstRun = Boolean.FALSE; }
}

	
