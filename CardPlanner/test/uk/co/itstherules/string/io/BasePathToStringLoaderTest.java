package uk.co.itstherules.string.io;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.Reader;

import org.junit.Test;

public class BasePathToStringLoaderTest {
	
	@Test
    public void ioExceptionSilentFailAsString() throws Exception {
		BasePathToStringLoader unit = new BasePathToStringLoader() {
			@Override
			protected Reader getReader(String fileName) throws FileNotFoundException {
				return new ThrowsWhenReadIsCalledReader();
			}
		};
	    assertEquals("", unit.asString("whatever"));
    }

	@Test
    public void ioExceptionAndCloseSilentFailAsString() throws Exception {
		BasePathToStringLoader unit = new BasePathToStringLoader() {
			@Override
			protected Reader getReader(String fileName) throws FileNotFoundException {
				return new ThrowsWhenReadOrCloseIsCalledReader();
			}
		};
	    assertEquals("", unit.asString("whatever"));
    }
	
	@Test
    public void ioExceptionSilentFailAsList() throws Exception {
		BasePathToStringLoader unit = new BasePathToStringLoader() {
			@Override
			protected Reader getReader(String fileName) throws FileNotFoundException {
				return new ThrowsWhenReadIsCalledReader();
			}
		};
	    assertEquals(0, unit.asList("whatever").size());
    }

	@Test
    public void ioExceptionAndCloseSilentFailAsList() throws Exception {
		BasePathToStringLoader unit = new BasePathToStringLoader() {
			@Override
			protected Reader getReader(String fileName) throws FileNotFoundException {
				return new ThrowsWhenReadOrCloseIsCalledReader();
			}
		};
	    assertEquals(0, unit.asList("whatever").size());
    }

}
