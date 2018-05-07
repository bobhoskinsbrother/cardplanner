package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExtensionFinderTest {
	
	@Test
    public void canFind() throws Exception {
		ExtensionFinder unit = new ExtensionFinder(".");
		assertEquals("anotherbedroom", unit.manipulate("build.anotherbedroom"));
		assertEquals("anotheroffice", unit.manipulate("build.anotherbedroom.anotheroffice"));
    }
	
	@Test
    public void cannotFind() throws Exception {
		ExtensionFinder unit = new ExtensionFinder(".");
		assertEquals("build_anotherbedroom", unit.manipulate("build_anotherbedroom"));
    }
}
