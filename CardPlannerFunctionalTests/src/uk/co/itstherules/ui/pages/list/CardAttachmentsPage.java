package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.Page;

public class CardAttachmentsPage extends Page<CardAttachmentsPage> {

    public CardAttachmentsPage(String appRoot, WebDriver driver) {
        super(driver, appRoot);
    }

    public WebElement getAttachmentField() { return driver.findElement(By.id("attachment.file")); }

    public WebElement getTitleField() {
        Wait.untilVisible(driver, By.id("title"), 5000);
        return driver.findElement(By.id("title"));
    }

    public WebElement getToggleUploadFormButton() { return driver.findElement(By.id("toggleVisibility")); }

    public WebElement getUploadButton() { return driver.findElement(By.name("completeAction")); }

    public WebElement getAttachedPanel() { return driver.findElement(By.id("attached")); }

    public WebElement getNotAttachedPanel() { return driver.findElement(By.id("attachmentsScrollerArea")); }

    @Override
    public String getAction() {
        return "List";
    }

    @Override
    public String getController() {
        return "CardAttachments";
    }

    public WebElement uploadedFileLink() {
        return driver.findElement(By.linkText("This is a title for... (...)"));
    }
}
