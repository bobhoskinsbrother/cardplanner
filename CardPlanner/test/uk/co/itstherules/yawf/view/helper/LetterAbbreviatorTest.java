package uk.co.itstherules.yawf.view.helper;

import org.junit.Assert;
import org.junit.Test;

public class LetterAbbreviatorTest {
	@Test public void canAbbreviate() {
		LetterAbbreviator unit = new LetterAbbreviator();
		String reply = unit.abbreviate("In The Beginning There Was Human", 9);
		Assert.assertEquals("In The...", reply);
		reply = unit.abbreviate("In The Beginning There Was Human", 19);
		Assert.assertEquals("In The Beginning...", reply);
		reply = unit.abbreviate("In The Beginning There Was Human", 0);
		Assert.assertEquals("...", reply);
		reply = unit.abbreviate("In The Beginning There Was Human", 31);
		Assert.assertEquals("In The Beginning There Was...", reply);
		reply = unit.abbreviate("In The Beginning There Was Human", 32);
		Assert.assertEquals("In The Beginning There Was Human", reply);
		reply = unit.abbreviate("In The Beginning There Was Human", 50);
		Assert.assertEquals("In The Beginning There Was Human", reply);
    }
}
