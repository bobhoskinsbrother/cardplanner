package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.pages.Page;

public class CardAttachmentsPage extends Page<CardAttachmentsPage> {

    private WebElement titleField;
    private WebElement toggleUploadFormButton;
    private WebElement attachmentField;
    private WebElement notAttachedPanel;
    private WebElement uploadButton;

    public CardAttachmentsPage(String appRoot, WebDriver driver) {
        super(driver, appRoot);
        titleField = driver.findElement(By.id("title"));
        toggleUploadFormButton = driver.findElement(By.id("toggleVisibility"));
        attachmentField = driver.findElement(By.id("attachment.file"));
        notAttachedPanel = driver.findElement(By.id("attachmentsScrollerArea"));
        uploadButton = driver.findElement(By.id("completeAction"));
    }

    public WebElement getAttachmentField() { return attachmentField; }

    public WebElement getTitleField() { return titleField; }

    public WebElement getToggleUploadFormButton() { return toggleUploadFormButton; }

    public WebElement getUploadButton() { return uploadButton; }

    public WebElement getAttachedPanel() { return driver.findElement(By.id("attached")); }

    public WebElement getNotAttachedPanel() { return notAttachedPanel; }

    @Override
    public String getAction() {
        return "List";
    }

    @Override
    public String getController() {
        return "CardAttachments";
    }

}
