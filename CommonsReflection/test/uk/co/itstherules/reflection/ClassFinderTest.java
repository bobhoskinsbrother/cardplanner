package uk.co.itstherules.reflection;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ClassFinderTest {
	
	@Test public void findMyClasses() throws ClassNotFoundException, IOException {
		ClassFinder unit = new ClassFinder();
		List<Class<?>> reply = unit.collectClassesFromAllPackagesNamed("test.things.to.find");
		Assert.assertEquals(test.things.to.find.FindMe.class, reply.get(0));
		Assert.assertEquals(test.things.to.find.FindMeToo.class, reply.get(1));
		Assert.assertEquals(test.things.to.find.subpackage.PleaseDontIgnoreMe.class, reply.get(2));
	}
	
	public void illegalPath() throws IOException {
		ClassFinder unit = new ClassFinder();
		List<Class<?>> reply = unit.collectClassesFromAllPackagesNamed("test.thin im an\\illegal path * gs.to.find");
		Assert.assertEquals(0, reply.size());
	}
}