package uk.co.itstherules.yawf.controller;

import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.yawf.FakeResponse;
import freemarker.template.SimpleHash;

public class BaseTest {
	
	@Test public void minimalListUrl() {
		BaseController unit = new TestBaseImpl();
		Assert.assertEquals("/FootRoot/TestBaseImpl/List/0/", unit.listUrl("/FootRoot"));
	}

	@Test public void listUrlWithTitle() {
		BaseController unit = new TestBaseImpl();
		Assert.assertEquals("/FootRoot/TestBaseImpl/List/0/KingOfRock", unit.listUrl("KingOfRock", "/FootRoot"));
	}

	@Test public void listUrlWithTitleAndIdentity() {
		BaseController unit = new TestBaseImpl();
		Assert.assertEquals("/FootRoot/TestBaseImpl/List/10101012/KingOfRock", unit.listUrl("KingOfRock", "10101012", "/FootRoot"));
	}
	
	@Test public void showUrlWithTitle() {
		BaseController unit = new TestBaseImpl();
		Assert.assertEquals("/FootRoot/TestBaseImpl/Show/0/KingOfRock", unit.showUrl("KingOfRock", "/FootRoot"));
	}

	@Test public void showUrlWithTitleAndIdentity() {
		BaseController unit = new TestBaseImpl();
		Assert.assertEquals("/FootRoot/TestBaseImpl/Show/999666007/KingOfRock", unit.showUrl("KingOfRock", "999666007", "/FootRoot"));
	}

	@Test public void showUrlWithTitleIdentityAndParams() {
		Map<String, String> params = new TreeMap<String, String>();
		params.put("hairy", "badgers");
		params.put("squirrels", "collectNuts");
		BaseController unit = new TestBaseImpl();
		Assert.assertEquals("/FootRoot/TestBaseImpl/Show/0/KingOfRock?hairy=badgers&amp;squirrels=collectNuts", unit.showUrl("KingOfRock", "/FootRoot", new SimpleHash(params)));
	}
	
	@Test public void defaultFeedRepliesWithIdentityMinusOne() throws Exception {
		BaseController unit = new TestBaseImpl();
		StringWriter writer = new StringWriter();
		FakeResponse response = new FakeResponse(writer);
		unit.feed(null, null, response, null);
		Assert.assertEquals("{\"identity\":\"0\"}", writer.toString());
		Assert.assertEquals("application/json", response.getContentType());
    }

}
