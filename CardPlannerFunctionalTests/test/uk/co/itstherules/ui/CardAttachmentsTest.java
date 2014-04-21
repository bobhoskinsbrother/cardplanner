package uk.co.itstherules.ui;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import uk.co.itstherules.TempFileWriter;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.server.CardPlannerServer;
import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.functions.DataFixtures;
import uk.co.itstherules.ui.pages.list.CardAttachmentsPage;
import uk.co.itstherules.ui.personas.BasicPersona;

import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;
import static uk.co.itstherules.junit.extension.WebMatcher.textOnThePage;

public class CardAttachmentsTest {

    private static WebDriver pageLookup;
    private static String path;
    private static BasicPersona norville;
    private static CardPlannerServer server;
    private static URI uri;
    private DataFixtures fixtures;
    private CardModel card;

    @BeforeClass
    public static void setup() throws Exception {
        norville = new BasicPersona("norville");
        pageLookup = WebDriverInstance.get();
        server = new CardPlannerServer(TEST).port(0);
        uri = server.startServer();
    }

    @AfterClass
    public static void destroy() throws Exception {
        WebDriverInstance.destroy();
        server.destroy();
    }

    @Before
    public void s() throws Exception {
        fixtures = new DataFixtures();
        card = fixtures.saveSimpleCard();
        path = TempFileWriter.write(norville.getMemory().getFilePathForUpload());
    }

    @After
    public void t() throws Exception {
        fixtures.destroy(card);
        TempFileWriter.destroy(path);
    }

    @Test
    public void addAnAttachment() throws Exception {
        CardAttachmentsPage page = new CardAttachmentsPage(uri.toString(), pageLookup);
        page = norville.browseToAttachmentsForCard(page);
        norville.uploadWith(page, path);
        Wait.forText(pageLookup, norville.getMemory().getAttachmentTitle(), 5000);
        norville.clickOnUploadedFileOn(page);
        final WindowSwitcher switcher = new WindowSwitcher(pageLookup);
        switcher.toLatest();
        assertThat("I am a text file that can be uploaded.\n" +
                "\n" +
                "You should know that it is failry easy to do.", is(textOnThePage(pageLookup)));
        switcher.toOriginal();
    }

    @Ignore @Test
    public void associateAnExistingAttachment() throws Exception {
        CardAttachmentsPage page = new CardAttachmentsPage(uri.toString(), pageLookup);
        page = norville.browseToAttachmentsForCard(page);
        WebElement attachedPanel = page.getAttachedPanel();
        WebElement notAttachedPanel = page.getNotAttachedPanel();

        WebElement notAttached = notAttachedPanel.findElement(By.id(norville.getMemory().getAttachmentIdentity()));
        Actions builder = new Actions(pageLookup);
        Action dragAndDrop = builder.clickAndHold(notAttached).moveToElement(attachedPanel).release(attachedPanel).build();
        dragAndDrop.perform();
        try {
            //TODO: a less horrible way to handle AJAX events
            Thread.sleep(750);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(page.getAttachedPanel().getText().contains("BigFreddy"));
    }

    private class WindowSwitcher {

        private final String before;
        private final WebDriver driver;

        private WindowSwitcher(WebDriver driver) {
            this.driver = driver;
            before = driver.getWindowHandle();
        }

        private void toOriginal() {driver.switchTo().window(before);}

        private void toLatest() {
            for(String newHandle : driver.getWindowHandles()){
                driver.switchTo().window(newHandle);
            }
        }
    }

}
