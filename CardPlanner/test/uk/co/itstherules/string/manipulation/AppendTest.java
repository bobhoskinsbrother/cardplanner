package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppendTest {

	@Test public void testCanAppend(){
		assertEquals("eerier", new Append("r").manipulate("eerie"));
	}
	
	@Test public void testCanAppendEmpty(){
		assertEquals("eerier", new Append("").manipulate("eerier"));
	}
	
}
