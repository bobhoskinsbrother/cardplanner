package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class SuffixTest {
	
	@Test
    public void canFindSuffix() throws Exception {
	    Suffix unit = new Suffix();
	    assertEquals("pee", unit.manipulate("free.pee"));
	    assertEquals("pee", unit.manipulate(".pee"));
	    assertEquals("", unit.manipulate("free."));
	    assertEquals("free", unit.manipulate("free"));
    }
}
