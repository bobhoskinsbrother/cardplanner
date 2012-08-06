package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class SplitCamelCaseTest {
	
	@Test
    public void canSplit() throws Exception {
		SplitCamelCase unit = new SplitCamelCase();
	 	assertEquals("Judean Peoples Front- Splitter!", unit.manipulate("JudeanPeoplesFront-Splitter!"));
	 	assertEquals("Judean 5 Peoples Front- Splitter!", unit.manipulate("Judean5PeoplesFront-Splitter!"));
    }

	@Test
    public void canSplitWithNumberAtFront() throws Exception {
		SplitCamelCase unit = new SplitCamelCase();
	 	assertEquals("10 Judean 5 Peoples Front- Splitter!", unit.manipulate("10Judean5PeoplesFront-Splitter!"));
    }

}
