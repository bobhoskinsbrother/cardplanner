package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
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
