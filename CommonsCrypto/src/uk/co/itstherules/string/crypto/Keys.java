package uk.co.itstherules.string.crypto;

public class Keys {
	
	private final Key publicKey;
	private final Key privateKey;

	public Keys(Key publicKey, Key privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
    }
	public Key getPrivateKey() { return privateKey; }
	public Key getPublicKey() { return publicKey; }
}
