package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.list.HardSumsPage;

@Ignore

public class HardSumsTest {

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
    public void canViewThePage() throws Exception {
        new HardSumsPage(driver, "http://localhost:9999/Simple").navigateTo("0");
        Wait.forText(driver, "Backlog", 5000);
    }

}
