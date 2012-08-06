package uk.co.itstherules.ui;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.list.ValueTypesPage;
import uk.co.itstherules.ui.personas.BasicPersona;
import uk.co.itstherules.ui.personas.Scrappy2;

public class ValueTypesTest {

    private static String file;
    private static WebDriver driver;

    @BeforeClass
    public static void setup() throws Exception {
        file = "simple";
        driver = WebDriverInstance.get();
    }

    @Test
    public void deleteValueType() throws Exception {
        DataInitializer.initializeData(file + "_one_card", "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        ValueTypesPage page = new ValueTypesPage("http://localhost:9999/Simple", driver).navigateTo("0");
        page = norville.delete(page, 0);
        Assert.assertFalse(page.containsText(norville.getMemory().getValueTypeTitle(), 0));
    }

    @Test
    public void editValueType() throws Exception {
        DataInitializer.initializeData(file + "_one_card", "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        ValueTypesPage page = new ValueTypesPage("http://localhost:9999/Simple", driver).navigateTo("0");
        page = norville.selectEdit(page, 0);
        page = norville.edit(page);
        Assert.assertTrue(page.containsText(norville.getMemory().getValueTypeTitle()));
        Assert.assertTrue(page.containsText(norville.getMemory().getRate()));
    }

    @Test
    public void addValueType() throws Exception {
        DataInitializer.initializeData(file, "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        ValueTypesPage page = new ValueTypesPage("http://localhost:9999/Simple", driver).navigateTo("0");
        page = norville.add(page);
        Assert.assertTrue(page.containsText(norville.getMemory().getValueTypeTitle()));
    }

    @Test
    public void cannotAddEmptyValueType() throws Exception {
        DataInitializer.initializeData(file, "CardPlanner");
        ValueTypesPage page = new ValueTypesPage("http://localhost:9999/Simple", driver).navigateTo("0");
        BasicPersona scrappy = new Scrappy2();
        page = scrappy.add(page);
        Assert.assertTrue(page.containsErrorForTitleField());
    }

}