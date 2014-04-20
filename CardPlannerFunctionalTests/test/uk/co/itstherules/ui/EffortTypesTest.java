package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.pages.list.EffortTypesPage;
import uk.co.itstherules.ui.personas.BasicPersona;
import uk.co.itstherules.ui.personas.Scrappy2;

import java.net.URI;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;
import static uk.co.itstherules.junit.extension.WebMatcher.onThePage;

public class EffortTypesTest {

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
    public void deleteEffortType() throws Exception {
        new DataFixtures().saveEffortType("Effort", "NumericBased", "2.35");
        BasicPersona norville = new BasicPersona("norville");
        EffortTypesPage page = new EffortTypesPage(uri.toString(), pageLookup).navigateTo("0");
        BrowserWait.forElement(pageLookup, By.name("deleteButton"), 5000);
        page = norville.delete(page, 0);
        assertFalse(page.containsText(norville.getMemory().getEffortTypeTitle(), 0));
    }

    @Test
    public void editEffortType() throws Exception {
        DataFixtures dataFixtures = new DataFixtures();
        EffortTypeModel reply=null;
        try {
            reply = dataFixtures.saveEffortType("Effort", "NumericBased", "2.35");
            BasicPersona norville = new BasicPersona("norville");
            EffortTypesPage page = new EffortTypesPage(uri.toString(), pageLookup).navigateTo("0");
            page = norville.selectEdit(page, 0);
            norville.edit(page);
            assertThat(norville.getMemory().getEffortTypeTitle(), is(onThePage(pageLookup)));
        } finally {
            dataFixtures.destroy(reply);
        }

    }

    @Test
    public void addAndDeleteEffortType() throws Exception {
        BasicPersona norville = new BasicPersona("norville");
        EffortTypesPage page = new EffortTypesPage(uri.toString(), pageLookup).navigateTo("0");
        page = norville.add(page);
        assertTrue(page.containsText(norville.getMemory().getEffortTypeTitle()));
        page = norville.delete(page, 0);
        assertFalse(page.containsText(norville.getMemory().getEffortTypeTitle(), 0));
    }

    @Test
    public void cannotAddEmptyEffortType() throws Exception {
        EffortTypesPage page = new EffortTypesPage(uri.toString(), pageLookup).navigateTo("0");
        BasicPersona scrappy = new Scrappy2();
        page = scrappy.add(page);
        assertTrue(page.containsErrorForTitleField());
    }

}