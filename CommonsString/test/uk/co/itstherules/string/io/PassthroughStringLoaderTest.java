package uk.co.itstherules.string.io;

import static org.junit.Assert.*;

import org.junit.Test;

public class PassthroughStringLoaderTest {
	
	@Test
    public void returnBackWithSameString() throws Exception {
		PassthroughStringLoader unit = new PassthroughStringLoader();
		assertEquals("hi", unit.asString("hi"));
		assertEquals("my", unit.asString("my"));
		assertEquals("name", unit.asString("name"));
		assertEquals("is", unit.asString("is"));
		assertEquals(null, unit.asString(null));
    }
}
