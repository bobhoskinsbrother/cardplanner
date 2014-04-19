package uk.co.itstherules.reflection;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClassAndDirectoryFilterTest {
	
	@Test
    public void canAccept() throws Exception {
		ClassAndDirectoryFilter unit = new ClassAndDirectoryFilter();
		assertTrue(unit.accept(new File("badgers.class")));
		assertTrue(unit.accept(new File("./")));
		assertFalse(unit.accept(new File("badgers.blast")));
    }
	
}
