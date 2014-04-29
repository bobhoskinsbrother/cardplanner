package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.Constants;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.list.StoryBoardPage;

import java.net.URI;

import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;

public class StoryBoardTest {

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

    @Test @Ignore("Double click not dispatching properly in firefox 28 on ubuntu")
    public void canDoubleClickToOpenCard() throws Exception {
        CardModel card = new DataFixtures().saveSimpleCard();
        String cardId = "_"+card.getIdentity();
        StoryBoardPage page = new StoryBoardPage(uri.toString(), pageLookup);
        page.navigateTo("0");
        page.openBacklog();

        Wait.forElement(pageLookup, By.id(cardId), 1000);

        WebElement element = pageLookup.findElement(By.id(cardId));
        String targetPanelId = "_1e5da83b-0a9d-4428-af28-7187b8718d2b";
        page.dragOnto(cardId, targetPanelId);

        Actions actions = new Actions(pageLookup);
        Action action = actions.doubleClick(element).build();
        action.perform();

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forText(pageLookup, "I am an card that requires editing", 5000);

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
