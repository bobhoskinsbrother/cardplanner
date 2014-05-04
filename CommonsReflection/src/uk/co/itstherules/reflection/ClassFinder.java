package uk.co.itstherules.reflection;

import uk.co.itstherules.string.manipulation.Chomp;
import uk.co.itstherules.string.manipulation.Chop;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder {
	private static final String _CLASS = ".class";

	public List<Class<?>> collectClassesFromAllPackagesNamed(String packageName) throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> fullQualifiedPackages = classLoader.getResources(packageName.replace('.', '/'));
		List<Class<?>> classes = new ArrayList<Class<?>>();
		
		while (fullQualifiedPackages.hasMoreElements()) {
			try {
	            String filePath = fullQualifiedPackages.nextElement().toURI().toString();
	    		if (filePath.contains("!/")) {
	    			classes.addAll(collectClassesFromPackageInJar(packageName, filePath));
	    		} else {
	    			classes.addAll(collectClassesFromPackageInFileSystem(packageName, filePath));
	    		}
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}
		return Collections.unmodifiableList(classes);
	}

	private List<Class<?>> collectClassesFromPackageInFileSystem(String packageName, String uriPath) throws URISyntaxException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		collectClassesFromPackageInFileSystem(classes, packageName, uriPath);
	    return classes;
    }
	
	private void collectClassesFromPackageInFileSystem(List<Class<?>> classes, String packageName, String uriPath) throws URISyntaxException {
	    File directory = new File(new URI(uriPath));
	    if (!directory.exists()) { return; }
	    File[] files = directory.listFiles(new ClassAndDirectoryFilter());
	    for (File file : files) {
	    	try {
	    		String fileName = file.getName();
				if(file.isDirectory()) {
	    			collectClassesFromPackageInFileSystem(classes, packageName+"."+fileName, uriPath+"/"+fileName);
	    		} else { 
	    			classes.add(Class.forName(packageName + '.' + new Chomp(_CLASS).manipulate(fileName)));
	    		}
            } catch (ClassNotFoundException e) {
            }
	    }
    }

	private List<Class<?>> collectClassesFromPackageInJar(String packageName, String jarFilePath) throws URISyntaxException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (jarFilePath.contains("!/")) {
			String[] directoryPath = jarFilePath.split("!/");
			jarFilePath = directoryPath[0];
		}
	    try {
	    	jarFilePath = new Chop("jar:").manipulate(jarFilePath);
	    	JarFile jarFile = new JarFile(new File(new URI(jarFilePath))); 
	    	Enumeration<JarEntry> entries = jarFile.entries();
	    	while (entries.hasMoreElements()) {
	    		JarEntry jarEntry = entries.nextElement();
	    		if(jarEntry.isDirectory()) { continue; }
	    		String candidateClass = jarEntry.getName().replace('/', '.');
	    		if (candidateClass.startsWith(packageName) && candidateClass.endsWith(_CLASS)) {
	    			classes.add(Class.forName(new Chomp(_CLASS).manipulate(candidateClass)));
	    		}
	    	}
	    } catch (IOException ignored) {
	    } catch (ClassNotFoundException ignored) {
        }
	    return classes;
    }
}
