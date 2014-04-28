package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.pages.list.TagsPage;

import java.net.URI;

import static org.junit.Assert.assertFalse;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;

public class DeleteTagTest {

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
    public void deleteTag() throws Exception {
        new DataFixtures().saveTag("Delete Me Tag");
        TagsPage tagsPage = new TagsPage(pageLookup, uri.toString());
        tagsPage.navigateTo("0");
        tagsPage.confirmAs(true);
        pageLookup.findElement(By.name("deleteButton")).click();
        assertFalse(tagsPage.containsText("Delete Me Tag", 1000));
    }


}
