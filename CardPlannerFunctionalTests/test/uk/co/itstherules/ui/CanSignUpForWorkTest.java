package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.list.SignUpForWorkPage;

import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.itstherules.junit.extension.WebMatcher.onThePage;

public class CanSignUpForWorkTest {

    private static WebDriver pageLookup;
    private static CardPlannerServer server;
    private static URI uri;

    @BeforeClass
    public static void setup() throws Exception {
        pageLookup = WebDriverInstance.get();
        server = new CardPlannerServer();
        uri = server.port(0).startServer();
    }

    @AfterClass
    public static void destroy() {
        WebDriverInstance.destroy();
        server.destroy();
    }


    @Test
    public void canViewThePage() throws Exception {
        DataInitializer.initializeData("simple", "CardPlanner");
        new SignUpForWorkPage(pageLookup, uri.toString()).navigateTo("0");
        BrowserWait.forText(pageLookup, "Sign Up For Work", 5000);
        assertThat("Sign Up For Work", is(onThePage(pageLookup)));
    }

}
