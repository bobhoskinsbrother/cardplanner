package uk.co.itstherules.reflection;

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

import uk.co.itstherules.string.manipulation.Chop;

public class ResourceFinder {

	public List<String> collectResourcesFromAllPackagesNamed(String packageName) throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> fullQualifiedPackages = classLoader.getResources(packageName.replace('.', '/'));
		List<String> classes = new ArrayList<String>();
		
		while (fullQualifiedPackages.hasMoreElements()) {
			try {
	            String filePath = fullQualifiedPackages.nextElement().toURI().toString();
	    		if (filePath.contains("!/")) {
    				classes.addAll(collectResourcesFromPackageInJar(packageName, filePath));
	    		} else {
	    			classes.addAll(collectResourcesFromPackageInFileSystem(packageName, filePath));
	    		}
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}
		return Collections.unmodifiableList(classes);
	}

	private List<String> collectResourcesFromPackageInFileSystem(String packageName, String uriPath) throws URISyntaxException {
		List<String> classes = new ArrayList<String>();
		collectResourcesFromPackageInFileSystem(classes, packageName, uriPath);
	    return classes;
    }
	
	private void collectResourcesFromPackageInFileSystem(List<String> collectedFiles, String packageName, String uriPath) throws URISyntaxException {
	    File directory = new File(new URI(uriPath));
	    if (!directory.exists()) { return; }
	    File[] files = directory.listFiles();
	    for (File file : files) {
    		String fileName = file.getName();
			if(file.isDirectory()) {
    			collectResourcesFromPackageInFileSystem(collectedFiles, packageName+"."+fileName, uriPath+"/"+fileName);
    		} else { 
    			collectedFiles.add(directory.getAbsolutePath() + '/' + fileName);
    		}
	    }
    }

	private List<String> collectResourcesFromPackageInJar(String packageName, String jarFilePath) throws URISyntaxException {
		List<String> classes = new ArrayList<String>();
		if (jarFilePath.contains("!/")) {
			String[] directoryPath = jarFilePath.split("!/");
			jarFilePath = directoryPath[0];
		}
	    try {
	    	jarFilePath = new Chop("jar:").manipulate(jarFilePath);
	    	JarFile jarFile = new JarFile(new File(new URI(jarFilePath))); 
	    	Enumeration<JarEntry> entries = jarFile.entries();
	    	while (entries.hasMoreElements()) {
	    		JarEntry jarEntry = (JarEntry) entries.nextElement();
	    		if(jarEntry.isDirectory()) { continue; }
	    		String candidateFile = jarEntry.getName().replace('/', '.');
	    		if (candidateFile.startsWith(packageName)) {
	    			classes.add(candidateFile);
	    		}
	    	}
	    } catch (IOException e) {
        }
	    return classes;
    }
}
