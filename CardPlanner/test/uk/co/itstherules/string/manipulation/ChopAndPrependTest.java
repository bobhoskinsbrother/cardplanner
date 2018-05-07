package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ChopAndPrependTest  {

	@Test
	public void canChopAndPrepend() {
		StringManipulator unit = new ChopAndPrepend("Jason", "Ben");
		assertEquals("Ben is the best!", unit.manipulate("Jason is the best!"));
	}
	
	@Test
	public void cannotChopAndPrependWhenInMiddle() {
		StringManipulator unit = new ChopAndPrepend("Jason", "Ben ");
		assertEquals("Ben Heather and Jason is the best!", unit.manipulate("Heather and Jason is the best!"));
	}
	
}
