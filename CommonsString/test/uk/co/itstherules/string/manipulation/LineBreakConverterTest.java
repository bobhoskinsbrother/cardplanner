package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class LineBreakConverterTest {
	@Test
    public void canConvert() throws Exception {
		LineBreakConverter unit = new LineBreakConverter("wibble");
		assertEquals("i like towibbletake a break", unit.manipulate("i like to"+System.getProperty("line.separator")+"take a break"));
    }
}
