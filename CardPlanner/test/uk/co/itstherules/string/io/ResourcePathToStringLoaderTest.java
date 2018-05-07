package uk.co.itstherules.string.io;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

public class ResourcePathToStringLoaderTest {
	
	@Test
    public void canLoadTextFileAsString() throws Exception {
		ResourcePathToStringLoader unit = new ResourcePathToStringLoader();
	    assertEquals("hi"+System.getProperty("line.separator")+"what's happening?", unit.asString("uk/co/itstherules/string/io/resourceloaded.txt"));
    }
	
	@Test
    public void canLoadEmptyTextFileAsString() throws Exception {
		ResourcePathToStringLoader unit = new ResourcePathToStringLoader();
	    assertEquals("", unit.asString("uk/co/itstherules/string/io/empty.txt"));
    }
	
	@Test(expected=FileNotFoundException.class)
    public void cannotAsDoesntExistAsString() throws Exception {
		ResourcePathToStringLoader unit = new ResourcePathToStringLoader();
	    unit.asString("uk/co/itstherules/string/io/i_dont_exist");
    }
	
	@Test
    public void canResourceLoadTextFileAsList() throws Exception {
		ResourcePathToStringLoader unit = new ResourcePathToStringLoader();
	    List<String> reply = unit.asList("uk/co/itstherules/string/io/resourceloaded.txt");
		assertEquals("hi", reply.get(0));
		assertEquals("what's happening?", reply.get(1));
    }
	
	@Test(expected=FileNotFoundException.class)
    public void cannotAsDoesntExistAsList() throws Exception {
		ResourcePathToStringLoader unit = new ResourcePathToStringLoader();
	    unit.asList("uk/co/itstherules/string/io/i_dont_exist");
    }
}
