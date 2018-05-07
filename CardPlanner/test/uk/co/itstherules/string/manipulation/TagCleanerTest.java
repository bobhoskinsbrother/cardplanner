package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class TagCleanerTest {
	
	@Test
    public void cleanMeSimple() throws Exception {
		TagCleaner unit = new TagCleaner();
		
	    assertEquals("clean me", unit.manipulate("<dirty>clean me</dirty>"));
	    assertEquals("clean me", unit.manipulate("<dirty>clean me</ dirty>"));
	    assertEquals("clean me", unit.manipulate("<dirty age=\"old man\">clean me</ dirty>"));
    }

	@Test
    public void cleanMeNested() throws Exception {
		TagCleaner unit = new TagCleaner();
	    assertEquals("clean mei'm not very nice", unit.manipulate("<dirty><bugger>clean me</bugger><yuk>i'm not very nice</yuk></dirty>"));
    }
}
