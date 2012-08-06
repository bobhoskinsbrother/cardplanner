package uk.co.itstherules.string.crypto;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import uk.co.itstherules.string.crypto.Key.CipherType;
import uk.co.itstherules.string.crypto.Key.Scope;

public class KeysGenerator {

	private static final String ISO_8859_1 = "ISO-8859-1";

	public Keys generate(GeneratorAndCipherType cipherType) throws Exception {
		Key publicKey = null;
		Key privateKey = null;
		
		if(cipherType == CipherType.Blowfish) {
	        KeyGenerator keyGenerator = KeyGenerator.getInstance(cipherType.getKeyGeneratorType());
	        keyGenerator.init(128);
	        SecretKey key = keyGenerator.generateKey();
	        String encodedString = new String(key.getEncoded(), ISO_8859_1);
	        publicKey = new Key(Scope.Public, encodedString);
	        privateKey = new Key(Scope.Private, encodedString);
		}
		else if(cipherType == CipherType.RSA) {
	        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(cipherType.getKeyGeneratorType());
	        keyPairGenerator.initialize(2048);
	        KeyPair keyPair = keyPairGenerator.genKeyPair();
	        KeyFactory factory = KeyFactory.getInstance(cipherType.getKeyGeneratorType());
	        RSAPublicKeySpec publicKeySpec = factory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
	        RSAPrivateKeySpec privateKeySpec = factory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);
	        publicKey = new Key(Scope.Public, publicKeySpec.getModulus(), publicKeySpec.getPublicExponent());
	        privateKey = new Key(Scope.Private, privateKeySpec.getModulus(), privateKeySpec.getPrivateExponent());
		} else {
	        throw new RuntimeException("CipherType not recognised");
        }
		return new Keys(publicKey, privateKey);
		
	}

}
