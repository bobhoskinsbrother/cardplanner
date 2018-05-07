package uk.co.itstherules.string.io;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

public class InputStreamToStringLoaderTest {
	
	@Test
    public void canLoadAsString() throws Exception {
		InputStream stream = InputStreamToStringLoaderTest.class.getClassLoader().getResourceAsStream("uk/co/itstherules/string/io/resourceloaded.txt");
		InputStreamToStringLoader unit = new InputStreamToStringLoader(stream);
		assertEquals("hi" +System.getProperty("line.separator")+"what's happening?", unit.asString(null));
    }
	
	@Test(expected=FileNotFoundException.class)
    public void cannotAsDoesntExistAsString() throws Exception {
		InputStream stream = InputStreamToStringLoaderTest.class.getClassLoader().getResourceAsStream("uk/co/itstherules/string/io/i_dont_exist");
		InputStreamToStringLoader unit = new InputStreamToStringLoader(stream);
	    unit.asString(null);
    }
	
	@Test
    public void canLoadTextFileAsList() throws Exception {
		InputStream stream = InputStreamToStringLoaderTest.class.getClassLoader().getResourceAsStream("uk/co/itstherules/string/io/resourceloaded.txt");
		InputStreamToStringLoader unit = new InputStreamToStringLoader(stream);
	    List<String> reply = unit.asList("uk/co/itstherules/string/io/resourceloaded.txt");
		assertEquals("hi", reply.get(0));
		assertEquals("what's happening?", reply.get(1));
    }
	
	@Test(expected=FileNotFoundException.class)
    public void cannotAsDoesntExistAsList() throws Exception {
		InputStream stream = InputStreamToStringLoaderTest.class.getClassLoader().getResourceAsStream("uk/co/itstherules/string/io/i_dont_exist");
		InputStreamToStringLoader unit = new InputStreamToStringLoader(stream);
	    unit.asList(null);
    }
}
