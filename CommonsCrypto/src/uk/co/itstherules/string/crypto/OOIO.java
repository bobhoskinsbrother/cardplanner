package uk.co.itstherules.string.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OOIO {

	
	public static synchronized byte[] serialise(Object object) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(byteArrayOutputStream));
		oout.writeObject(object);
		oout.flush();
		oout.close();
		return byteArrayOutputStream.toByteArray();
	}
	
	public static synchronized Object deserialise(byte[] bytes) throws IOException, ClassNotFoundException {
		ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes)));
		Object object = input.readObject();
		input.close();
		return object;
	}
	
	public static synchronized void write(String fileName, Object object) throws IOException {
		ObjectOutputStream oout = 
			new ObjectOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(fileName)
					)
			);
		oout.writeObject(object);
		oout.flush();
		oout.close();
	}
	
	public static synchronized Object read(String fileName) throws Exception {
		InputStream in = OOIO.class.getClassLoader().getResourceAsStream(fileName);
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
		Object object = oin.readObject();
		oin.close();
		return object;
	}

}
