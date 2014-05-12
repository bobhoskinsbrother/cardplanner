package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.Constants;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.list.StoryBoardPage;

import java.net.URI;

import static org.junit.Assert.assertTrue;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;

public class StoryBoardTest {

    private static WebDriver pageLookup;
    private static CardPlannerServer server;
    private static URI uri;

    @BeforeClass
    public static void setup() throws Exception {
        pageLookup = WebDriverInstance.make();
        server = new CardPlannerServer(TEST);
        uri = server.port(0).startServer();
    }

    @AfterClass
    public static void destroy() {
        WebDriverInstance.destroy(pageLookup);
        server.destroy();
    }

    @Test
    public void canAddACard() throws Exception {
        StoryBoardPage page = new StoryBoardPage(uri.toString(), pageLookup);
        page.navigateTo("0");

        page.clickCardAdd();

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forElement(pageLookup, By.id("title"), 5000);

        page
                .setTitle("I'm a new card")
                .setBody("**Well, well, well**  It's all gone a bit funny")
                .setEffort("Ideal Day", 2.5)
                .setValue("Currency", 2100)
                .setCardType("User Story")
                .setStatus("Planned")
                .addTag("Funny")
                .completeAction();
        page.focus();
        assertTrue(page.containsText("I'm a new card"));
    }

    @Test
    public void canAddAPostIt() throws Exception {
        StoryBoardPage page = new StoryBoardPage(uri.toString(), pageLookup);
        page.navigateTo("0");

        page.clickPostItAdd();

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forElement(pageLookup, By.id("title"), 5000);

        page
                .setTitle("I'm a new postit")
                .setColour("#00FFFF")
                .setBody("**Well, well, well**  It's all gone a bit runny")
                .completeAction();
        page.focus();
        assertTrue(page.containsText("I'm a new postit"));
    }

    @Test
    public void canChangeStatusOfCardByDragging() throws Exception {
        CardModel card = new DataFixtures().saveSimpleCard();
        String cardId = "_" + card.getIdentity();
        StoryBoardPage page = new StoryBoardPage(uri.toString(), pageLookup);
        page.navigateTo("0");
        page.toggleBacklog();
        page.expandCard(cardId);
        page.clickCardShow(cardId);

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forText(pageLookup, "I am an card that requires editing", 5000);
        page.focus();
        page.closePopOver();

        assertTrue(page.containsText("Backlog"));
        page.collapseCard(cardId);
        page.dragCardFromBacklogToInProgress(cardId);

        page.toggleBacklog();
        page.expandCard(cardId);
        page.clickCardShow(cardId);

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forText(pageLookup, "I am an card that requires editing", 5000);

        assertTrue(page.containsText("In Progress"));
    }

    @Test
    public void canOpenAndCloseNavTwice() throws Exception {
        final StoryBoardPage page = new StoryBoardPage(uri.toString(), pageLookup);
        page.navigateTo("0");
        Wait.forText(pageLookup, "Empty", 5000);

        page.clickLinksTab();
        Wait.untilVisible(pageLookup, By.linkText("StoryBoard"), 1000);

        page.clickLinksTab();
        Wait.untilElementIsGone(pageLookup, By.linkText("StoryBoard"), 2000);

        page.clickLinksTab();
        Wait.untilVisible(pageLookup, By.linkText("StoryBoard"), 1000);

        page.clickLinksTab();
        Wait.untilElementIsGone(pageLookup, By.linkText("StoryBoard"), 2000);
    }


}
