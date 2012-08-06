package uk.co.itstherules.cardplanner.javascript.runner;

import java.io.File;
import java.io.FileFilter;

public class JSTestAndDirectoryFilter implements FileFilter {
	
	public static final String END_IDENTIFIER = "test.js";
	
	@Override public boolean accept(File file) {
		return file.getName().endsWith(END_IDENTIFIER) || file.isDirectory();
	}
}
