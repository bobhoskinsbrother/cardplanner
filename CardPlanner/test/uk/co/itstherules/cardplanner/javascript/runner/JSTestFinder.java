package uk.co.itstherules.cardplanner.javascript.runner;

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

public class JSTestFinder {

	public List<String> collectTestsFromAllPackagesNamed(String packageName) throws IOException {
		packageName = packageName.replace('.', '/');
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> fullQualifiedPackages = classLoader.getResources(packageName.replace('.', '/'));
		List<String> filePaths = new ArrayList<String>();
		
		while (fullQualifiedPackages.hasMoreElements()) {
			try {
	            String filePath = fullQualifiedPackages.nextElement().toURI().toString();
	    		if (filePath.contains("!/")) {
	    			filePaths.addAll(collectFilePathsFromPackageInJar(packageName, filePath));
	    		} else {
	    			filePaths.addAll(collectFilePathsFromPackageInFileSystem(packageName, filePath));
	    		}
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}
		return Collections.unmodifiableList(filePaths);
	}

	private List<String> collectFilePathsFromPackageInFileSystem(String packageName, String uriPath) throws URISyntaxException {
		List<String> filePaths = new ArrayList<String>();
		collectFilePathsFromPackageInFileSystem(filePaths, packageName, uriPath);
	    return filePaths;
    }
	
	private void collectFilePathsFromPackageInFileSystem(List<String> fileNames, String packageName, String uriPath) throws URISyntaxException {
	    File directory = new File(new URI(uriPath));
	    if (!directory.exists()) { return; }
	    File[] files = directory.listFiles(new JSTestAndDirectoryFilter());
	    for (File file : files) {
    		String fileName = file.getName();
			if(file.isDirectory()) {
    			collectFilePathsFromPackageInFileSystem(fileNames, packageName+"/"+fileName, uriPath+"/"+fileName);
    		} else { 
    			fileNames.add(packageName + '/' + fileName);
    		}
	    }
    }

	private List<String> collectFilePathsFromPackageInJar(String packageName, String jarFilePath) throws URISyntaxException {
		List<String> filePaths = new ArrayList<String>();
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
	    		String candidateFilePath = jarEntry.getName();
	    		if (candidateFilePath.startsWith(packageName) && candidateFilePath.endsWith(JSTestAndDirectoryFilter.END_IDENTIFIER)) {
	    			filePaths.add(candidateFilePath);
	    		}
	    	}
	    } catch (IOException e) {
	    }
	    return filePaths;
    }
}
