package uk.co.itstherules.string.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.co.itstherules.string.crypto.Key.CipherType;

public class KeyTest {
	
	@Test(expected=NullPointerException.class)
    public void nullModulus() throws Exception {
	    Key key = new Key(null, null, null);
	    key.getModulus();
    }

	@Test(expected=NullPointerException.class)
    public void nullExponent() throws Exception {
	    Key key = new Key(null, null, null);
	    key.getExponent();
    }

	@Test public void cipherToString() {
		assertEquals("Blowfish/ECB/PKCS5Padding", CipherType.Blowfish.toString()); 
		assertEquals("RSA", CipherType.RSA.toString()); 
    }

}
