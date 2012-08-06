//$Id: ExplodedJarVisitor.java 14672 2008-05-17 12:50:57Z epbernard $
package org.hibernate.ejb.packaging;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


/**
 * @author Emmanuel Bernard
 */
public class ExplodedJarVisitor extends AbstractJarVisitor {
	private final Logger log = LoggerFactory.getLogger( ExplodedJarVisitor.class );
	private String entry;

	public ExplodedJarVisitor(URL url, Filter[] filters, String entry) {
		super( url, filters );
		this.entry = entry;
	}

	public ExplodedJarVisitor(String fileName, Filter[] filters) {
		super( fileName, filters );
	}

	protected void doProcessElements() throws IOException {
		File jarFile;
		try {
			String filePart = jarUrl.getFile();
			if ( filePart != null && filePart.indexOf( ' ' ) != -1 ) {
				//unescaped (from the container), keep as is
				jarFile = new File( jarUrl.getFile() );
			}
			else {
				jarFile = new File( jarUrl.toURI().getSchemeSpecificPart() );
			}
		}
		catch (URISyntaxException e) {
			log.warn( "Malformed url: " + jarUrl, e );
			return;
		}
		
		if ( !jarFile.exists() ) {
			log.warn( "Exploded jar does not exists (ignored): {}", jarUrl );
			return;
		}
		if ( !jarFile.isDirectory() ) {
			log.warn( "Exploded jar file not a directory (ignored): {}", jarUrl );
			return;
		}
		File rootFile;
		if (entry != null && entry.length() > 0 && ! "/".equals( entry ) ) {
			rootFile = new File(jarFile, entry);
		}
		else {
			rootFile = jarFile;
		}
		if ( rootFile.isDirectory() ) {
			getClassNamesInTree( rootFile, null );
		}
		else {
			//assume zipped file
			processZippedRoot(rootFile);
		}
	}

	//FIXME shameful copy of FileZippedJarVisitor.doProcess()
	//TODO long term fix is to introduce a process interface (closure like) to addElements and then share the code
	private void processZippedRoot(File rootFile) throws IOException {
		JarFile jarFile = new JarFile(rootFile);
		Enumeration<? extends ZipEntry> entries = jarFile.entries();
		while ( entries.hasMoreElements() ) {
			ZipEntry zipEntry = entries.nextElement();
			String name = zipEntry.getName();
			if ( !zipEntry.isDirectory() ) {
				//build relative name
				if ( name.startsWith( "/" ) ) name = name.substring( 1 );
				addElement(
						name,
						new BufferedInputStream( jarFile.getInputStream( zipEntry ) ),
						new BufferedInputStream( jarFile.getInputStream( zipEntry ) )
				);
			}
		}
	}

	private void getClassNamesInTree(File jarFile, String header) throws IOException {
		File[] files = jarFile.listFiles();
		header = header == null ? "" : header + "/";
		for ( File localFile : files ) {
			if ( !localFile.isDirectory() ) {
				String entryName = localFile.getName();
				addElement(
						header + entryName,
						new BufferedInputStream( new FileInputStream( localFile ) ),
						new BufferedInputStream( new FileInputStream( localFile ) )
				);

			}
			else {
				getClassNamesInTree( localFile, header + localFile.getName() );
			}
		}
	}
}
