package uk.co.itstherules.binary.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class WarOutputStream extends JarOutputStream {

	public WarOutputStream(OutputStream outputStream, Manifest manifest, Map<String,byte[]> additional) throws IOException {
		this(outputStream, manifest);
		for (String key : additional.keySet()) {
			ZipEntry zipEntry = new ZipEntry(key);
			putNextEntry(zipEntry);
			byte[] data = additional.get(key);
			write(data, 0, data.length);
			closeEntry();
        }
		
    }
	
	public WarOutputStream(OutputStream outputStream, Manifest manifest) throws IOException {
		super(outputStream, manifest);
	}
	
	public WarOutputStream(OutputStream outputstream) throws IOException {
	    super(outputstream);
    }
	
	
}
