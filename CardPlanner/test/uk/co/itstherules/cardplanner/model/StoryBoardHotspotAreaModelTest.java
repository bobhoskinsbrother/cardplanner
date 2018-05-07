package uk.co.itstherules.cardplanner.model;

import junit.framework.Assert;
import org.junit.Test;
import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class StoryBoardHotspotAreaModelTest {
	
	@Test public void doesContain() {
		StoryBoardHotspotAreaModel unit = new StoryBoardHotspotAreaModel();
		bindHotspotAreaTo(unit);
		
		Assert.assertTrue(unit.containsPoint(0, 0));
		Assert.assertTrue(unit.containsPoint(0, 10));
		Assert.assertTrue(unit.containsPoint(10, 0));
		Assert.assertTrue(unit.containsPoint(5, 5));
		Assert.assertTrue(unit.containsPoint(5, 0));
		Assert.assertTrue(unit.containsPoint(0, 5));
		Assert.assertTrue(unit.containsPoint(10, 10));
	}

	@Test public void doesContainNotZero() {
		StoryBoardHotspotAreaModel unit = new StoryBoardHotspotAreaModel();
		bindHotspotAreaTo2(unit);
		Assert.assertTrue(unit.containsPoint(5,5));
		Assert.assertTrue(unit.containsPoint(10, 10));
		Assert.assertTrue(unit.containsPoint(15, 15));
	}
	
	@Test public void doesNotContain() {
		StoryBoardHotspotAreaModel unit = new StoryBoardHotspotAreaModel();
		bindHotspotAreaTo(unit);
		Assert.assertFalse(unit.containsPoint(-1, 0));
		Assert.assertFalse(unit.containsPoint(0, -1));
		
		Assert.assertFalse(unit.containsPoint(11, 10));
		Assert.assertFalse(unit.containsPoint(10, 11));

		Assert.assertFalse(unit.containsPoint(0, 11));
		Assert.assertFalse(unit.containsPoint(11, 0));
	}
	
	private void bindHotspotAreaTo(StoryBoardHotspotAreaModel unit) {
		Map<String, Object> storyBoardValues = new HashMap<String, Object>();
		storyBoardValues.put("x","0");
		storyBoardValues.put("y","0");
		storyBoardValues.put("width","10");
		storyBoardValues.put("height","10");

		new BasicValuesProviderBinder().bind(new MapValuesProvider(storyBoardValues), unit, new FakeObjectCache(new HashSet()));
    }

	private void bindHotspotAreaTo2(StoryBoardHotspotAreaModel unit) {
		Map<String, Object> storyBoardValues = new HashMap<String, Object>();
		storyBoardValues.put("x","5");
		storyBoardValues.put("y","5");
		storyBoardValues.put("width","15");
		storyBoardValues.put("height","15");
		
		new BasicValuesProviderBinder().bind(new MapValuesProvider(storyBoardValues), unit, new FakeObjectCache(new HashSet()));
	}

}
