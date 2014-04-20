package uk.co.itstherules.yawf.inbound;

import org.junit.Assert;
import org.junit.Test;

public class UrlTokenizerTest {
	
	@Test public void emptyString() {
		UrlTokenizer unit = new UrlTokenizer("");
		Assert.assertFalse(unit.hasNext());
    }

	@Test public void oneSlash() {
		UrlTokenizer unit = new UrlTokenizer("/");
		Assert.assertFalse(unit.hasNext());
    }


	@Test public void oneThing() {
		UrlTokenizer unit = new UrlTokenizer("/Big");
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Big", unit.nextToken());
    }

	@Test public void twoThings() {
		UrlTokenizer unit = new UrlTokenizer("/Big/Bad");
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Big", unit.nextToken());
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Bad", unit.nextToken());
		Assert.assertFalse(unit.hasNext());
    }
	

	@Test public void multipleThings() {
		UrlTokenizer unit = new UrlTokenizer("/Big/Bad/Badgers/Beat/Squirrels");
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Big", unit.nextToken());
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Bad", unit.nextToken());
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Badgers", unit.nextToken());
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Beat", unit.nextToken());
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Squirrels", unit.nextToken());
		
		Assert.assertFalse(unit.hasNext());
    }
	
	@Test public void ignoreQuestionMarks() {
		UrlTokenizer unit = new UrlTokenizer("/Big/Bad/Badgers/Beat/Squirrels?okay");
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Big", unit.nextToken());
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Bad", unit.nextToken());
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Badgers", unit.nextToken());
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Beat", unit.nextToken());
		Assert.assertTrue(unit.hasNext());
		Assert.assertEquals("Squirrels?okay", unit.nextToken());
		
		Assert.assertFalse(unit.hasNext());
    }
}
