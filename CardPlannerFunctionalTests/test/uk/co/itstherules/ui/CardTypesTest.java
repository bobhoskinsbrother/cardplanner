package uk.co.itstherules.ui;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.list.CardTypesPage;
import uk.co.itstherules.ui.personas.BasicPersona;
import uk.co.itstherules.ui.personas.Scrappy2;

@Ignore
public class CardTypesTest {

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
    public void deleteCardType() throws Exception {
        DataInitializer.initializeData(file + "_one_card", "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        CardTypesPage page = new CardTypesPage("http://localhost:9999/CardPlanner", driver).navigateTo("0");
        page = norville.delete(page, 0);
        Assert.assertFalse(page.containsText(norville.getMemory().getCardTypeTitle(), 0));
    }

    @Test
    public void editCardType() throws Exception {
        DataInitializer.initializeData(file + "_one_card", "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        CardTypesPage page = new CardTypesPage("http://localhost:9999/CardPlanner", driver).navigateTo("0");
        page = norville.selectEdit(page, 0);
        page = norville.edit(page);
        Assert.assertTrue(page.containsText(norville.getMemory().getCardTypeTitle()));
    }

    @Test
    public void addCardType() throws Exception {
        DataInitializer.initializeData(file, "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        CardTypesPage page = new CardTypesPage("http://localhost:9999/CardPlanner", driver).navigateTo("0");
        page = norville.add(page);
        Assert.assertTrue(page.containsText(norville.getMemory().getCardTypeTitle()));
    }

    @Test
    public void cannotAddEmptyCardType() throws Exception {
        DataInitializer.initializeData(file, "CardPlanner");
        CardTypesPage page = new CardTypesPage("http://localhost:9999/CardPlanner", driver).navigateTo("0");
        BasicPersona scrappy = new Scrappy2();
        page = scrappy.add(page);
        Assert.assertTrue(page.containsErrorForTitleField());
    }

}
