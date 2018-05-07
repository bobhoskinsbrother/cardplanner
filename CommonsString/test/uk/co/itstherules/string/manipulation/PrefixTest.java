package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PrefixTest {
	
	@Test
    public void canRemove() throws Exception {
	    Prefix unit = new Prefix();
	    assertEquals("remove.from.", unit.manipulate("remove.from.end"));
    }

	@Test
    public void cannotRemove() throws Exception {
		Prefix unit = new Prefix();
	    assertEquals("remove.end.", unit.manipulate("remove.end.from"));
    }

}
