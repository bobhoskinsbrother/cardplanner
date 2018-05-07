package uk.co.itstherules.string.url;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class QueryStringParserTest {
	
	@Test public void canParseNoQuestionMark() throws Exception {
		QueryStringParser unit = new QueryStringParser();
		Map<String, String> reply = unit.parse("fred=flintstone&betty=rubble");
		Assert.assertEquals("flintstone", reply.get("fred"));
		Assert.assertEquals("rubble", reply.get("betty"));
    }

	@Test(expected=IllegalArgumentException.class) public void cannotParseNull() throws Exception {
		QueryStringParser unit = new QueryStringParser();
		unit.parse(null);
    }


	@Test public void canParseJustQuestionMarkAndKey() throws Exception {
		QueryStringParser unit = new QueryStringParser();
		Map<String, String> reply = unit.parse("?fred");
		Assert.assertEquals("", reply.get("fred"));
    }

	@Test public void canParseJustKey() throws Exception {
		QueryStringParser unit = new QueryStringParser();
		Map<String, String> reply = unit.parse("fred");
		Assert.assertEquals("", reply.get("fred"));
    }

	@Test public void canParseJustValue() throws Exception {
		QueryStringParser unit = new QueryStringParser();
		Map<String, String> reply = unit.parse("=wilbur");
		Assert.assertEquals(0, reply.keySet().size());
    }

	@Test public void canParseJustEquals() throws Exception {
		QueryStringParser unit = new QueryStringParser();
		Map<String, String> reply = unit.parse("=");
		Assert.assertEquals(0, reply.keySet().size());
    }

	@Test public void canParseEqualsAndNextValues() throws Exception {
		QueryStringParser unit = new QueryStringParser();
		Map<String, String> reply = unit.parse("=&fred=flintstone");
		Assert.assertEquals(1, reply.keySet().size());
		Assert.assertEquals("flintstone", reply.get("fred"));
    }

	@Test public void canParseNoQuestionMarkTwoValuesTheSame() throws Exception {
		QueryStringParser unit = new QueryStringParser();
		Map<String, String> reply = unit.parse("fred=flintstone&fred=rubble");
		Assert.assertEquals("flintstone,rubble", reply.get("fred"));
    }


	@Test public void canParseWithQuestionMark() throws Exception {
		QueryStringParser unit = new QueryStringParser();
		Map<String, String> reply = unit.parse("Ben?fred=flintstone&betty=rubble");
		Assert.assertEquals("flintstone", reply.get("fred"));
		Assert.assertEquals("rubble", reply.get("betty"));
    }
	
	@Test public void canParseWithQuestionMarkAndSemiColonDelimiter() throws Exception {
		QueryStringParser unit = new QueryStringParser();
		Map<String, String> reply = unit.parse("Ben?fred=flintstone;betty=rubble", ";");
		Assert.assertEquals("flintstone", reply.get("fred"));
		Assert.assertEquals("rubble", reply.get("betty"));
    }
}
