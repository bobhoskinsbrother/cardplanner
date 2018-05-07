package uk.co.itstherules.cardplanner.model;

import org.junit.Assert;
import org.junit.Test;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StoryBoardModelTest {

    @Test public void isBlank() {
        StoryBoardModel unit = new StoryBoardModel();
        assertThat(unit.isBlank(), is(true));
    }

    @Test public void notBlankWhenHotspotArea() {
        StoryBoardModel unit = new StoryBoardModel();
        Map<String, Object> storyBoardValues = new HashMap<String, Object>();
        storyBoardValues.put("hotspotAreas.0.status.identity", "Planned");
        storyBoardValues.put("hotspotAreas.0.status.title", "Planned");
        storyBoardValues.put("hotspotAreas.0.x", "0");
        storyBoardValues.put("hotspotAreas.0.y", "0");
        storyBoardValues.put("hotspotAreas.0.width", "267");
        storyBoardValues.put("hotspotAreas.0.height", "600");
        new BasicValuesProviderBinder().bind(new MapValuesProvider(storyBoardValues), unit, new FakeObjectCache(new HashSet<StatusModel>()));
        assertThat(unit.isBlank(), is(false));
    }

    @Test public void notBlankWhenLine() {
        StoryBoardModel unit = new StoryBoardModel();
        Map<String, Object> storyBoardValues = new HashMap<String, Object>();
        storyBoardValues.put("lines.0.one.x", "0");
        storyBoardValues.put("lines.0.one.y", "0");
        storyBoardValues.put("lines.0.two.x", "750");
        storyBoardValues.put("lines.0.two.y", "750");
        new BasicValuesProviderBinder().bind(new MapValuesProvider(storyBoardValues), unit, new FakeObjectCache(new HashSet<StatusModel>()));
        assertThat(unit.isBlank(), is(false));
    }

    @Test public void notBlankWhenTextArea() {
        StoryBoardModel unit = new StoryBoardModel();
        Map<String, Object> storyBoardValues = new HashMap<String, Object>();
        storyBoardValues.put("textAreas.0.title", "Planned");
        storyBoardValues.put("textAreas.0.x", "10");
        storyBoardValues.put("textAreas.0.y", "10");
        storyBoardValues.put("textAreas.0.width", "267");
        storyBoardValues.put("textAreas.0.height", "600");
        new BasicValuesProviderBinder().bind(new MapValuesProvider(storyBoardValues), unit, new FakeObjectCache(new HashSet<StatusModel>()));
        assertThat(unit.isBlank(), is(false));
    }

    @Test public void canAutoGenerateABoardWhenSuppliedWithThreeStatusesWidthAndHeight800x600() {
        StoryBoardModel storyBoardModel = new StoryBoardModel() {
            @Override public void clear(ObjectCache objectCache) {}

            @Override public Integer getWidth() { return 800; }

            @Override public Integer getHeight() { return 600; }

            @Override public CardModel getCard() {
                final CardModel cardModel = new CardModel();
                cardModel.setTitle("badgers");
                return cardModel;
            }
        }.defaultSetup(null);
        StoryBoardService unit = new StoryBoardService();
        Set<StatusModel> statuses = new LinkedHashSet<StatusModel>();
        addStatusesTo(statuses);
        final HashSet<StatusModel> statusModels = new HashSet<>();
        statusModels.add(status("One"));
        statusModels.add(status("Two"));
        statusModels.add(status("Three"));
        final FakeObjectCache objectCache = new FakeObjectCache(statusModels);
        unit.modifyStoryBoard(StoryBoardTemplate.ModifyTo.KanBanTemplate, objectCache, storyBoardModel);
        List<StoryBoardLineModel> lines = new ArrayList<StoryBoardLineModel>(storyBoardModel.getLines());
        List<StoryBoardHotspotAreaModel> hotspotAreas = new ArrayList<StoryBoardHotspotAreaModel>(storyBoardModel.getHotspotAreas());
        List<StoryBoardTextAreaModel> textAreas = new ArrayList<StoryBoardTextAreaModel>(storyBoardModel.getTextAreas());
        StoryBoardLineModel firstLine = lines.get(0);
        assertThat(firstLine.getPoints().get(0).getX(), is(275));
        assertThat(firstLine.getPoints().get(0).getY(), is(25));
        assertThat(firstLine.getPoints().get(1).getX(), is(275));
        assertThat(firstLine.getPoints().get(1).getY(), is(575));
        StoryBoardLineModel secondLine = lines.get(1);
        assertThat(secondLine.getPoints().get(0).getX(), is(525));
        assertThat(secondLine.getPoints().get(0).getY(), is(25));
        assertThat(secondLine.getPoints().get(1).getX(), is(525));
        assertThat(secondLine.getPoints().get(1).getY(), is(575));
        StoryBoardLineModel horizontalLine = lines.get(2);
        assertThat(horizontalLine.getPoints().get(0).getX(), is(25));
        assertThat(horizontalLine.getPoints().get(0).getY(), is(100));
        assertThat(horizontalLine.getPoints().get(1).getX(), is(775));
        assertThat(horizontalLine.getPoints().get(1).getY(), is(100));
        StoryBoardHotspotAreaModel firstHotspotArea = hotspotAreas.get(0);
        StoryBoardHotspotAreaModel secondHotspotArea = hotspotAreas.get(1);
        StoryBoardHotspotAreaModel thirdHotspotArea = hotspotAreas.get(2);
        assertThat(firstHotspotArea.getX(), is(25));
        assertThat(firstHotspotArea.getY(), is(25));
        assertThat(firstHotspotArea.getWidth(), is(250));
        assertThat(firstHotspotArea.getHeight(), is(575));
        assertThat(secondHotspotArea.getX(), is(275));
        assertThat(secondHotspotArea.getY(), is(25));
        assertThat(secondHotspotArea.getWidth(), is(250));
        assertThat(secondHotspotArea.getHeight(), is(575));
        assertThat(thirdHotspotArea.getX(), is(525));
        assertThat(thirdHotspotArea.getY(), is(25));
        assertThat(thirdHotspotArea.getWidth(), is(250));
        assertThat(thirdHotspotArea.getHeight(), is(575));
        StoryBoardTextAreaModel firstTextArea = textAreas.get(0);
        StoryBoardTextAreaModel secondTextArea = textAreas.get(1);
        StoryBoardTextAreaModel thirdTextArea = textAreas.get(2);
        assertThat(firstTextArea.getX(), is(90));
        assertThat(firstTextArea.getY(), is(50));
        assertThat(firstTextArea.getWidth(), is(120));
        assertThat(firstTextArea.getHeight(), is(35));
        assertThat(secondTextArea.getX(), is(340));
        assertThat(secondTextArea.getY(), is(50));
        assertThat(secondTextArea.getWidth(), is(120));
        assertThat(secondTextArea.getHeight(), is(35));
        assertThat(thirdTextArea.getX(), is(590));
        assertThat(thirdTextArea.getY(), is(50));
        assertThat(thirdTextArea.getWidth(), is(120));
        assertThat(thirdTextArea.getHeight(), is(35));
    }

    private StatusModel status(String title) {
        final StatusModel statusModel = new StatusModel();
        statusModel.setTitle(title);
        return statusModel;
    }

    @Test public void canAutoGenerateABoardWhenSuppliedWithThreeStatusesWidthAndHeight() {
        StoryBoardModel storyBoardModel = new StoryBoardModel() {
            @Override public void clear(ObjectCache objectCache) {}

            @Override public Integer getWidth() { return 1022; }

            @Override public Integer getHeight() { return 768; }

            @Override public CardModel getCard() {
                final CardModel cardModel = new CardModel();
                cardModel.setTitle("badgers");
                return cardModel;
            }
        }.defaultSetup(null);
        StoryBoardService unit = new StoryBoardService();
        Set<StatusModel> statuses = new LinkedHashSet<StatusModel>();
        addStatusesTo(statuses);
        final HashSet<StatusModel> statusModels = new HashSet<>();
        statusModels.add(status("One"));
        statusModels.add(status("Two"));
        statusModels.add(status("Three"));
        final FakeObjectCache objectCache = new FakeObjectCache(statusModels);
        unit.modifyStoryBoard(StoryBoardTemplate.ModifyTo.KanBanTemplate, objectCache, storyBoardModel);
        List<StoryBoardLineModel> lines = new ArrayList<StoryBoardLineModel>(storyBoardModel.getLines());
        List<StoryBoardHotspotAreaModel> hotspotAreas = new ArrayList<StoryBoardHotspotAreaModel>(storyBoardModel.getHotspotAreas());
        List<StoryBoardTextAreaModel> textAreas = new ArrayList<StoryBoardTextAreaModel>(storyBoardModel.getTextAreas());
        StoryBoardLineModel firstLine = lines.get(0);
        assertThat(firstLine.getPoints().get(0).getX(), is(349));
        assertThat(firstLine.getPoints().get(0).getY(), is(25));
        assertThat(firstLine.getPoints().get(1).getX(), is(349));
        assertThat(firstLine.getPoints().get(1).getY(), is(743));
        StoryBoardLineModel secondLine = lines.get(1);
        assertThat(secondLine.getPoints().get(0).getX(), is(673));
        assertThat(secondLine.getPoints().get(0).getY(), is(25));
        assertThat(secondLine.getPoints().get(1).getX(), is(673));
        assertThat(secondLine.getPoints().get(1).getY(), is(743));
        StoryBoardLineModel horizontalLine = lines.get(2);
        assertThat(horizontalLine.getPoints().get(0).getX(), is(25));
        assertThat(horizontalLine.getPoints().get(0).getY(), is(128));
        assertThat(horizontalLine.getPoints().get(1).getX(), is(997));
        assertThat(horizontalLine.getPoints().get(1).getY(), is(128));
        StoryBoardHotspotAreaModel firstHotspotArea = hotspotAreas.get(0);
        StoryBoardHotspotAreaModel secondHotspotArea = hotspotAreas.get(1);
        StoryBoardHotspotAreaModel thirdHotspotArea = hotspotAreas.get(2);
        assertThat(firstHotspotArea.getX(), is(25));
        assertThat(firstHotspotArea.getY(), is(25));
        assertThat(firstHotspotArea.getWidth(), is(324));
        assertThat(firstHotspotArea.getHeight(), is(743));
        assertThat(secondHotspotArea.getX(), is(349));
        assertThat(secondHotspotArea.getY(), is(25));
        assertThat(secondHotspotArea.getWidth(), is(324));
        assertThat(secondHotspotArea.getHeight(), is(743));
        assertThat(thirdHotspotArea.getX(), is(673));
        assertThat(thirdHotspotArea.getY(), is(25));
        assertThat(thirdHotspotArea.getWidth(), is(324));
        assertThat(thirdHotspotArea.getHeight(), is(743));
        StoryBoardTextAreaModel firstTextArea = textAreas.get(0);
        StoryBoardTextAreaModel secondTextArea = textAreas.get(1);
        StoryBoardTextAreaModel thirdTextArea = textAreas.get(2);
        assertThat(firstTextArea.getX(), is(127));
        assertThat(firstTextArea.getY(), is(50));
        assertThat(firstTextArea.getWidth(), is(120));
        assertThat(firstTextArea.getHeight(), is(35));
        assertThat(secondTextArea.getX(), is(451));
        assertThat(secondTextArea.getY(), is(50));
        assertThat(secondTextArea.getWidth(), is(120));
        assertThat(secondTextArea.getHeight(), is(35));
        assertThat(thirdTextArea.getX(), is(775));
        assertThat(thirdTextArea.getY(), is(50));
        assertThat(thirdTextArea.getWidth(), is(120));
        assertThat(thirdTextArea.getHeight(), is(35));
    }

    @Test public void canAddCardToPlanned() {
        CardModel card = new CardModel();
        card.setTitle("Smoke a Badger");
        StoryBoardModel unit = new StoryBoardModel().defaultSetup(new FakeObjectCache(new HashSet()));
        bindHotspotAreasTo(unit);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x", "10");
        map.put("y", "10");
        map.put("status.identity", Identities.THE_BACKLOG.getIdentity());
        new BasicValuesProviderBinder().bind(new MapValuesProvider(map), card, new FakeObjectCache(new HashSet()));
        Assert.assertEquals(Identities.THE_BACKLOG.getIdentity(), card.getStatus().getIdentity());
        unit.drop(card);
        Assert.assertEquals("Planned", card.getStatus().getIdentity());
    }

    @Test public void canAddCardToStarted() {
        CardModel card = new CardModel();
        card.setTitle("Smoke a Badger");
        StoryBoardModel unit = new StoryBoardModel().defaultSetup(new FakeObjectCache(new HashSet()));
        bindHotspotAreasTo(unit);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x", "300");
        map.put("y", "10");
        map.put("status.identity", Identities.THE_BACKLOG.getIdentity());
        ValuesProvider provider = new MapValuesProvider(map);
        new BasicValuesProviderBinder().bind(provider, card, new FakeObjectCache(new HashSet()));
        Assert.assertEquals(Identities.THE_BACKLOG.getIdentity(), card.getStatus().getIdentity());
        unit.drop(card);
        Assert.assertEquals("Started", card.getStatus().getIdentity());
    }

    @Test public void canAddCardToDone() {
        CardModel card = new CardModel();
        card.setTitle("Smoke a Badger");
        StoryBoardModel unit = new StoryBoardModel().defaultSetup(new FakeObjectCache(new HashSet()));
        bindHotspotAreasTo(unit);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x", "600");
        map.put("y", "10");
        map.put("status.identity", Identities.THE_BACKLOG.getIdentity());
        ValuesProvider provider = new MapValuesProvider(map);
        new BasicValuesProviderBinder().bind(provider, card, new FakeObjectCache(new HashSet()));
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
        storyBoardValues.put("hotspotAreas.0.status.identity", "Planned");
        storyBoardValues.put("hotspotAreas.0.status.title", "Planned");
        storyBoardValues.put("hotspotAreas.0.x", "0");
        storyBoardValues.put("hotspotAreas.0.y", "0");
        storyBoardValues.put("hotspotAreas.0.width", "267");
        storyBoardValues.put("hotspotAreas.0.height", "600");
        storyBoardValues.put("hotspotAreas.1.status.identity", "Started");
        storyBoardValues.put("hotspotAreas.1.status.title", "Started");
        storyBoardValues.put("hotspotAreas.1.x", "267");
        storyBoardValues.put("hotspotAreas.1.y", "0");
        storyBoardValues.put("hotspotAreas.1.width", "267");
        storyBoardValues.put("hotspotAreas.1.height", "600");
        storyBoardValues.put("hotspotAreas.2.status.identity", "Done");
        storyBoardValues.put("hotspotAreas.2.status.title", "Done");
        storyBoardValues.put("hotspotAreas.2.x", "534");
        storyBoardValues.put("hotspotAreas.2.y", "0");
        storyBoardValues.put("hotspotAreas.2.width", "267");
        storyBoardValues.put("hotspotAreas.2.height", "600");
        new BasicValuesProviderBinder().bind(new MapValuesProvider(storyBoardValues), unit, new FakeObjectCache(new HashSet()));
    }

}
