package uk.co.itstherules.ui;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.list.CardPlannerPage;

@Ignore
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
        CardPlannerPage page = new CardPlannerPage("http://localhost:9999/Simple", driver).navigateTo("0");
        Wait.forText(driver, "Empty", 5000);
        Assert.assertFalse(page.containsText("HTTP ERROR: 404", 5000));
    }

}
