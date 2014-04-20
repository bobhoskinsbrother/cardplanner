package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.pages.list.ResurrectDeadThingsPage;

@Ignore
public class ResurrectDeadThingsTest {

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
        new ResurrectDeadThingsPage(driver, "http://localhost:9999/Simple").navigateTo("0");
        BrowserWait.forText(driver, "Resurrect Dead Things", 5000);
    }

//	@Test public void reviveEffortType() throws Exception {
//    }
//	
//	@Test public void reviveCard() throws Exception {
//    }
//
//	@Test public void revivePerson() throws Exception {
//    }
//
//	@Test public void reviveSpan() throws Exception {
//    }
//
//	@Test public void reviveStatus() throws Exception {
//    }
//
//	@Test public void reviveTag() throws Exception {
//    }
//
//	@Test public void reviveTeam() throws Exception {
//    }
//
//	@Test public void reviveValueType() throws Exception {
//    }
//
//	@Test public void reviveCardType() throws Exception {
//    }

}
