package uk.co.itstherules.reflection;

import java.io.File;
import java.io.FileFilter;

public class ClassAndDirectoryFilter implements FileFilter {
	private static final String _CLASS = ".class";

	public boolean accept(File pathname) {
		return (pathname.getName().endsWith(_CLASS) || pathname.isDirectory());
	}
}
