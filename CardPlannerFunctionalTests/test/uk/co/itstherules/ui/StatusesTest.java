package uk.co.itstherules.ui;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.change.ChangeStatusPage;
import uk.co.itstherules.ui.pages.list.StatusesPage;
import uk.co.itstherules.ui.personas.BasicPersona;

public class StatusesTest {

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
    public void editAStatus() throws Exception {
        DataInitializer.initializeData(file + "_one_card", "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        StatusesPage statusesPage = new StatusesPage("http://localhost:9999/Simple", driver).navigateTo("0");
        ChangeStatusPage selected = norville.selectEdit(statusesPage, 0);
        norville.edit(selected);
        BrowserWait.forText(driver, norville.getMemory().getStatusTitle(), 5000);
    }

    @Test
    public void addAStatus() throws Exception {
        DataInitializer.initializeData(file, "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        StatusesPage statuses = new StatusesPage("http://localhost:9999/Simple", driver).navigateTo("0");
        statuses = norville.add(statuses);
        Assert.assertTrue(statuses.containsText(norville.getMemory().getStatusTitle()));
    }

    @Test
    public void moveStatusOrder() throws Exception {
        DataInitializer.initializeData(file + "_one_card_three_statuses", "CardPlanner");
        BasicPersona daphne = new BasicPersona("daphne");
        StatusesPage statusesPage = new StatusesPage("http://localhost:9999/Simple", driver).navigateTo("0");
        daphne.change(statusesPage);
        statusesPage = new StatusesPage("http://localhost:9999/Simple", driver).navigateTo("0");
        String statusTitle = daphne.getMemory().getStatusTitle();
        WebElement statusesPanel = statusesPage.getStatusesPanel();
        String text = statusesPanel.getText();
        Assert.assertTrue(text.contains(statusTitle));
        Assert.assertTrue(text.contains("Building the Empire"));
        Assert.assertTrue(text.indexOf(statusTitle) > text.indexOf("Building the Empire"));
    }


}
