package uk.co.itstherules.ui;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.list.EffortTypesPage;
import uk.co.itstherules.ui.personas.BasicPersona;
import uk.co.itstherules.ui.personas.Scrappy2;

@Ignore
public class EffortTypesTest {

    private static String databaseFile = "simple";
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
    public void deleteEffortType() throws Exception {
        DataInitializer.initializeData(databaseFile + "_one_card", "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        EffortTypesPage page = new EffortTypesPage("http://localhost:9999/CardPlanner", driver).navigateTo("0");
        page = norville.delete(page, 0);
        Assert.assertFalse(page.containsText(norville.getMemory().getEffortTypeTitle(), 0));
    }

    @Test
    public void editEffortType() throws Exception {
        DataInitializer.initializeData(databaseFile + "_one_card", "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        EffortTypesPage page = new EffortTypesPage("http://localhost:9999/CardPlanner", driver).navigateTo("0");
        page = norville.selectEdit(page, 0);
        page = norville.edit(page);
        Assert.assertTrue(page.containsText(norville.getMemory().getEffortTypeTitle()));
        Assert.assertTrue(page.containsText(norville.getMemory().getRate()));
    }

    @Test
    public void addEffortType() throws Exception {
        DataInitializer.initializeData(databaseFile, "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        EffortTypesPage page = new EffortTypesPage("http://localhost:9999/CardPlanner", driver).navigateTo("0");
        page = norville.add(page);
        Assert.assertTrue(page.containsText(norville.getMemory().getEffortTypeTitle()));
    }

    @Test
    public void cannotAddEmptyEffortType() throws Exception {
        DataInitializer.initializeData(databaseFile, "CardPlanner");
        EffortTypesPage page = new EffortTypesPage("http://localhost:9999/CardPlanner", driver).navigateTo("0");
        BasicPersona scrappy = new Scrappy2();
        page = scrappy.add(page);
        Assert.assertTrue(page.containsErrorForTitleField());
    }
}