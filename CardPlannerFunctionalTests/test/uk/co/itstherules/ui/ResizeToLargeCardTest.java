package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.pages.list.StoryBoardPage;

import java.net.URI;

import static org.junit.Assert.assertThat;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;
import static uk.co.itstherules.junit.extension.WebMatcher.textNotOnThePage;
import static uk.co.itstherules.junit.extension.WebMatcher.textOnThePage;

public class ResizeToLargeCardTest {

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
    public void canResizeCardToLargeByDragging() throws Exception {
        CardModel card = new DataFixtures().saveSimpleCard();
        String cardId = "_" + card.getIdentity();
        StoryBoardPage page = new StoryBoardPage(uri.toString(), pageLookup);
        page.navigateTo("0");
        page.toggleBacklog();
        page.dragCardFromBacklogToXY(cardId, 0, -300);
        assertThat("as I may have a few tpyos", textNotOnThePage(pageLookup));
        assertThat("0 Ideal Day", textNotOnThePage(pageLookup));
        assertThat("0 Currency", textNotOnThePage(pageLookup));
        final Actions a = new Actions(pageLookup);
        a.moveToElement(pageLookup.findElement(By.id(cardId)), 92, 62).clickAndHold().moveByOffset(200,125).release().build().perform();
        assertThat("as I may have a few tpyos", textOnThePage(pageLookup));
        assertThat("0 Ideal Day", textOnThePage(pageLookup));
        assertThat("0 Currency", textOnThePage(pageLookup));

    }

}