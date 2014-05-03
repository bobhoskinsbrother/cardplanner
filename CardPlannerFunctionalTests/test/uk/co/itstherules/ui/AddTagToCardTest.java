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
import uk.co.itstherules.ui.pages.list.TagsPage;

import java.net.URI;

import static org.junit.Assert.assertTrue;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;

public class AddTagToCardTest {

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
    public void canAddATagToACard() throws Exception {
        CardModel card = new DataFixtures().saveSimpleCard();
        String cardId = "_" + card.getIdentity();

        StoryBoardPage page = new StoryBoardPage(uri.toString(), pageLookup);
        page.navigateTo("0");

        page.toggleBacklog();
        page.dragCardFromBacklogToInProgress(cardId);
        page.toggleBacklog();

        page.clickCardEdit(cardId);

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forElement(pageLookup, By.id("title"), 5000);

        page.addTag("LaserTag");

        pageLookup.findElement(By.id("completeAction")).click();
        page.focus();

        Wait.forText(pageLookup, "LaserTag", 2000);

        TagsPage tagsPage = new TagsPage(pageLookup, uri.toString()).navigateTo("0");
        pageLookup.findElement(By.className("expandTag")).click();

        Wait.untilVisible(pageLookup, By.id(cardId), 3000);

        pageLookup.findElement(By.id(cardId)).click();

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forText(pageLookup, "I am an card that requires editing", 5000);

        assertTrue(tagsPage.containsText("I am an card that requires editing"));
        assertTrue(tagsPage.containsText("LaserTag"));

    }

}