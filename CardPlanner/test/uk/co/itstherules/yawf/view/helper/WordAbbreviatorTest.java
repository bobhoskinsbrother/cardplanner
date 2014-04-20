package uk.co.itstherules.yawf.view.helper;

import org.junit.Assert;
import org.junit.Test;

public class WordAbbreviatorTest {
	
	@Test public void canAbbreviate() {
		WordAbbreviator unit = new WordAbbreviator();
		String reply = unit.abbreviate("In The Beginning There Was Human", 2);
		Assert.assertEquals("In The...", reply);
		reply = unit.abbreviate("In The Beginning There Was Human", 3);
		Assert.assertEquals("In The Beginning...", reply);
		reply = unit.abbreviate("In The Beginning There Was Human", 0);
		Assert.assertEquals("...", reply);
		reply = unit.abbreviate("In The Beginning There Was Human", 5);
		Assert.assertEquals("In The Beginning There Was...", reply);
		reply = unit.abbreviate("In The Beginning There Was Human", 6);
		Assert.assertEquals("In The Beginning There Was Human", reply);
		reply = unit.abbreviate("In The Beginning There Was Human", 10);
		Assert.assertEquals("In The Beginning There Was Human", reply);
    }
	
}
