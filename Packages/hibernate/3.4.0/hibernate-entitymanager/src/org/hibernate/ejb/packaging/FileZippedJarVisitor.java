//$Id: FileZippedJarVisitor.java 14672 2008-05-17 12:50:57Z epbernard $
package org.hibernate.ejb.packaging;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Work on a JAR that can be accessed through a File
 *
 * @author Emmanuel Bernard
 */
public class FileZippedJarVisitor extends AbstractJarVisitor {
	private final Logger log = LoggerFactory.getLogger( FileZippedJarVisitor.class );
	private String entry;

	public FileZippedJarVisitor(String fileName, Filter[] filters) {
		super( fileName, filters );
	}

	public FileZippedJarVisitor(URL url, Filter[] filters, String entry) {
		super( url, filters );
		this.entry = entry;
	}

	protected void doProcessElements() throws IOException {
		JarFile jarFile;
		try {
			String filePart = jarUrl.getFile();
			if ( filePart != null && filePart.indexOf( ' ' ) != -1 ) {
				//unescaped (from the container), keep as is
				jarFile = new JarFile( jarUrl.getFile() );
			}
			else {
				jarFile = new JarFile( jarUrl.toURI().getSchemeSpecificPart() );
			}
		}
		catch (IOException ze) {
			log.warn( "Unable to find file (ignored): " + jarUrl, ze );
			return;
		}
		catch (URISyntaxException e) {
			log.warn( "Malformed url: " + jarUrl, e );
			return;
		}

		if ( entry != null && entry.length() == 1 ) entry = null; //no entry
		if ( entry != null && entry.startsWith( "/" ) ) entry = entry.substring( 1 ); //remove '/' header

		Enumeration<? extends ZipEntry> entries = jarFile.entries();
		while ( entries.hasMoreElements() ) {
			ZipEntry zipEntry = entries.nextElement();
			String name = zipEntry.getName();
			if ( entry != null && ! name.startsWith( entry ) ) continue; //filter it out
			if ( !zipEntry.isDirectory() ) {
				if ( name.equals( entry ) ) {
					//exact match, might be a nested jar entry (ie from jar:file:..../foo.ear!/bar.jar)
					/*
					 * This algorithm assumes that the zipped file is only the URL root (including entry), not just any random entry
					 */
					InputStream is = null;
					try {
						is = new BufferedInputStream( jarFile.getInputStream( zipEntry ) );
						JarInputStream jis = new JarInputStream( is );
						ZipEntry subZipEntry = jis.getNextEntry();
						while (subZipEntry != null) {
							if ( ! subZipEntry.isDirectory() ) {
								//FIXME copy sucks
								byte[] entryBytes = JarVisitorFactory.getBytesFromInputStream( jis );
								String subname = subZipEntry.getName();
								if ( subname.startsWith( "/" ) ) subname = subname.substring( 1 );
								addElement(
										subname,
										new ByteArrayInputStream(entryBytes),
										new ByteArrayInputStream(entryBytes)
								);
							}
							subZipEntry = jis.getNextEntry();
						}
					}
					finally {
						if ( is != null) is.close();
					}
				}
				else {
					//build relative name
					if (entry != null) name = name.substring( entry.length() );
					if ( name.startsWith( "/" ) ) name = name.substring( 1 );
					addElement(
							name,
							new BufferedInputStream( jarFile.getInputStream( zipEntry ) ),
							new BufferedInputStream( jarFile.getInputStream( zipEntry ) )
					);
				}
			}
		}
	}
}
