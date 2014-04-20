package uk.co.itstherules.ui;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.functions.Constants;
import uk.co.itstherules.ui.pages.change.ChangeCardPage;
import uk.co.itstherules.ui.pages.change.ChangeTypePage;
import uk.co.itstherules.ui.pages.list.ArchivedCardsPage;
import uk.co.itstherules.ui.pages.list.CardManipulationPage;
import uk.co.itstherules.ui.pages.list.CardsPage;
import uk.co.itstherules.ui.pages.show.ShowCardPage;
import uk.co.itstherules.ui.personas.Adder;
import uk.co.itstherules.ui.personas.BasicPersona;
import uk.co.itstherules.ui.personas.Scrappy2;

@Ignore
public class CardsTest {

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
    public void editAnCard() throws Exception {
        BasicPersona norville = new BasicPersona("norville");
        CardsPage cardsPage = new CardsPage("http://localhost:9999/Simple", driver).navigateTo("0");
        ChangeCardPage selected = norville.selectEdit(cardsPage, 0);
        norville.edit(selected);
        BrowserWait.forText(driver, norville.getMemory().getCardTitle(), 5000);
    }

    @Test
    public void addAnCardWithoutTagsWithWikiText() throws Exception {
        ChangeCardPage addCardPage = new ChangeCardPage("http://localhost:9999/Simple", driver, ChangeTypePage.Add).navigateTo("0");
        BasicPersona daphne = new BasicPersona("daphne");
        CardsPage listCardsPage = (CardsPage) daphne.add(addCardPage);
        Assert.assertTrue(listCardsPage.containsText(daphne.getMemory().getCardTitle()));
        ShowCardPage showCardPage = daphne.selectShow(listCardsPage, 0);
        BrowserWait.forFrame(driver, Constants.LIGHTWINDOW_IFRAME, 5000);
        Assert.assertTrue(showCardPage.containsText(daphne.getMemory().getCardTitle()));
        Assert.assertTrue(showCardPage.containsText("As Daphne I need <strong>Scooby</strong> to be brave so that we can catch that nefarious criminal pretending to be a ghost"));
    }


    @Test
    public void cantAddAnIncompleteCard() throws Exception {
        ChangeCardPage addCardPage = new ChangeCardPage("http://localhost:9999/Simple", driver, ChangeTypePage.Add).navigateTo("0");
        Adder scrappy = new Scrappy2();
        addCardPage = (ChangeCardPage) scrappy.add(addCardPage);
        Assert.assertTrue(addCardPage.containsErrorForTitleField());
    }

    @Test
    public void addAnCardWithTags() throws Exception {
        ChangeCardPage addCardPage = new ChangeCardPage("http://localhost:9999/Simple", driver, ChangeTypePage.Add).navigateTo("0");
        BasicPersona terry = new BasicPersona("terry");
        CardsPage listCardsPage = (CardsPage) terry.add(addCardPage);
        Assert.assertTrue(listCardsPage.containsText(terry.getMemory().getCardTitle()));
        ShowCardPage showCardPage = terry.selectShow(listCardsPage, 0);
        Assert.assertTrue(showCardPage.containsText(terry.getMemory().getCardTitle()));
        String[] tags = terry.getMemory().getTags();
        Assert.assertTrue(showCardPage.containsText(tags[0]));
        Assert.assertTrue(showCardPage.containsText(tags[1]));
    }


    @Test
    public void showAnCard() throws Exception {
        BasicPersona scrappy = new BasicPersona("scrappy");
        CardsPage cardsPage = new CardsPage("http://localhost:9999/Simple", driver).navigateTo("0");
        ShowCardPage selected = scrappy.selectShow(cardsPage, 0);
        BrowserWait.forText(driver, scrappy.getMemory().getCardTitle(), 5000);
        Assert.assertTrue(selected.containsText(scrappy.getMemory().getCardBody()));
    }


    @Test
    public void deleteAnCard() throws Exception {
        BasicPersona scrappy = new BasicPersona("scrappy");
        CardsPage cardsPage = new CardsPage("http://localhost:9999/Simple", driver).navigateTo("0");
        CardManipulationPage<?> selected = scrappy.delete(cardsPage, 0);
        Assert.assertTrue(selected.containsText("There are currently no Cards defined"));
    }


    @Test
    public void archiveAnCard() throws Exception {
        BasicPersona scrappy = new BasicPersona("scrappy");
        CardsPage cardsPage = new CardsPage("http://localhost:9999/Simple", driver).navigateTo("0");
        CardManipulationPage<?> selected = scrappy.selectArchive(cardsPage, 0);
        Assert.assertTrue(selected.containsText("There are currently no Cards defined"));

        ArchivedCardsPage archivedCardsPage = new ArchivedCardsPage("http://localhost:9999/Simple", driver).navigateTo("0");
        Assert.assertTrue(archivedCardsPage.containsText("I am an card that requires editing"));
    }


    @Test
    public void dragHeirarchy() throws Exception {
        BasicPersona daphne = new BasicPersona("daphne");
        CardsPage cardsPage = new CardsPage("http://localhost:9999/Simple", driver).navigateTo("0");
        cardsPage = daphne.dragCardIntoCard(cardsPage, 0, 1);
        daphne.selectShow(cardsPage, 1);
        BrowserWait.forText(driver, daphne.getMemory().getCardParent(), 10000);
    }

}
