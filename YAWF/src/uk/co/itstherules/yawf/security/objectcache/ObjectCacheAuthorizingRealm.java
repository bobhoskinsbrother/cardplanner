package uk.co.itstherules.yawf.security.objectcache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.ObjectCacheFactory;


public final class ObjectCacheAuthorizingRealm extends AuthorizingRealm {

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	ObjectCache objectCache = ObjectCacheFactory.get();
        UsernamePasswordToken usernamePasswordToken = UsernamePasswordToken.class.cast(token);
        String username = usernamePasswordToken.getUsername();
        if (username == null) { throw new AccountException("Null usernames are not allowed by this realm."); }
        String password = getPasswordForUser(objectCache, username);
        AuthenticationInfo info = buildAuthenticationInfo(username, password.toCharArray());
        objectCache.close();
        return info;
    }

    protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password) {
        return new SimpleAuthenticationInfo(username, password, getName());
    }

    private UserModel getUserForUserName(ObjectCache objectCache, String username) throws UnknownAccountException {
    	Set<UserModel> users = objectCache.all(UserModel.class, "userName", username, ObjectState.Active);
    	if(users.size()==0) { throw new UnknownAccountException("No active account found for user [" + username + "]"); }
    	if(users.size() > 1) { throw new UnknownAccountException("Multiple accounts found for username [" + username + "]"); }
    	return users.iterator().next();
    }
    
    
    private String getPasswordForUser(ObjectCache objectCache, String username) throws UnknownAccountException {
    	return getUserForUserName(objectCache, username).getPassword();
    }

    @Override protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        String username = String.class.cast(getAvailablePrincipal(principals));
    	ObjectCache objectCache = ObjectCacheFactory.get();
        Set<GroupModel> groups = getGroupsForUser(objectCache, username);
        Set<String> groupNames = new HashSet<String>();
        Set<String> permissionsStrings = new HashSet<String>();
        for (GroupModel group : groups) {
        	groupNames.add(group.getTitle());
	        List<PermissionModel> permissions = group.getPermissions();
	        if(permissions != null) {
	        	for (PermissionModel permission : permissions) {
	        		permissionsStrings.add(permission.getPermission());
	        	}
	        }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(groupNames);
        info.setStringPermissions(permissionsStrings);
        objectCache.close();
        return info;

    }

    private Set<GroupModel> getGroupsForUser(ObjectCache objectCache, String username) {
    	return getUserForUserName(objectCache, username).getGroups();
    }
}