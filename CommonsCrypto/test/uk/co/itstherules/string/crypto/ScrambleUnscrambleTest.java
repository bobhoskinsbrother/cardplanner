package uk.co.itstherules.string.crypto;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import uk.co.itstherules.string.crypto.Key.Scope;
import uk.co.itstherules.string.manipulation.ScrambleUnscramble;

public class ScrambleUnscrambleTest {
	
	@Test
    public void canScrambleUnscrambleWithRSA() throws Exception {
		BigInteger publicExponent = new BigInteger("65537");
		BigInteger privateExponent = new BigInteger("6944619464987247030102990101005926605661911186414341443743608994648105680450026112672657203924928490513252509980579679604027725424823038249375975523998017305870973650588339149037177433160221957508475503729414298991562217968521795270208109988213391787283340025681553905914899265141116450718460486272421885595703321198515063737182789534942018613197373552292144348032586328108269979055526604520162665929284313381998807563359959908589403996261095852803087275732122617815763357175123488108058995637087367953948768564153886930544277639471564450929751622124225448581967278836296837221525681084546609045144403420425917854953");
		BigInteger modulus = new BigInteger("16511138250566631910461079711577196152920902355306972436010335667776270704866800701840302382500563703347253029080255775882792129191606292680912508903183677133134990028609032570667494918810864009767203304477222017522583460148848644898372171387540034738370696726395501481296744173392104075121920728780544644160813253847010698154348487341508618406483743334593231986210899553664464490835987512904936339349957219818649712360927138833785025234592994418105878094014870916684500080715827954167682549373584428322504057914760598228345948936051954149900211112404442596076547992477276135842829674855532989751873309135279293455151");
		
		Key publicKey = new Key(Scope.Public, modulus, publicExponent);
		Key privateKey = new Key(Scope.Private, modulus, privateExponent);
		
		ScrambleUnscramble unit=new ScrambleUnscramble(publicKey);
		String reply = unit.manipulate("Hey, how u doin?");
		assertFalse("Hey, how u doin?".equals(reply));
		
		unit = new ScrambleUnscramble(privateKey);
		reply = unit.manipulate(reply);
		assertTrue("Hey, how u doin?".equals(reply));
    }
	
	@Test
    public void canScrambleUnscrambleWithBlowfish() throws Exception {
		String hash = new String(new byte[]{-92,-44,88,92,31,-104,120,-2,-117,15,98,76,38,-42,-29,-50}, "ISO-8859-1");
		
		Key publicKey = new Key(Scope.Public, hash);
		Key privateKey = new Key(Scope.Private, hash);
		
		ScrambleUnscramble unit=new ScrambleUnscramble(publicKey);
		String reply = unit.manipulate("Hey, how u doin?");
		assertFalse("Hey, how u doin?".equals(reply));
		
		unit = new ScrambleUnscramble(privateKey);
		reply = unit.manipulate(reply);
		assertTrue("Hey, how u doin?".equals(reply));
    }
	
	@Test(expected=RuntimeException.class)
    public void cannotScrambleUnscrambleWithBrokenKeys() throws Exception {
		BigInteger publicExponent = new BigInteger("7");
		BigInteger privateExponent = new BigInteger("6");
		BigInteger modulus = new BigInteger("1");
		
		Key publicKey = new Key(Scope.Public, modulus, publicExponent);
		Key privateKey = new Key(Scope.Private, modulus, privateExponent);
		
		ScrambleUnscramble unit=new ScrambleUnscramble(publicKey);
		String reply = unit.manipulate("Hey, how u doin?");
		assertFalse("Hey, how u doin?".equals(reply));
		
		unit = new ScrambleUnscramble(privateKey);
		reply = unit.manipulate(reply);
		assertTrue("Hey, how u doin?".equals(reply));
    }
	
}
