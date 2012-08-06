package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;

import net.sf.oval.constraint.AssertURL;
import net.sf.oval.constraint.NotEmpty;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


@Entity 
public final class SyncServerModel extends IdentifiableDeleteableModel<SyncServerModel> {

	@QueryKey(value="url") @NotEmpty @AssertURL private String url;
	@QueryKey(value="syncType") @NotEmpty private SyncType syncType;
	@QueryKey(value="requiresPassword") @NotEmpty private Boolean requiresPassword;
	@QueryKey(value="username") private String username;
	@QueryKey(value="password") private String password;

	enum SyncType { Originator, Subscriber }
	
	public SyncServerModel() { super(); this.syncType = SyncType.Originator; this.requiresPassword = Boolean.FALSE; }
	public SyncType getSyncType() { return syncType; }
	public void setSyncType(SyncType syncType) { this.syncType = syncType; }
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	public Boolean getRequiresPassword() { return requiresPassword; }
	public String getUsername() { return username; }
	public String getPassword() { return password; }
    public SyncServerModel defaultSetup(ObjectCache objectCache) { return this; }

}