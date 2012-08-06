package uk.co.itstherules.string.manipulation;

import org.junit.Assert;
import org.junit.Test;

public class PluraliserTest {
	
	@Test public void pluraliser() {
		Pluraliser unit = new Pluraliser();
		Assert.assertEquals("sheeps", unit.manipulate("sheep"));
		Assert.assertEquals("flies", unit.manipulate("flies"));
		Assert.assertEquals("Hoskins", unit.manipulate("Hoskins"));
	}
	
}
