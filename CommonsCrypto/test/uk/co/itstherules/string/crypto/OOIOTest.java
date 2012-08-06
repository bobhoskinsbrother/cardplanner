package uk.co.itstherules.string.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.Serializable;

import org.junit.Test;

public class OOIOTest {

	
	@Test public void canSerialiseAndDeserialiseObject() throws Exception {
		byte[] reply = OOIO.serialise(new Faker("Big Daddy"));
		Object object = OOIO.deserialise(reply);
		assertEquals("Big Daddy", ((Faker)object).name);
		reply = OOIO.serialise(new Faker("lil Indah"));
		object = OOIO.deserialise(reply);
		assertEquals("lil Indah", ((Faker)object).name);
	}
	
	
	@Test public void canReadObject() throws Exception {
		try {
			Object object = OOIO.read("who_am_i");
			assertEquals("Big Daddy", ((Faker)object).name);
		} finally {
			new File("who_am_i").delete();
		}
	}
	
	@Test public void canWriteObject() throws Exception {
		File file = new File("who_am_i");
		try {
			OOIO.write("who_am_i", new Faker("lil Indah"));
			assertTrue(file.exists());
		} finally {
			file.delete();
		}
	}
	
	private static final class Faker implements Serializable {
		private static final long serialVersionUID = -4646068861126405140L;
		private final String name;
		public Faker(String name) { this.name = name; }
		
	}
}
