package uk.co.itstherules.yawf.security.objectcache;

import java.text.MessageFormat;

import javax.persistence.Entity;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity 
public final class PermissionModel extends IdentifiableDeleteableModel<PermissionModel> {

	@SuppressWarnings("unused") private static final long serialVersionUID = -6663987330709771359L;
	public static final String ALL = "*";
	
	@QueryKey("classDefinition") private String classDefinition = "";
	@QueryKey("instanceIdentity") private String instanceIdentity = "";
	@QueryKey("instanceAction") private String instanceAction = "";
	
	public PermissionModel() {}
	
	public PermissionModel(String classDefinition, String instanceIdentity, String instanceAction) {
		this.classDefinition = classDefinition;
		this.instanceIdentity = instanceIdentity;
		this.instanceAction = instanceAction;
    }
	
	public String getPermission() { return PermissionModel.buildPermissionString(classDefinition, instanceIdentity, instanceAction); }
	
	public static String buildPermissionString(String classDefinition, String instanceIdentity, String instanceAction) {
		Assertion.checkNotNull(classDefinition);
		Assertion.checkNotNull(instanceIdentity);
		Assertion.checkNotNull(instanceAction);
		return MessageFormat.format("{0}:{1}:{2}", classDefinition, instanceIdentity, instanceAction);
	}

    @Override public PermissionModel defaultSetup(ObjectCache objectCache) { return this; }

}
