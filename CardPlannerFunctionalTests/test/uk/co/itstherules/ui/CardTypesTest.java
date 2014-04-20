package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.model.CardTypeModel;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.pages.list.CardTypesPage;
import uk.co.itstherules.ui.personas.BasicPersona;
import uk.co.itstherules.ui.personas.Scrappy2;

import java.net.URI;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;
import static uk.co.itstherules.junit.extension.WebMatcher.onThePage;

public class CardTypesTest {

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
    public void deleteCardType() throws Exception {
        new DataFixtures().saveCardType("Task", "#FF9900");
        BasicPersona norville = new BasicPersona("norville");
        CardTypesPage page = new CardTypesPage(uri.toString(), pageLookup).navigateTo("0");
        BrowserWait.forElement(pageLookup, By.name("deleteButton"), 5000);
        page = norville.delete(page, 0);
        assertFalse(page.containsText(norville.getMemory().getCardTypeTitle(), 0));
    }

    @Test
    public void editCardType() throws Exception {
        DataFixtures dataFixtures = new DataFixtures();
        CardTypeModel reply=null;
        try {
            reply = dataFixtures.saveCardType("Task", "#FF9900");
            BasicPersona norville = new BasicPersona("norville");
            CardTypesPage page = new CardTypesPage(uri.toString(), pageLookup).navigateTo("0");
            page = norville.selectEdit(page, 0);
            norville.edit(page);
            assertThat(norville.getMemory().getCardTypeTitle(), is(onThePage(pageLookup)));
        } finally {
            dataFixtures.destroy(reply);
        }

    }

    @Test
    public void addAndDeleteCardType() throws Exception {
        BasicPersona norville = new BasicPersona("norville");
        CardTypesPage page = new CardTypesPage(uri.toString(), pageLookup).navigateTo("0");
        page = norville.add(page);
        assertTrue(page.containsText(norville.getMemory().getCardTypeTitle()));
        page = norville.delete(page, 0);
        assertFalse(page.containsText(norville.getMemory().getCardTypeTitle(), 0));
    }

    @Test
    public void cannotAddEmptyCardType() throws Exception {
        CardTypesPage page = new CardTypesPage(uri.toString(), pageLookup).navigateTo("0");
        BasicPersona scrappy = new Scrappy2();
        page = scrappy.add(page);
        assertTrue(page.containsErrorForTitleField());
    }

}
