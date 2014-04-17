package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.pages.list.AdminPage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.itstherules.junit.extension.WebMatcher.onThePage;

public class AdminTest {

    private static WebDriver pageLookup;

    @BeforeClass
    public static void setup() throws Exception {
        pageLookup = WebDriverInstance.get();
    }

    @AfterClass
    public static void destroy() {
        WebDriverInstance.destroy();
    }

    @Test
    public void canViewThePage() throws Exception {
        new AdminPage(pageLookup, "http://localhost:9999/CardPlanner").navigateTo("0");
        BrowserWait.forText(pageLookup, "Effort Types", 5000);
        assertThat("Card Types", is(onThePage(pageLookup)));
        assertThat("Value Types", is(onThePage(pageLookup)));
        assertThat("Excel Export", is(onThePage(pageLookup)));
        assertThat("Setup Email Details", is(onThePage(pageLookup)));
        assertThat("Resurrect Dead Things", is(onThePage(pageLookup)));
        assertThat("Customise Routes", is(onThePage(pageLookup)));
    }


}