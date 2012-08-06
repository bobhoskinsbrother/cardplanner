package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.functions.Constants;
import uk.co.itstherules.ui.pages.Addable;
import uk.co.itstherules.ui.pages.Editable;
import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.change.ChangeStatusPage;
import uk.co.itstherules.ui.pages.change.ChangeTypePage;

public class StatusesPage extends Page<StatusesPage> implements Editable<ChangeStatusPage>, Addable<ChangeStatusPage> {

	private final String appRoot;
	
	public StatusesPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
		this.appRoot = appRoot;
		PageFactory.initElements(driver, this);
    }

	public WebElement getStatusesPanel() {
		return driver.findElement(By.id("statuses"));
    }
	

	public ChangeStatusPage selectEditCard(int index) {
		WebElement editStatusLink = driver.findElements(By.name("editStatusLink")).get(index);
		editStatusLink.click();
		BrowserWait.forFrame(driver, Constants.LIGHTWINDOW_IFRAME, 5000);
		BrowserWait.forElement(driver, By.name("title"), 6000);
		return new ChangeStatusPage(appRoot, driver, ChangeTypePage.Edit);
    }

	public StatusesPage dragStatusRight(String identity) {
		Actions builder = new Actions(driver);
		WebElement status = driver.findElement(By.id(identity));
		Action dragAndDrop = builder.clickAndHold(status).moveByOffset(900, 0).release(status).build();
		dragAndDrop.perform();
		return this;
    }

	@Override
    public ChangeStatusPage selectAdd() {
		WebElement addStatusLink = driver.findElement(By.id("addStatus"));
		addStatusLink.click();
		BrowserWait.forFrame(driver, Constants.LIGHTWINDOW_IFRAME, 5000);
		BrowserWait.forElement(driver, By.name("title"), 6000);
		return new ChangeStatusPage(appRoot, driver, ChangeTypePage.Add);
    }

	@Override
    public String getAction() {
	    return "List";
    }

	@Override
    public String getController() {
	    return "Statuses";
    }

}
