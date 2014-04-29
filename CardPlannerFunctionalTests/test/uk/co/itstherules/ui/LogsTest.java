package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.list.LogsPage;
import uk.co.itstherules.ui.pages.list.StoryBoardPage;

import java.net.URI;

import static org.junit.Assert.assertTrue;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;

public class LogsTest {

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
    public void moveACardAroundABitAndSeeLogsAreGenerated() throws Exception {
        CardModel card = new DataFixtures().saveSimpleCard();
        String cardId = "_" + card.getIdentity();

        StoryBoardPage page = new StoryBoardPage(uri.toString(), pageLookup);
        page.navigateTo("0");

        page.toggleBacklog();
        Wait.forElement(pageLookup, By.id(cardId), 1000);

        String targetPanelId = "_1e5da83b-0a9d-4428-af28-7187b8718d2b";
        page.dragOnto(cardId, targetPanelId);

        LogsPage logsPage = new LogsPage(pageLookup, uri.toString());
        logsPage.navigateTo("0");
        Wait.forText(pageLookup, "Logs", 5000);
        assertTrue(logsPage.containsText("I am an card that requires editing"));
        assertTrue(logsPage.containsText("The Backlog"));
        assertTrue(logsPage.containsText("In Progress"));
        assertTrue(logsPage.containsText("Update"));
    }

}
