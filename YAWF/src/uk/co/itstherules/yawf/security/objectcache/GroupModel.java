package uk.co.itstherules.yawf.security.objectcache;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity 
public final class GroupModel extends IdentifiableDeleteableModel<GroupModel> {

	@ManyToMany @QueryKey(value="permissions", cache=CacheInstruction.FromCache) private List<PermissionModel> permissions = new ArrayList<PermissionModel>();

	public void setPermissions(List<PermissionModel> permissions) { this.permissions = permissions; }
	public void addPermission(PermissionModel permission) { this.permissions.add(permission); }
	public void removePermission(PermissionModel permission) { this.permissions.remove(permission); }
	public List<PermissionModel> getPermissions() { return permissions; }
    @Override public GroupModel defaultSetup(ObjectCache objectCache) { return this; }

}