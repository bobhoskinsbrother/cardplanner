package uk.co.itstherules.yawf.inbound.binders;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;

public class ListConverterTest {
	
	@Test public void canConvertString() {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		values.put("basic.0.title", "bigdaddy");
		values.put("basic.0.identity", "smallmummy");
		values.put("basic.1.title", "smalldaddy");
		values.put("basic.1.identity", "bigmummy");
		values.put("basic.1.body", "weekiddie");
		values.put("basic.2.identity", "smallskivvy");
		values.put("advanced.0.fred", "wilma");
		
		ListBinder unit = new ListBinder(null, new BasicValuesProviderBinder(), new ImplementationProviderRegister());
		Map<String, Map<String, Object>> reply = unit.filterProviderForCurrentContext("basic", new MapValuesProvider(values));
		Map<String, Object> zero = reply.get("0");
		Map<String, Object> one = reply.get("1");
		Map<String, Object> two = reply.get("2");

		Assert.assertEquals("bigdaddy", zero.get("title"));
		Assert.assertEquals("smallmummy", zero.get("identity"));

		Assert.assertEquals("smalldaddy", one.get("title"));
		Assert.assertEquals("bigmummy", one.get("identity"));
		Assert.assertEquals("weekiddie", one.get("body"));

		Assert.assertEquals("smallskivvy", two.get("identity"));
	}
	
}
