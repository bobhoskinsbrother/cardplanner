package uk.co.itstherules.ui;

import org.junit.*;
import org.openqa.selenium.WebDriver;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.change.ChangePersonPage;
import uk.co.itstherules.ui.pages.change.ChangeTypePage;
import uk.co.itstherules.ui.pages.list.PeoplePage;
import uk.co.itstherules.ui.personas.BasicPersona;

@Ignore
public class PeopleTest {

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
    public void canEditDaphneToNorville() throws Exception {
        DataInitializer.initializeData(file + "_one_card", "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        PeoplePage peoplePage = new PeoplePage("http://localhost:9999/Simple", driver).navigateTo("0");
        ChangePersonPage selected = norville.selectEdit(peoplePage, 0);
        norville.edit(selected);
        Assert.assertTrue(peoplePage.containsText(norville.getMemory().getPersonFirstName()));
        Assert.assertTrue(peoplePage.containsText(norville.getMemory().getPersonLastName()));
    }

    @Test
    public void canAddWithNoImage() throws Exception {
        DataInitializer.initializeData(file, "CardPlanner");
        ChangePersonPage addCardPage = new ChangePersonPage("http://localhost:9999/Simple", driver, ChangeTypePage.Add).navigateTo("0");
        BasicPersona norville = new BasicPersona("norville");
        PeoplePage peoplePage = (PeoplePage) norville.add(addCardPage);
        Assert.assertTrue(peoplePage.containsText(norville.getMemory().getPersonFirstName()));
        Assert.assertTrue(peoplePage.containsText(norville.getMemory().getPersonLastName()));
        Assert.assertTrue(peoplePage.containsText(norville.getMemory().getPersonInitials()));
    }

    @Test
    public void canAddWithAnImage() throws Exception {
        DataInitializer.initializeData(file + "_one_card", "CardPlanner");
        ChangePersonPage addCardPage = new ChangePersonPage("http://localhost:9999/Simple", driver, ChangeTypePage.Add).navigateTo("0");
        BasicPersona freddie = new BasicPersona("freddie");
        PeoplePage people = (PeoplePage) freddie.add(addCardPage);
        Assert.assertTrue(people.containsText(freddie.getMemory().getPersonFirstName()));
        Assert.assertTrue(people.containsText(freddie.getMemory().getPersonLastName()));
        Assert.assertTrue(people.containsText(freddie.getMemory().getPersonInitials()));
        Assert.assertTrue(people.containsText(freddie.getMemory().getPersonImageIdentity()));
    }
}
