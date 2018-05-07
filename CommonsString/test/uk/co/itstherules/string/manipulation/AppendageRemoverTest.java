package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppendageRemoverTest {
	
	@Test
    public void canRemove() throws Exception {
	    AppendageRemover unit = new AppendageRemover(".end");
	    assertEquals("remove.from", unit.manipulate("remove.from.end"));
    }

	@Test
    public void cannotRemove() throws Exception {
	    AppendageRemover unit = new AppendageRemover(".end");
	    assertEquals("remove.end.from", unit.manipulate("remove.end.from"));
    }

}
