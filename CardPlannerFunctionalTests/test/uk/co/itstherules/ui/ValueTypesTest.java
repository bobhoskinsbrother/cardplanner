package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.list.ValueTypesPage;
import uk.co.itstherules.ui.personas.BasicPersona;
import uk.co.itstherules.ui.personas.Scrappy2;

import java.net.URI;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;
import static uk.co.itstherules.junit.extension.WebMatcher.textOnThePage;

public class ValueTypesTest {

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
    public void deleteValueType() throws Exception {
        new DataFixtures().saveValueType("Dollar", "2.35");
        BasicPersona norville = new BasicPersona("norville");
        ValueTypesPage page = new ValueTypesPage(uri.toString(), pageLookup).navigateTo("0");
        Wait.forElement(pageLookup, By.name("deleteButton"), 5000);
        page = norville.delete(page, 0);
        assertFalse(page.containsText(norville.getMemory().getValueTypeTitle(), 0));
    }

    @Test
    public void editValueType() throws Exception {
        DataFixtures dataFixtures = new DataFixtures();
        ValueTypeModel reply=null;
        try {
            reply = dataFixtures.saveValueType("FTEs", "1.2");
            BasicPersona norville = new BasicPersona("norville");
            ValueTypesPage page = new ValueTypesPage(uri.toString(), pageLookup).navigateTo("0");
            page = norville.selectEdit(page, 0);
            norville.edit(page);
            assertThat(norville.getMemory().getValueTypeTitle(), is(textOnThePage(pageLookup)));
        } finally {
            dataFixtures.destroy(reply);
        }

    }

    @Test
    public void addAndDeleteValueType() throws Exception {
        BasicPersona norville = new BasicPersona("norville");
        ValueTypesPage page = new ValueTypesPage(uri.toString(), pageLookup).navigateTo("0");
        page = norville.add(page);
        assertTrue(page.containsText(norville.getMemory().getValueTypeTitle()));
        page = norville.delete(page, 0);
        assertFalse(page.containsText(norville.getMemory().getValueTypeTitle(), 0));
    }

    @Test
    public void cannotAddEmptyValueType() throws Exception {
        ValueTypesPage page = new ValueTypesPage(uri.toString(), pageLookup).navigateTo("0");
        BasicPersona scrappy = new Scrappy2();
        page = scrappy.add(page);
        assertTrue(page.containsErrorForTitleField());
    }

}