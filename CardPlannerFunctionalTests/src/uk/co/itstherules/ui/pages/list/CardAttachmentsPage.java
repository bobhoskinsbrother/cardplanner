package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import uk.co.itstherules.ui.pages.Page;

public class CardAttachmentsPage extends Page<CardAttachmentsPage> {

	/*
	 * TODO: WEBDRIVER DEFECT, WHEN USING THE ANNOTATION APPROACH BELOW, YOU CAN'T DRAG n DROP
	 */
	
	@FindBy(id="toggleVisibility") private WebElement toggleUploadFormButton;
	@FindBy(id="title") private WebElement titleField;
	@FindBy(id="attachment.file") private WebElement attachmentField;
	@FindBy(id="attachmentsScrollerArea") private WebElement notAttachedPanel;
	@FindBy(name="completeAction") private WebElement uploadButton;
	
	public CardAttachmentsPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
	}

	public WebElement getAttachmentField() { return attachmentField; }
	public WebElement getTitleField() { return titleField; }
	public WebElement getToggleUploadFormButton() { return toggleUploadFormButton; }
	public WebElement getUploadButton() { return uploadButton; }
	public WebElement getAttachedPanel() { return driver.findElement(By.id("attached")); }
	public WebElement getNotAttachedPanel() { return notAttachedPanel;  }

	@Override
    public String getAction() {
	    return "List";
    }

	@Override
    public String getController() {
	    return "CardAttachments";
    }

}
