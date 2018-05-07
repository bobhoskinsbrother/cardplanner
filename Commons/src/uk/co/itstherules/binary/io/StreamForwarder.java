package uk.co.itstherules.binary.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamForwarder {
	public void forward(InputStream inputStream, OutputStream outputStream) {
		if (inputStream != null) {
			try {
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
				byte[] buf = new byte[2048];
				while (true) {
					int readValue = inputStream.read(buf, 0, buf.length);
					if (readValue == -1) { break; }
					bufferedOutputStream.write(buf, 0, readValue);
				}
				bufferedOutputStream.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
