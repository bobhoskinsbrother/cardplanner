package uk.co.itstherules.string.manipulation;

import javax.crypto.Cipher;

import uk.co.itstherules.string.crypto.Key;
import uk.co.itstherules.string.crypto.KeyConverter;

public class ScrambleUnscramble {

	private static ThreadLocal<Key> keyThread = new ThreadLocal<Key>();
	
	private static final String ISO_8859_1 = "ISO-8859-1";

	public ScrambleUnscramble(Key key) {
		keyThread.set(key);
	}
	
	public String manipulate(String text) {
		try {
			Cipher cipher = Cipher.getInstance(keyThread.get().getCipherType().toString());
			java.security.Key key = new KeyConverter().convert(keyThread.get());
			if(keyThread.get().isPrivate()) {
				cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
			} else {
				cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
			}
			byte[] changedText = cipher.doFinal(text.getBytes(ISO_8859_1));
			return new String(changedText, ISO_8859_1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

}
