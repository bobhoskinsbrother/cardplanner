package uk.co.itstherules.string.crypto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Key implements Serializable {
	
	private static final long serialVersionUID = -3270603415117755819L;
	private final GeneratorAndCipherType cipherType;
	private BigInteger modulus;
	private BigInteger exponent;
	private Scope scope;
	private byte[] byteArray;

	enum Scope {
		Public, Private
	}

	public enum CipherType implements GeneratorAndCipherType {
		Blowfish("Blowfish/ECB/PKCS5Padding"), RSA("RSA");
		private final String cipherType;
		private CipherType(String cipherType) { this.cipherType = cipherType; }
		@Override public String toString() { return cipherType; }
		public String getKeyGeneratorType() { return name(); }
	}

	public Key(Scope scope, String password) {
		this.scope = scope;
		this.cipherType = CipherType.Blowfish;
		try {
			this.byteArray = password.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public Key(Scope scope, BigInteger modulus, BigInteger exponent) {
		this.cipherType = CipherType.RSA;
		this.scope = scope;
		this.modulus = modulus;
		this.exponent = exponent;
	}

	public boolean isPublic() {
		return scope == Scope.Public;
	}

	public boolean isPrivate() {
		return scope == Scope.Private;
	}

	public BigInteger getExponent() {
		if(exponent==null) {
			throw new NullPointerException("Exponent is null");
		}
		return exponent;
	}

	public BigInteger getModulus() {
		if(modulus==null) {
			throw new NullPointerException("Modulus is null");
		}
		return modulus;
	}

	public GeneratorAndCipherType getCipherType() {
		return cipherType;
	}

	public byte[] toByteArray() {
		if(byteArray==null) {
			throw new NullPointerException("ByteArray is null");
		}
		return byteArray;
	}
}
