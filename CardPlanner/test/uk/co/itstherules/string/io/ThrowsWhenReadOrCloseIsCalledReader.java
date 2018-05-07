package uk.co.itstherules.string.io;

import java.io.IOException;

public class ThrowsWhenReadOrCloseIsCalledReader extends ThrowsWhenReadIsCalledReader {
	@Override public void close() throws IOException {
		throw new IOException("Never implemented");
	}
}
