package uk.co.itstherules.yawf.security.objectcache;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.NotBlank;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.inbound.annotations.UseBinder;
import uk.co.itstherules.yawf.inbound.binders.PasswordBinder;
import uk.co.itstherules.yawf.inbound.binders.UniqueStringFieldBinder;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.serializer.Json;

@Entity 
public final class UserModel extends IdentifiableDeleteableModel<UserModel> {

	@QueryKey("userName") @NotBlank @UseBinder(UniqueStringFieldBinder.class) private String userName;
	@QueryKey("password") @NotBlank @UseBinder(PasswordBinder.class) private String password;
	@QueryKey("email") @NotBlank @Email private String email;
	@ManyToMany @QueryKey(value="groups", cache=CacheInstruction.FromCache) private Set<GroupModel> groups = new HashSet<GroupModel>();

	public UserModel() { 
		super(); 
	}
	
	@Override
	public UserModel defaultSetup(ObjectCache objectCache) {
	    return this;
	}

	public void addGroup(GroupModel group) { groups.add(group); }
	
	public String getEmail() { return email; }
	public Set<GroupModel> getGroups() { return groups; }
	public String getPassword() { return password; }
	public String getUserName() { return userName; }
	public boolean isAccountNonExpired() { return !getExpired(); }
	public boolean isAccountNonLocked() { return !getLocked(); }
	public boolean isCredentialsNonExpired() { return !getExpired(); }
	public boolean isEnabled() { return getActive(); }
	public void setGroups(Set<GroupModel> groups) { this.groups = groups; }
	public void setPassword(String password) { this.password = password; }
	public void setUsername(String userName) { this.userName = userName; }
	@Override public String toString() { return new Json<Object>().serialize(this); }
}
