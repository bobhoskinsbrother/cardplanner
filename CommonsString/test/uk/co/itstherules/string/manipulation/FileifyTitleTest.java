package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileifyTitleTest {
	
	@Test
    public void can() throws Exception {
		FileifyTitle unit = new FileifyTitle(".sikuli");
		assertEquals("IAmAFileName.sikuli", unit.manipulate("i-am*a?file<>name"));
    }
}
