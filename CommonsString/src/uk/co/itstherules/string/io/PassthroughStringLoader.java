package uk.co.itstherules.string.io;

import java.io.FileNotFoundException;

public final class PassthroughStringLoader implements StringLoader {

	public String asString(String string) throws FileNotFoundException { return string; }

}