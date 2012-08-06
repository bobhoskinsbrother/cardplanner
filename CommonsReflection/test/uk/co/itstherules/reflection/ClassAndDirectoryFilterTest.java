package uk.co.itstherules.reflection;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class ClassAndDirectoryFilterTest {
	
	@Test
    public void canAccept() throws Exception {
		ClassAndDirectoryFilter unit = new ClassAndDirectoryFilter();
		assertTrue(unit.accept(new File("badgers.class")));
		assertTrue(unit.accept(new File("test-resource")));
		assertFalse(unit.accept(new File("badgers.blast")));
    }
	
}
