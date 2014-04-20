package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.inbound.annotations.QueryKeyRoot;

public class ClassFieldDescriptionsTest {
	
	@Test public void simpleCollect() {
		Map<String, Field> reply = ClassFieldDescriptions.getFieldsFor(SimpleTestClass.class);
		Assert.assertEquals("freud", reply.get("koi").getName());
    }
	
	@Test public void simpleHeirarchy() {
		Map<String, Field> reply = ClassFieldDescriptions.getFieldsFor(SimpleHeirarchyTestClass.class);
		Assert.assertEquals("freud", reply.get("koi").getName());
		Assert.assertEquals("royal", reply.get("poi").getName());
    }
	
	@Test public void stopHeirarchyHalfway() {
		Map<String, Field> reply = ClassFieldDescriptions.getFieldsFor(BottomHeirarchyTestClass.class);
		Assert.assertNull(reply.get("oik"));
		Assert.assertEquals("loyal", reply.get("koi").getName());
		Assert.assertEquals("embroil", reply.get("poi").getName());
    }
}


class SimpleTestClass { @SuppressWarnings("unused")
@QueryKey("koi") private String freud; }
class SimpleHeirarchyTestClass extends SimpleTestClass { @SuppressWarnings("unused")
@QueryKey("poi") private String royal; }

class TopHeirarchyTestClass { @SuppressWarnings("unused")
@QueryKey("oik") private String royal; }
@QueryKeyRoot class MiddleHeirarchyTestClass extends TopHeirarchyTestClass { @SuppressWarnings("unused")
@QueryKey("koi") private String loyal; }
class BottomHeirarchyTestClass extends MiddleHeirarchyTestClass { @SuppressWarnings("unused")
@QueryKey("poi") private String embroil; }

