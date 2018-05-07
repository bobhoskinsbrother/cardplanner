package uk.co.itstherules.string.io;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class InputStreamToStringLoader  extends BasePathToStringLoader {

	private static final ThreadLocal<InputStream> streamThread = new ThreadLocal<InputStream>();
	
	public InputStreamToStringLoader(InputStream inputStream) {
		InputStreamToStringLoader.streamThread.set(inputStream);
    }
	
	@Override
    protected Reader getReader(String fileName) throws FileNotFoundException {
		InputStream stream = streamThread.get();
		if(stream==null) { throw new FileNotFoundException(fileName + " is not found"); }
		 return new InputStreamReader(stream);
    }
}
