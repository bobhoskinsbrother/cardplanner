package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.add.AddSignUpPage;
import uk.co.itstherules.ui.pages.show.ShowSignUpPage;
import uk.co.itstherules.ui.personas.BasicPersona;

public class SignUpTest {
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
    public void addANewUserUsingCardPlannerProcess() throws Exception {
        DataInitializer.initializeData(file, "CardPlanner");

        BasicPersona velma = new BasicPersona("velma");
        AddSignUpPage signUpPage = new AddSignUpPage("http://localhost:9999/SimpleWithSecurity", driver).navigateTo("0");

        ShowSignUpPage signUpShowPage = velma.<ShowSignUpPage>signUp(signUpPage);
        Assert.assertTrue(signUpShowPage.containsText(""));
        Assert.fail();

//			tryToLogin
//			fail
//			loginAsBase
//			setToActive
//			LogOut
//			logInAsUser
    }
}
