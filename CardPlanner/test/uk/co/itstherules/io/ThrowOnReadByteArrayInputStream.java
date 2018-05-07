package uk.co.itstherules.io;

import java.io.IOException;
import java.io.InputStream;

public class ThrowOnReadByteArrayInputStream extends InputStream {

	@Override
	public synchronized int read(byte[] abyte0, int i, int j) throws IOException {
	    throw new IOException("On purpose");
	}

	@Override
    public int read() throws IOException {
	    throw new IOException("On purpose");
    }
}
