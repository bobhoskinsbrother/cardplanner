package uk.co.itstherules.string.url;

import org.junit.Assert;
import org.junit.Test;

public class UrlTest {
	
	@Test public void canCreateBase() {
		Assert.assertEquals("http://badgers/are", Url.base("http://badgers/are/hairy", "/are"));
		Assert.assertEquals("http://badgers:9999/are", Url.base("http://badgers:9999/are/hairy", "/are"));
	}

	@Test public void cannotCreateBaseMalformed() {
		Assert.assertEquals("", Url.base("badgers/are/hairy", ""));
	}

	@Test public void cannotCreateBaseNull() {
		Assert.assertEquals("", Url.base(null, ""));
	}

}
