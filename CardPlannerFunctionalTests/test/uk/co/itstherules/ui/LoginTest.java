package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.show.HomePage;
import uk.co.itstherules.ui.pages.show.LoginPage;
import uk.co.itstherules.ui.personas.BasicPersona;

public class LoginTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setup() throws Exception {
        driver = WebDriverInstance.get();
    }

    @AfterClass
    public static void destroy() {
        WebDriverInstance.destroy();
    }

    @Test
    public void canLogin() throws Exception {
        try {
            DataInitializer.initializeData("simple_one_card", "CardPlanner");
            BasicPersona norville = new BasicPersona("norville");
            LoginPage page = new LoginPage("http://localhost:9999/SimpleWithSecurity", driver).navigateTo("0");
            norville.<HomePage>login(page);
            BrowserWait.forText(driver, "<!--Home Page-->", 5000);
        } finally {
        }
    }

    @Test
    public void cannotLoginIncorrectCredentials() throws Exception {
        try {
            DataInitializer.initializeData("simple_one_card", "CardPlanner");
            BasicPersona daphne = new BasicPersona("daphne");
            LoginPage page = new LoginPage("http://localhost:9999/SimpleWithSecurity", driver).navigateTo("0");
            daphne.<LoginPage>login(page);
            BrowserWait.forText(driver, "<!--Login Page-->", 5000);
        } finally {
        }
    }


    @Test
    public void canLoginThenLogout() throws Exception {
        try {
            DataInitializer.initializeData("simple_one_card", "CardPlanner");
            BasicPersona norville = new BasicPersona("norville");
            LoginPage page = new LoginPage("http://localhost:9999/SimpleWithSecurity", driver).navigateTo("0");
            HomePage homePage = norville.<HomePage>login(page);
            BrowserWait.forText(driver, "<!--Home Page-->", 5000);
            page = norville.openLinks(homePage).clickLogout(homePage);
            BrowserWait.forText(driver, "<!--Login Page-->", 5000);
        } finally {
        }
    }
}
