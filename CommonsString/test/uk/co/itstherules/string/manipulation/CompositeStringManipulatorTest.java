package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompositeStringManipulatorTest {

	@Test public void testCanCompose() {
		Chomp one = new Chomp(":( :( :(");
		Append two = new Append(":) :) :)");
		
		CompositeStringManipulator unit = new CompositeStringManipulator(one, two);
		assertEquals("I'm :) :) :)", unit.manipulate("I'm :( :( :("));
	}
	
	@Test public void testCanComposeInOrder() {
		Chomp one = new Chomp(":(");
		Prepend two = new Prepend(":)");
		Append three = new Append(":)");
		Chomp four = new Chomp(":(");
		
		CompositeStringManipulator unit = new CompositeStringManipulator(one, two, three, four);
		assertEquals(":):) I'm :(:(:)", unit.manipulate(":) I'm :(:(:("));
	}
	
}
