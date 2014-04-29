package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.list.ResurrectDeadThingsPage;
import uk.co.itstherules.ui.pages.list.StoryBoardPage;

import java.net.URI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;

public class ResurrectDeadThingsTest {

    private static WebDriver pageLookup;
    private static CardPlannerServer server;
    private static URI uri;

    @BeforeClass
    public static void setup() throws Exception {
        pageLookup = WebDriverInstance.get();
        server = new CardPlannerServer(TEST);
        uri = server.port(0).startServer();
    }

    @AfterClass
    public static void destroy() {
        WebDriverInstance.destroy();
        server.destroy();
    }

    @Test
    public void canViewThePage() throws Exception {
        new ResurrectDeadThingsPage(pageLookup, uri.toString()).navigateTo("0");
        Wait.forText(pageLookup, "Resurrect Dead Things", 5000);
    }

	@Test public void reviveCard() throws Exception {
        CardModel card = new DataFixtures().saveSimpleCard();
        String cardId = "_" + card.getIdentity();

        StoryBoardPage storyBoardPage = new StoryBoardPage(uri.toString(), pageLookup);
        storyBoardPage.navigateTo("0");
        storyBoardPage.deleteCard(cardId);
        assertFalse(storyBoardPage.containsText("I am a card that requires editing"));

        ResurrectDeadThingsPage deadThingsPage = new ResurrectDeadThingsPage(pageLookup, uri.toString()).navigateTo("0");

        deadThingsPage.selectType("Card");
        assertTrue(deadThingsPage.containsText("I am an card that"));

        deadThingsPage.undelete(cardId);
        assertFalse(deadThingsPage.containsText("I am an card that"));

        storyBoardPage.navigateTo("0");
        storyBoardPage.toggleBacklog();

        assertTrue(storyBoardPage.containsText("I am an card that"));

    }


//	@Test public void reviveEffortType() throws Exception {
//    }
//
//	@Test public void revivePerson() throws Exception {
//    }
//
//	@Test public void reviveSpan() throws Exception {
//    }
//
//	@Test public void reviveStatus() throws Exception {
//    }
//
//	@Test public void reviveTag() throws Exception {
//    }
//
//	@Test public void reviveTeam() throws Exception {
//    }
//
//	@Test public void reviveValueType() throws Exception {
//    }
//
//	@Test public void reviveCardType() throws Exception {
//    }

}
