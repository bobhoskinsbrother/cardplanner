package uk.co.itstherules.yawf.security.objectcache;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PermissionModelTest {
	
	@Test
    public void canBuildPermission() throws Exception {
		String reply = PermissionModel.buildPermissionString("uk.co.itstherules.yawf.security.objectcache.PermissionModel", "0", "Delete");
		assertEquals("uk.co.itstherules.yawf.security.objectcache.PermissionModel:0:Delete", reply);
    }
	
	@Test(expected=IllegalArgumentException.class) public void noClassFails() throws Exception {
		PermissionModel.buildPermissionString(null, "0", "Delete");
    }

	@Test(expected=IllegalArgumentException.class) public void noIdentityFails() throws Exception {
		PermissionModel.buildPermissionString("uk.co.itstherules.yawf.security.objectcache.PermissionModel", null, "Delete");
    }
	
	@Test(expected=IllegalArgumentException.class) public void noActionFails() throws Exception {
		PermissionModel.buildPermissionString("uk.co.itstherules.yawf.security.objectcache.PermissionModel", "0", null);
    }

}
