package uk.co.itstherules.binary.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Test;

public class StreamForwarderTest {
	
	@Test
    public void canForward() throws Exception {
	    byte[] input = {10, 23, 34, 47};
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		new StreamForwarder().forward(byteArrayInputStream, byteArrayOutputStream);
		byte[] reply = byteArrayOutputStream.toByteArray();
		assertEquals(input.length, reply.length);
		assertEquals(input[0], reply[0]);
		assertEquals(input[1], reply[1]);
		assertEquals(input[2], reply[2]);
		assertEquals(input[3], reply[3]);
    }
	
	@Test(expected=RuntimeException.class) public void willThrowOnIO() {
		InputStream byteArrayInputStream = new ThrowOnReadByteArrayInputStream();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		new StreamForwarder().forward(byteArrayInputStream, byteArrayOutputStream);
	}
}
