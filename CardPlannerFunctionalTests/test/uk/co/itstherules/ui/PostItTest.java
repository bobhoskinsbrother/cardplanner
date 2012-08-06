package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.change.ChangePostItPage;
import uk.co.itstherules.ui.pages.change.ChangeTypePage;
import uk.co.itstherules.ui.pages.list.PostItPage;
import uk.co.itstherules.ui.pages.show.ShowPostItPage;
import uk.co.itstherules.ui.personas.BasicPersona;

public class PostItTest {

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
    public void editAPostIt() throws Exception {
    }

    @Test
    public void addAPostItWithoutAttachment() throws Exception {
        DataInitializer.initializeData(file, "CardPlanner");
        ChangePostItPage addPostItPage = new ChangePostItPage("http://localhost:9999/Simple", driver, ChangeTypePage.Add).navigateTo("0");
        BasicPersona daphne = new BasicPersona("daphne");
        PostItPage listPostItPage = (PostItPage) daphne.addPostIt(addPostItPage);
        Assert.assertTrue(listPostItPage.containsText(daphne.getMemory().getPostItTitle()));
        ShowPostItPage showPostItPage = daphne.selectShow(listPostItPage, 0);
        Assert.assertTrue(showPostItPage.containsText(daphne.getMemory().getPostItTitle()));
    }

    @Test
    public void cantAddAnIncompletePostIt() throws Exception {
    }

    @Test
    public void addAPostItWithAttachment() throws Exception {
    }

    @Test
    public void dragPostIt() throws Exception {
    }

}
