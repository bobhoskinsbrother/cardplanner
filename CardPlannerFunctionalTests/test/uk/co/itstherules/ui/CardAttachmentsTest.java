package uk.co.itstherules.ui;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import uk.co.itstherules.junit.extension.WebDriverInstance;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.functions.DataInitializer;
import uk.co.itstherules.ui.pages.list.CardAttachmentsPage;
import uk.co.itstherules.ui.personas.BasicPersona;

@Ignore
public class CardAttachmentsTest {

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
    public void addAnAttachment() throws Exception {
        DataInitializer.initializeData("simple_one_card", "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        CardAttachmentsPage page = new CardAttachmentsPage("http://localhost:9999/Simple", driver);
        page = norville.browseToAttachmentsForCard(page);
        norville.uploadWith(page);
        BrowserWait.forText(driver, norville.getMemory().getAttachmentTitle(), 5000);
        Assert.assertTrue(page.getAttachedPanel().getText().contains("This is a title for... (upload_me.txt)"));
    }

    @Test
    public void associateAnExistingAttachment() throws Exception {
        DataInitializer.initializeData("simple_one_card", "CardPlanner");
        BasicPersona norville = new BasicPersona("norville");
        CardAttachmentsPage page = new CardAttachmentsPage("http://localhost:9999/Simple", driver);
        page = norville.browseToAttachmentsForCard(page);
        WebElement attachedPanel = page.getAttachedPanel();
        WebElement notAttachedPanel = page.getNotAttachedPanel();

        WebElement notAttached = notAttachedPanel.findElement(By.id(norville.getMemory().getAttachmentIdentity()));
        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(notAttached).moveToElement(attachedPanel).release(attachedPanel).build();
        dragAndDrop.perform();
        try {
            //TODO: a less horrible way to handle AJAX events
            Thread.sleep(750);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertTrue(page.getAttachedPanel().getText().contains("BigFreddy"));
    }

}
