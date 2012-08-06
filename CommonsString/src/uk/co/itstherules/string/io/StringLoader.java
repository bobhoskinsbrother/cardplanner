package uk.co.itstherules.string.io;

import java.io.FileNotFoundException;

public interface StringLoader {
	public String asString(String fileName) throws FileNotFoundException;
}
