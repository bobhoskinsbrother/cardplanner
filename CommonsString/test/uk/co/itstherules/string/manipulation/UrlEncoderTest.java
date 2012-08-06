package uk.co.itstherules.string.manipulation;

import org.junit.Assert;
import org.junit.Test;

public class UrlEncoderTest {
	
	@Test public void canEncode() {
		UrlEncoder unit  = new UrlEncoder();
		Assert.assertEquals("%3B%3F%2F%3A%23%26%3D%2B%24%2C+%25%3C%3E%7E+fredd", unit.manipulate(";?/:#&=+$, %<>~ fredd"));
		Assert.assertEquals("%3B%3F%2F%3A%23%26%3D+fredd+%2B%24%2C+%25%3C%3E%7E+", unit.manipulate(";?/:#&= fredd +$, %<>~ "));
		
	}
}



