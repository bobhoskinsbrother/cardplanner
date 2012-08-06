package uk.co.itstherules.string.crypto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import uk.co.itstherules.string.crypto.Key.CipherType;

public class KeysGeneratorTest {
	@Test
    public void canGenerateBlowfish() throws Exception {
		KeysGenerator unit = new KeysGenerator();
		Keys reply = unit.generate(CipherType.Blowfish);
	    assertNotNull(reply);
	    Key publicKey = reply.getPublicKey();
	    Key privateKey = reply.getPrivateKey();
		assertNotNull(publicKey);
		assertNotNull(privateKey);
		assertEquals(CipherType.Blowfish, publicKey.getCipherType());
		assertEquals(CipherType.Blowfish, privateKey.getCipherType());
		assertTrue(publicKey.isPublic());
		assertFalse(publicKey.isPrivate());
		assertTrue(privateKey.isPrivate());
		assertFalse(privateKey.isPublic());
		assertArrayEquals(publicKey.toByteArray(), privateKey.toByteArray());
    }
	
	@Test public void canGenerateRSA() throws Exception {
		KeysGenerator unit = new KeysGenerator();
		Keys reply = unit.generate(CipherType.RSA);
	    assertNotNull(reply);
	    Key publicKey = reply.getPublicKey();
	    Key privateKey = reply.getPrivateKey();
		assertNotNull(publicKey);
		assertNotNull(privateKey);
		assertEquals(CipherType.RSA, publicKey.getCipherType());
		assertEquals(CipherType.RSA, privateKey.getCipherType());
		assertTrue(publicKey.isPublic());
		assertFalse(publicKey.isPrivate());
		assertTrue(privateKey.isPrivate());
		assertFalse(privateKey.isPublic());
		try {
			publicKey.toByteArray();
			fail();
		} catch (NullPointerException e) {
		}
		try {
			privateKey.toByteArray();
			fail();
		} catch (NullPointerException e) {
		}
		assertFalse(publicKey.getExponent() == privateKey.getExponent());
		assertTrue(publicKey.getModulus() == privateKey.getModulus());
    }
	
	@Test(expected = RuntimeException.class) public void cannotGenerateUnknown() throws Exception {
		new KeysGenerator().generate(new UnknownCipherType());
	}
	
	private static final class UnknownCipherType implements GeneratorAndCipherType {

		@Override
        public String getKeyGeneratorType() {
			return "TODO: Not yet implemented";
        }
		
	}
}
