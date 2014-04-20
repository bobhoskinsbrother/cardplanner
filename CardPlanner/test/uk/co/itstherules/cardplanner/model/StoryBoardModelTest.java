package uk.co.itstherules.cardplanner.model;

import static org.hamcrest.CoreMatchers.is;

import java.util.*;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;

@Ignore
public class StoryBoardModelTest {
	
	@Test public void isBlank() {
		StoryBoardModel unit = new StoryBoardModel();
		Assert.assertThat(unit.isBlank(), is(true));
	}
	
	@Test public void notBlankWhenHotspotArea() {
		StoryBoardModel unit = new StoryBoardModel();
		Map<String, Object> storyBoardValues = new HashMap<String, Object>();
		storyBoardValues.put("hotspotAreas.0.status.identity","Planned");
		storyBoardValues.put("hotspotAreas.0.status.title","Planned");
		storyBoardValues.put("hotspotAreas.0.x","0");
		storyBoardValues.put("hotspotAreas.0.y","0");
		storyBoardValues.put("hotspotAreas.0.width","267");
		storyBoardValues.put("hotspotAreas.0.height","600");
		new BasicValuesProviderBinder().bind(new MapValuesProvider(storyBoardValues), unit, new FakeObjectCache());

		Assert.assertThat(unit.isBlank(), is(false));
	}
	
	@Test public void notBlankWhenLine() {
		StoryBoardModel unit = new StoryBoardModel();
		Map<String, Object> storyBoardValues = new HashMap<String, Object>();
		storyBoardValues.put("lines.0.one.x","0");
		storyBoardValues.put("lines.0.one.y","0");
		storyBoardValues.put("lines.0.two.x","750");
		storyBoardValues.put("lines.0.two.y","750");
		new BasicValuesProviderBinder().bind(new MapValuesProvider(storyBoardValues), unit, new FakeObjectCache());
		
		Assert.assertThat(unit.isBlank(), is(false));
	}
	
	@Test public void notBlankWhenTextArea() {
		StoryBoardModel unit = new StoryBoardModel();
		Map<String, Object> storyBoardValues = new HashMap<String, Object>();
		storyBoardValues.put("textAreas.0.title","Planned");
		storyBoardValues.put("textAreas.0.x","10");
		storyBoardValues.put("textAreas.0.y","10");
		storyBoardValues.put("textAreas.0.width","267");
		storyBoardValues.put("textAreas.0.height","600");
		new BasicValuesProviderBinder().bind(new MapValuesProvider(storyBoardValues), unit, new FakeObjectCache());
		
		Assert.assertThat(unit.isBlank(), is(false));
	}
	
	@Test public void canAutoGenerateABoardWhenSuppliedWithThreeStatusesWidthAndHeight800x600() {
		StoryBoardModel unit = new StoryBoardModel();
        StoryBoardService service = new StoryBoardService();
	    Map<String, Object> map = new HashMap<String, Object>();
        map.put("width", "800");
        map.put("height", "600");
        map.put("card.title", "badgers");
        new BasicValuesProviderBinder().bind(new MapValuesProvider(map), unit, new FakeObjectCache());

        Set<StatusModel> statuses = new LinkedHashSet<StatusModel>();
        addStatusesTo(statuses);
        service.modifyStoryBoard(StoryBoardTemplate.ModifyTo.KanBanTemplate, new FakeObjectCache(), unit);

		List<StoryBoardLineModel> lines = new ArrayList<StoryBoardLineModel>(unit.getLines());
		List<StoryBoardHotspotAreaModel> hotspotAreas = new ArrayList<StoryBoardHotspotAreaModel>(unit.getHotspotAreas());
		List<StoryBoardTextAreaModel> textAreas = new ArrayList<StoryBoardTextAreaModel>(unit.getTextAreas());
		
		StoryBoardLineModel firstLine = lines.get(0);
		Assert.assertThat(firstLine.getPoints().get(0).getX(), is(266));
		Assert.assertThat(firstLine.getPoints().get(0).getY(), is(0));
		Assert.assertThat(firstLine.getPoints().get(1).getX(), is(266));
		Assert.assertThat(firstLine.getPoints().get(1).getY(), is(600));
		
		StoryBoardLineModel secondLine = lines.get(1);
		Assert.assertThat(secondLine.getPoints().get(0).getX(), is(532));
		Assert.assertThat(secondLine.getPoints().get(0).getY(), is(0));
		Assert.assertThat(secondLine.getPoints().get(1).getX(), is(532));
		Assert.assertThat(secondLine.getPoints().get(1).getY(), is(600));
		
		StoryBoardLineModel horizontalLine = lines.get(2);
		Assert.assertThat(horizontalLine.getPoints().get(0).getX(), is(0));
		Assert.assertThat(horizontalLine.getPoints().get(0).getY(), is(100));
		Assert.assertThat(horizontalLine.getPoints().get(1).getX(), is(800));
		Assert.assertThat(horizontalLine.getPoints().get(1).getY(), is(100));
		
		StoryBoardHotspotAreaModel firstHotspotArea = hotspotAreas.get(0);
		StoryBoardHotspotAreaModel secondHotspotArea = hotspotAreas.get(1);
		StoryBoardHotspotAreaModel thirdHotspotArea = hotspotAreas.get(2);
		
		Assert.assertThat(firstHotspotArea.getX(), is(0));
		Assert.assertThat(firstHotspotArea.getY(), is(0));
		Assert.assertThat(firstHotspotArea.getWidth(), is(266));
		Assert.assertThat(firstHotspotArea.getHeight(), is(600));
		
		Assert.assertThat(secondHotspotArea.getX(), is(266));
		Assert.assertThat(secondHotspotArea.getY(), is(0));
		Assert.assertThat(secondHotspotArea.getWidth(), is(266));
		Assert.assertThat(secondHotspotArea.getHeight(), is(600));
		
		Assert.assertThat(thirdHotspotArea.getX(), is(532));
		Assert.assertThat(thirdHotspotArea.getY(), is(0));
		Assert.assertThat(thirdHotspotArea.getWidth(), is(266));
		Assert.assertThat(thirdHotspotArea.getHeight(), is(600));
		
		StoryBoardTextAreaModel firstTextArea = textAreas.get(0);
		StoryBoardTextAreaModel secondTextArea = textAreas.get(1);
		StoryBoardTextAreaModel thirdTextArea = textAreas.get(2);
		
		Assert.assertThat(firstTextArea.getX(), is(73)); 
		Assert.assertThat(firstTextArea.getY(), is(50));
		Assert.assertThat(firstTextArea.getWidth(), is(120));
		Assert.assertThat(firstTextArea.getHeight(), is(50));
		
		Assert.assertThat(secondTextArea.getX(), is(339));
		Assert.assertThat(secondTextArea.getY(), is(50));
		Assert.assertThat(secondTextArea.getWidth(), is(120));
		Assert.assertThat(secondTextArea.getHeight(), is(50));
		
		Assert.assertThat(thirdTextArea.getX(), is(605));
		Assert.assertThat(thirdTextArea.getY(), is(50));
		Assert.assertThat(thirdTextArea.getWidth(), is(120));
		Assert.assertThat(thirdTextArea.getHeight(), is(50));
	}
	
	
	@Test public void canAutoGenerateABoardWhenSuppliedWithThreeStatusesWidthAndHeight() {
	    StoryBoardModel unit = new StoryBoardModel();
	    Map<String, Object> map = new HashMap<String, Object>();
		map.put("width", "1022");
	    map.put("height", "768");
        map.put("card.title", "badgers");
		new BasicValuesProviderBinder().bind(new MapValuesProvider(map), unit, new FakeObjectCache());
		
		Set<StatusModel> statuses = new LinkedHashSet<StatusModel>();
		addStatusesTo(statuses);
		new StoryBoardService().modifyStoryBoard(StoryBoardTemplate.ModifyTo.KanBanTemplate, new FakeObjectCache(), unit);
		
		List<StoryBoardLineModel> lines = new ArrayList<StoryBoardLineModel>(unit.getLines());
		List<StoryBoardHotspotAreaModel> hotspotAreas = new ArrayList<StoryBoardHotspotAreaModel>(unit.getHotspotAreas());
		List<StoryBoardTextAreaModel> textAreas =  new ArrayList<StoryBoardTextAreaModel>(unit.getTextAreas());
		

		StoryBoardLineModel firstLine = lines.get(0);
		Assert.assertThat(firstLine.getPoints().get(0).getX(), is(340));
		Assert.assertThat(firstLine.getPoints().get(0).getY(), is(0));
		Assert.assertThat(firstLine.getPoints().get(1).getX(), is(340));
		Assert.assertThat(firstLine.getPoints().get(1).getY(), is(768));

		StoryBoardLineModel secondLine = lines.get(1);
		Assert.assertThat(secondLine.getPoints().get(0).getX(), is(680));
		Assert.assertThat(secondLine.getPoints().get(0).getY(), is(0));
		Assert.assertThat(secondLine.getPoints().get(1).getX(), is(680));
		Assert.assertThat(secondLine.getPoints().get(1).getY(), is(768));
		
		StoryBoardLineModel horizontalLine = lines.get(2);
		Assert.assertThat(horizontalLine.getPoints().get(0).getX(), is(0));
		Assert.assertThat(horizontalLine.getPoints().get(0).getY(), is(100));
		Assert.assertThat(horizontalLine.getPoints().get(1).getX(), is(1022));
		Assert.assertThat(horizontalLine.getPoints().get(1).getY(), is(100));

		StoryBoardHotspotAreaModel firstHotspotArea = hotspotAreas.get(0);
		StoryBoardHotspotAreaModel secondHotspotArea = hotspotAreas.get(1);
		StoryBoardHotspotAreaModel thirdHotspotArea = hotspotAreas.get(2);
		
		Assert.assertThat(firstHotspotArea.getX(), is(0));
		Assert.assertThat(firstHotspotArea.getY(), is(0));
		Assert.assertThat(firstHotspotArea.getWidth(), is(340));
		Assert.assertThat(firstHotspotArea.getHeight(), is(768));
		
		Assert.assertThat(secondHotspotArea.getX(), is(340));
		Assert.assertThat(secondHotspotArea.getY(), is(0));
		Assert.assertThat(secondHotspotArea.getWidth(), is(340));
		Assert.assertThat(secondHotspotArea.getHeight(), is(768));
		
		Assert.assertThat(thirdHotspotArea.getX(), is(680));
		Assert.assertThat(thirdHotspotArea.getY(), is(0));
		Assert.assertThat(thirdHotspotArea.getWidth(), is(340));
		Assert.assertThat(thirdHotspotArea.getHeight(), is(768));
		
		StoryBoardTextAreaModel firstTextArea = textAreas.get(0);
		StoryBoardTextAreaModel secondTextArea = textAreas.get(1);
		StoryBoardTextAreaModel thirdTextArea = textAreas.get(2);

		Assert.assertThat(firstTextArea.getX(), is(110));
		Assert.assertThat(firstTextArea.getY(), is(50));
		Assert.assertThat(firstTextArea.getWidth(), is(120));
		Assert.assertThat(firstTextArea.getHeight(), is(50));
		
		Assert.assertThat(secondTextArea.getX(), is(450));
		Assert.assertThat(secondTextArea.getY(), is(50));
		Assert.assertThat(secondTextArea.getWidth(), is(120));
		Assert.assertThat(secondTextArea.getHeight(), is(50));
		
		Assert.assertThat(thirdTextArea.getX(), is(790));
		Assert.assertThat(thirdTextArea.getY(), is(50));
		Assert.assertThat(thirdTextArea.getWidth(), is(120));
		Assert.assertThat(thirdTextArea.getHeight(), is(50));
	}
	
	@Test public void canAddCardToPlanned() {
		CardModel card = new CardModel();
		card.setTitle("Smoke a Badger");
		
	    StoryBoardModel unit = new StoryBoardModel().defaultSetup(new FakeObjectCache());
		bindHotspotAreasTo(unit);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("x", "10");
		map.put("y", "10");
		map.put("status.identity", Identities.THE_BACKLOG.getIdentity());
		new BasicValuesProviderBinder().bind(new MapValuesProvider(map), card, new FakeObjectCache());
		
		Assert.assertEquals(Identities.THE_BACKLOG.getIdentity(), card.getStatus().getIdentity());
		unit.drop(card);
		Assert.assertEquals("Planned", card.getStatus().getIdentity());
	}

	
	@Test public void canAddCardToStarted() {
		CardModel card = new CardModel();
		card.setTitle("Smoke a Badger");
	    StoryBoardModel unit = new StoryBoardModel().defaultSetup(new FakeObjectCache());
		bindHotspotAreasTo(unit);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("x", "300");
		map.put("y", "10");
		map.put("status.identity", Identities.THE_BACKLOG.getIdentity());
		ValuesProvider provider = new MapValuesProvider(map);
		new BasicValuesProviderBinder().bind(provider, card, new FakeObjectCache());
		
		Assert.assertEquals(Identities.THE_BACKLOG.getIdentity(), card.getStatus().getIdentity());
		unit.drop(card);
		Assert.assertEquals("Started", card.getStatus().getIdentity());
	}
	
	@Test public void canAddCardToDone() {
		CardModel card = new CardModel();
		card.setTitle("Smoke a Badger");
	    StoryBoardModel unit = new StoryBoardModel().defaultSetup(new FakeObjectCache());
		bindHotspotAreasTo(unit);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("x", "600");
		map.put("y", "10");
		map.put("status.identity", Identities.THE_BACKLOG.getIdentity());
		ValuesProvider provider = new MapValuesProvider(map);
		new BasicValuesProviderBinder().bind(provider, card, new FakeObjectCache());
		
		Assert.assertEquals(Identities.THE_BACKLOG.getIdentity(), card.getStatus().getIdentity());
		unit.drop(card);
		Assert.assertEquals("Done", card.getStatus().getIdentity());
	}	
	
	private void addStatusesTo(Set<StatusModel> statuses) {
		StatusModel one = new StatusModel();
		one.setTitle("Planned");
		one.setIdentity("Planned");
		StatusModel two = new StatusModel();
		two.setTitle("Started");
		two.setIdentity("Started");
		StatusModel three = new StatusModel();
		three.setTitle("Done");
		three.setIdentity("Done");
		statuses.add(one);
		statuses.add(two);
		statuses.add(three);
    }

	
	private void bindHotspotAreasTo(StoryBoardModel unit) {
		Map<String, Object> storyBoardValues = new HashMap<String, Object>();
		storyBoardValues.put("hotspotAreas.0.status.identity","Planned");
		storyBoardValues.put("hotspotAreas.0.status.title","Planned");
		storyBoardValues.put("hotspotAreas.0.x","0");
		storyBoardValues.put("hotspotAreas.0.y","0");
		storyBoardValues.put("hotspotAreas.0.width","267");
		storyBoardValues.put("hotspotAreas.0.height","600");

		storyBoardValues.put("hotspotAreas.1.status.identity","Started");
		storyBoardValues.put("hotspotAreas.1.status.title","Started");
		storyBoardValues.put("hotspotAreas.1.x","267");
		storyBoardValues.put("hotspotAreas.1.y","0");
		storyBoardValues.put("hotspotAreas.1.width","267");
		storyBoardValues.put("hotspotAreas.1.height","600");
		
		storyBoardValues.put("hotspotAreas.2.status.identity","Done");
		storyBoardValues.put("hotspotAreas.2.status.title","Done");
		storyBoardValues.put("hotspotAreas.2.x","534");
		storyBoardValues.put("hotspotAreas.2.y","0");
		storyBoardValues.put("hotspotAreas.2.width","267");
		storyBoardValues.put("hotspotAreas.2.height","600");
		
		new BasicValuesProviderBinder().bind(new MapValuesProvider(storyBoardValues), unit, new FakeObjectCache());
    }

}
