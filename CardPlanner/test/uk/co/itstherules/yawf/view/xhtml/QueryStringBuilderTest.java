package uk.co.itstherules.yawf.view.xhtml;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.SimpleEntityModel;

public class QueryStringBuilderTest {
	
	@Test public void canBuildMap() {
		QueryStringBuilder unit = new QueryStringBuilder();
		List<Entity<?>> map = new LinkedList<Entity<?>>();
		Assert.assertEquals("", unit.build(map));
		map.add(new SimpleEntityModel("squirrels", "spiders"));
		Assert.assertEquals("?squirrels=spiders", unit.build(map));
		map.add(new SimpleEntityModel("gibbons", "ribbons"));
		Assert.assertEquals("?squirrels=spiders&amp;gibbons=ribbons", unit.build(map));
    }
	
	@Test public void canBuildHash() {
		QueryStringBuilder unit = new QueryStringBuilder();
		List<Entity<?>> map = new LinkedList<Entity<?>>();
		Assert.assertEquals("", unit.build(map));
		map = new LinkedList<Entity<?>>();
		map.add(new SimpleEntityModel("squirrels", "spiders"));
		Assert.assertEquals("?squirrels=spiders", unit.build(map));
		map = new LinkedList<Entity<?>>();
		map.add(new SimpleEntityModel("squirrels", "spiders"));
		map.add(new SimpleEntityModel("gibbons", "ribbons"));
		Assert.assertEquals("?squirrels=spiders&amp;gibbons=ribbons", unit.build(map));
    }
	
}
