package uk.co.itstherules.reflection;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClassFinderTest {

    @Test public void findMyClasses() throws ClassNotFoundException, IOException {
        ClassFinder unit = new ClassFinder();
        List<Class<?>> reply = unit.collectClassesFromAllPackagesNamed("test.things.to.find");
        assertEquals(test.things.to.find.FindMe.class, reply.get(0));
        assertEquals(test.things.to.find.FindMeToo.class, reply.get(1));
        assertEquals(test.things.to.find.subpackage.PleaseDontIgnoreMe.class, reply.get(2));
    }

    @Test public void illegalPath() throws IOException {
        ClassFinder unit = new ClassFinder();
        List<Class<?>> reply = unit.collectClassesFromAllPackagesNamed("test.thin im an\\illegal path * gs.to.find");
        assertEquals(0, reply.size());
    }
}