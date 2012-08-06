package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class TitleifyFileTest {
	
	@Test
    public void canTitleify() throws Exception {
		TitleifyFile unit = new TitleifyFile(".html");
		
		assertEquals("Im A Text File", unit.manipulate("ImATextFile.html"));
		assertEquals("Im A Text File.txt", unit.manipulate("ImATextFile.txt"));
    }
}
