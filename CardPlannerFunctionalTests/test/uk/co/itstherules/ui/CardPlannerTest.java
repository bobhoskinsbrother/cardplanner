package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.list.CardPlannerPage;

//TODO: flakey - dunno why - need to look at it
public class CardPlannerTest {

    private static String file;
    private static WebDriver driver;

    @BeforeClass
    public static void setup() throws Exception {
        file = "simple";
        driver = WebDriverInstance.get();
    }

    @AfterClass
    public static void destroy() {
        WebDriverInstance.destroy();
    }

    @Test
    public void pageOpens() throws Exception {
        DataInitializer.initializeData(file, "CardPlanner");
        CardPlannerPage page = new CardPlannerPage("http://localhost:9999/Simple", driver).navigateTo("0");
        BrowserWait.forText(driver, "Empty", 5000);
        Assert.assertFalse(page.containsText("HTTP ERROR: 404", 5000));
    }

}
