package uk.co.itstherules.yawf.inbound;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class BaseValuesProviderTest {

	@Test public void canLimitContext() {
		Map<String, Object> initial = new LinkedHashMap<String, Object>();
		initial.put("i.am.a.key.that.need.splitting", "fish");
		initial.put("i.am.another.key.that.need.splitting", "dish");
		initial.put("dont.add.me", "rubbish");
		MapValuesProvider unit = new MapValuesProvider(initial);
		
		ValuesProvider reply = unit.limitContext("i.am");
		Set<String> keys = reply.getKeys();
		Assert.assertEquals(2, keys.size());
		
		Iterator<String> iterator = keys.iterator();
		String currentKey = iterator.next();
		Assert.assertEquals("a.key.that.need.splitting", currentKey);
		Assert.assertEquals("fish", reply.getString(currentKey));
		
		currentKey = iterator.next();
		Assert.assertEquals("another.key.that.need.splitting", currentKey);
		Assert.assertEquals("dish", reply.getString(currentKey));
		
	}
	
}
