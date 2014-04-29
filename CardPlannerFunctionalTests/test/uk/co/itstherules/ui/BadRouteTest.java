package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;

import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;
import static uk.co.itstherules.junit.extension.WebMatcher.textOnThePage;

public class BadRouteTest {

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
    public void badRouteGivesSensibleMessage() throws Exception {
        pageLookup.get(uri.toString() + "/IDontExist/");
        assertThat("The route: /CardPlanner/IDontExist/Show/0/index.xhtml cannot be found", is(textOnThePage(pageLookup)));
    }

}
