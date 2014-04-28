package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.Constants;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.list.TagsPage;

import java.net.URI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;

public class TagsTest {

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
        new TagsPage(pageLookup, uri.toString()).navigateTo("0");
        Wait.forText(pageLookup, "Tags", 5000);
    }

    @Test
    public void editATag() throws Exception {
        new DataFixtures().saveTag("New Tag");

        TagsPage tagsPage = new TagsPage(pageLookup, uri.toString());
        tagsPage.navigateTo("0");

        pageLookup.findElement(By.linkText("New Tag")).click();

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forElement(pageLookup, By.id("title"), 5000);

        tagsPage.setTitle("I've edited the tag");
        tagsPage.completeAction();

        tagsPage.focus();
        Wait.forText(pageLookup, "I've edited the tag", 5000);

        assertFalse(tagsPage.containsText("New Tag"));
    }

    @Test
    public void add2Tags() throws Exception {
        TagsPage tagsPage = new TagsPage(pageLookup, uri.toString());
        tagsPage.navigateTo("0");
        Wait.forText(pageLookup, "Tags", 5000);

        tagsPage.clickOnAdd();

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forElement(pageLookup, By.id("title"), 5000);

        tagsPage.setTitle("I'm A Tag");
        tagsPage.completeAction();
        tagsPage.focus();

        Wait.forText(pageLookup, "I'm A Tag", 5000);

        tagsPage.clickOnAdd();

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forElement(pageLookup, By.id("title"), 5000);

        tagsPage.setTitle("I'm Another Tag");
        tagsPage.completeAction();

        tagsPage.focus();
        Wait.forText(pageLookup, "I'm Another Tag", 5000);

    }

    @Test
    public void cannotAddEmptyTag() {
        TagsPage tagsPage = new TagsPage(pageLookup, uri.toString());
        tagsPage.navigateTo("0");
        Wait.forText(pageLookup, "Tags", 5000);

        tagsPage.clickOnAdd();

        Wait.forFrame(pageLookup, Constants.LIGHTWINDOW_IFRAME, 5000);
        Wait.forElement(pageLookup, By.id("title"), 5000);

        tagsPage.setTitle("");
        tagsPage.completeAction();
        assertTrue(tagsPage.containsText("Please input a title", 4000));
    }


}
