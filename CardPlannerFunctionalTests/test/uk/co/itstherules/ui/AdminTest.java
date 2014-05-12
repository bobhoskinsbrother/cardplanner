package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.list.AdminPage;

import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;
import static uk.co.itstherules.junit.extension.WebMatcher.textOnThePage;

public class AdminTest {

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
    public void canViewThePage() throws Exception {
        new AdminPage(pageLookup, uri.toString()).navigateTo("0");
        Wait.forText(pageLookup, "Effort Types", 5000);
        assertThat("Card Types", is(textOnThePage(pageLookup)));
        assertThat("Value Types", is(textOnThePage(pageLookup)));
        assertThat("Excel Export", is(textOnThePage(pageLookup)));
        assertThat("Resurrect Dead Things", is(textOnThePage(pageLookup)));
    }


}