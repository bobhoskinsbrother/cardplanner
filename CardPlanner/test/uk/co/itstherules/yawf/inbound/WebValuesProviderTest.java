package uk.co.itstherules.yawf.inbound;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Test;

public class WebValuesProviderTest {
	
	@Test public void canSetupDefaults() {
		Map<String, String> contextValues = new HashMap<String, String>();
		contextValues.put("defaultHomePage", "/HomeAndAway/TurnOff/0/IHateIt");
		contextValues.put("root", "");
		ServletContext context = new FakeServletContext(contextValues);

		Map<String, Object> requestValues = new HashMap<String, Object>();
		requestValues.put("uri", "/");
		requestValues.put("url", "http://www.hairybadgers.com");
		Map<String, String> parameters = new HashMap<String, String>();
		requestValues.put("method", "get");
		requestValues.put("parameters", parameters);
		FakeRequest request = new FakeRequest(requestValues);
		
		ValuesProvider unit = new WebValuesProvider(context, request);
		Assert.assertEquals("HomeAndAway", unit.getString(ValuesProvider.CONTROLLER));
		Assert.assertEquals("TurnOff", unit.getString(ValuesProvider.ACTION));
		Assert.assertEquals("0", unit.getString(ValuesProvider.IDENTITY));
		Assert.assertEquals("IHateIt", unit.getString(ValuesProvider.TITLE));
    }
	
	@Test public void canSetupDefaultsAndOverrideWithQueryString() {
		Map<String, String> contextValues = new HashMap<String, String>();
		contextValues.put("defaultHomePage", "/HomeAndAway/TurnOff/0/IHateIt");
		contextValues.put("root", "");
		ServletContext context = new FakeServletContext(contextValues);

		Map<String, Object> requestValues = new HashMap<String, Object>();
		requestValues.put("method", "get");
		requestValues.put("uri", "/");
		requestValues.put("url", "http://www.hairybadgers.com");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("title", "ILoatheIt");
		requestValues.put("parameters", parameters);
		FakeRequest request = new FakeRequest(requestValues);
		
		ValuesProvider unit = new WebValuesProvider(context, request);
		Assert.assertEquals("HomeAndAway", unit.getString(ValuesProvider.CONTROLLER));
		Assert.assertEquals("TurnOff", unit.getString(ValuesProvider.ACTION));
		Assert.assertEquals("0", unit.getString(ValuesProvider.IDENTITY));
		Assert.assertEquals("ILoatheIt", unit.getString(ValuesProvider.TITLE));
    }
	
	@Test public void canSetupDefaultsOverrideWithQueryStringWithContextAlwaysWins() {
		Map<String, String> contextValues = new HashMap<String, String>();
		contextValues.put("defaultHomePage", "/HomeAndAway/TurnOff/0/IHateIt");
		contextValues.put("root", "/hoot");
		contextValues.put("cannotOverride", "Indestructable");
		ServletContext context = new FakeServletContext(contextValues);

		Map<String, Object> requestValues = new HashMap<String, Object>();
		requestValues.put("method", "get");
		requestValues.put("uri", "/hoot");
		requestValues.put("url", "http://www.hairybadgers.com");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("cannotOverride", "PunyAttempt");
		parameters.put("title", "ILoatheIt");
		requestValues.put("parameters", parameters);
		FakeRequest request = new FakeRequest(requestValues);
		
		ValuesProvider unit = new WebValuesProvider(context, request);
		Assert.assertEquals("HomeAndAway", unit.getString(ValuesProvider.CONTROLLER));
		Assert.assertEquals("TurnOff", unit.getString(ValuesProvider.ACTION));
		Assert.assertEquals("0", unit.getString(ValuesProvider.IDENTITY));
		Assert.assertEquals("ILoatheIt", unit.getString(ValuesProvider.TITLE));
		Assert.assertEquals("Indestructable", unit.getString("cannotOverride"));
    }
	
}
