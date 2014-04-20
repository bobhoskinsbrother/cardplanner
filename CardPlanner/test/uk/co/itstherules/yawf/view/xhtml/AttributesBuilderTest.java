package uk.co.itstherules.yawf.view.xhtml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.SimpleEntityModel;

public class AttributesBuilderTest {
	
	@Test public void buildNoError() {
		AttributesBuilder unit = new AttributesBuilder();
		Assert.assertEquals("", unit.build(new ArrayList<Entity<?>>(), false));
		Assert.assertEquals("key=\"value\" ", unit.build(Collections.singletonList(new SimpleEntityModel("key", "value")), false));
		List<Entity<?>> list = new LinkedList<Entity<?>>();
		list.add(new SimpleEntityModel("key", "value"));
		list.add(new SimpleEntityModel("key2", "value2"));
		Assert.assertEquals("key=\"value\" key2=\"value2\" ", unit.build(list, false));

		Assert.assertEquals("", unit.build(new ArrayList<Entity<?>>()));
		Assert.assertEquals("key=\"value\" ", unit.build(Collections.singletonList(new SimpleEntityModel("key", "value"))));
		list = new LinkedList<Entity<?>>();
		list.add(new SimpleEntityModel("key", "value"));
		list.add(new SimpleEntityModel("key2", "value2"));
		Assert.assertEquals("key=\"value\" key2=\"value2\" ", unit.build(list));
	}
	
	@Test public void buildError() {
		AttributesBuilder unit = new AttributesBuilder();
		Assert.assertEquals(" class=\"errorbackground errorborder\" ", unit.build(new ArrayList<Entity<?>>(), true));
		Assert.assertEquals("key=\"value\"  class=\"errorbackground errorborder\" ", unit.build(Collections.singletonList(new SimpleEntityModel("key", "value")), true));
		List<Entity<?>> list = new LinkedList<Entity<?>>();
		list.add(new SimpleEntityModel("key", "value"));
		list.add(new SimpleEntityModel("key2", "value2"));
		Assert.assertEquals("key=\"value\" key2=\"value2\"  class=\"errorbackground errorborder\" ", unit.build(list, true));
	}
	
	@Test public void buildErrorClassAlreadyExists() {
		AttributesBuilder unit = new AttributesBuilder();
		List<Entity<?>> list = new LinkedList<Entity<?>>();
		list.add(new SimpleEntityModel("class", "brass"));
		list.add(new SimpleEntityModel("key", "value"));
		list.add(new SimpleEntityModel("key2", "value2"));
		Assert.assertEquals("class=\"brass errorbackground errorborder\" key=\"value\" key2=\"value2\" ", unit.build(list, true));
	}
}