package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.list.PagesPage;

@Ignore
public class PagesTest {

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
        try {
            DataInitializer.initializeData(file + "_one_card", "CardPlanner");
            PagesPage page = new PagesPage(driver, "http://localhost:9999/Simple").navigateTo("0");
            page.verifyIsCorrectPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        }
    }


}
