package uk.co.itstherules.string.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public final class FilePathToStringLoader extends BasePathToStringLoader {

	@Override
    protected Reader getReader(String fileName) throws FileNotFoundException {
		 return new FileReader(fileName);
    }

}