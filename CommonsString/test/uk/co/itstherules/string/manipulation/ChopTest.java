package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ChopTest  {

	@Test public void testCanChop(){
		assertEquals("rope", new Chop("eerie").manipulate("eerierope"));
	}
	
	@Test public void testCanChopAll(){
		assertEquals("", new Chop("eerierope").manipulate("eerierope"));
	}
	
	@Test public void testCannotChopWhenInMiddle(){
		assertEquals("eerierope", new Chop("rie").manipulate("eerierope"));
	}
	
	@Test public void testCannotChopWhenInMore(){
		assertEquals("eerierope", new Chop("eeeerierope").manipulate("eerierope"));
	}
	
}
