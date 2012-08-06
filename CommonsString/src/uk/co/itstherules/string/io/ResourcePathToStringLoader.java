package uk.co.itstherules.string.io;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class ResourcePathToStringLoader extends BasePathToStringLoader {

	@Override
    protected Reader getReader(String fileName) throws FileNotFoundException {
		InputStream stream = ResourcePathToStringLoader.class.getClassLoader().getResourceAsStream(fileName);
		if(stream==null) { throw new FileNotFoundException(fileName + " is not found"); }
		 return new InputStreamReader(stream);
    }

}