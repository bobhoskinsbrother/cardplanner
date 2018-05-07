package uk.co.itstherules.string.io;

import java.io.IOException;
import java.io.Reader;

public class ThrowsWhenReadIsCalledReader extends Reader {
	@Override
	public void close() throws IOException {
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		throw new IOException("Never implemented");
	}
}
