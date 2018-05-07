package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ChompTest {

	@Test public void testCanChomp(){
		assertEquals("eerie", new Chomp("rope").manipulate("eerierope"));
	}
	
	@Test public void testCanChompAll(){
		assertEquals("", new Chomp("eerierope").manipulate("eerierope"));
	}
	
	@Test public void testCannotChompWhenInMiddle(){
		assertEquals("eerierope", new Chomp("rie").manipulate("eerierope"));
	}
	
	@Test public void testCannotChompWhenInMore(){
		assertEquals("eerierope", new Chomp("eeeerierope").manipulate("eerierope"));
	}
	
}
