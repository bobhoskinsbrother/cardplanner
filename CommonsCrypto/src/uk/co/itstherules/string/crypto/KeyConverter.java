package uk.co.itstherules.string.crypto;

import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.spec.SecretKeySpec;

import uk.co.itstherules.string.crypto.Key.CipherType;


public class KeyConverter {
	
	public java.security.Key convert(Key key) {
		try {
			GeneratorAndCipherType cipherType = key.getCipherType();
			if(cipherType == CipherType.Blowfish) {
		        return new SecretKeySpec(key.toByteArray(), cipherType.getKeyGeneratorType());
			} else if(cipherType == CipherType.RSA) {
	        	KeySpec keySpec = null;
	        	KeyFactory factory = KeyFactory.getInstance(cipherType.getKeyGeneratorType());
	        	java.security.Key replyKey = null;
	        	if(key.isPublic()) {
	        		keySpec = new RSAPublicKeySpec(key.getModulus(), key.getExponent());
	 	        	replyKey = factory.generatePublic(keySpec);
	        	} else {
	        		keySpec = new RSAPrivateKeySpec(key.getModulus(), key.getExponent());
	 	        	replyKey = factory.generatePrivate(keySpec);
	        	}
	        	return replyKey;
	        }
		} catch (Exception e) {
		}
		throw new RuntimeException();
	}

}
