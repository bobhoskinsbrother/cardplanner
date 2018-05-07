package uk.co.itstherules.yawf.inbound;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class MultipleNameValuesParserTest {
	
	@Test public void one() {
		String nameValue = "[ben=fab]";
		Map<String, String> reply = MultipleNameValuesParser.parse(nameValue);
		Assert.assertEquals("fab", reply.get("ben"));
    }
	
	@Test public void manyWithOptionalBrackets() {
		String nameValue = "[ben=neb,indah=hadni]";
		Map<String, String> reply = MultipleNameValuesParser.parse(nameValue);
		Assert.assertEquals("neb", reply.get("ben"));
		Assert.assertEquals("hadni", reply.get("indah"));
    }
	
	@Test public void manyWithExtraOptionalBrackets() {
		String nameValue = "[[ben=neb],[indah=hadni]]";
		Map<String, String> reply = MultipleNameValuesParser.parse(nameValue);
		Assert.assertEquals("neb", reply.get("ben"));
		Assert.assertEquals("hadni", reply.get("indah"));
    }
	
	@Test(expected=IllegalArgumentException.class) public void broken() {
		String nameValue = "[ben=,neb;indah=hadni=,]";
		MultipleNameValuesParser.parse(nameValue);
    }
	
	@Test public void manyWithNoBrackets() {
		String nameValue = "ben=neb,indah=hadni";
		Map<String, String> reply = MultipleNameValuesParser.parse(nameValue);
		Assert.assertEquals("neb", reply.get("ben"));
		Assert.assertEquals("hadni", reply.get("indah"));
    }
	
	@Test public void secondEqualsDelimitsString() {
		String nameValue = "ben=neb=indah=hadni";
		Map<String, String> reply = MultipleNameValuesParser.parse(nameValue);
		Assert.assertEquals("neb", reply.get("ben"));
		Assert.assertEquals(null, reply.get("indah"));
    }
	
	@Test(expected=IllegalArgumentException.class) public void allCommasBadMana() {
		String nameValue = "ben,neb,indah,hadni";
		MultipleNameValuesParser.parse(nameValue);
    }
	
}
