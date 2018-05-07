package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.functions.Constants;
import uk.co.itstherules.ui.pages.Editable;
import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.change.ChangePersonPage;
import uk.co.itstherules.ui.pages.change.ChangeTypePage;

public class PeoplePage extends Page<PeoplePage> implements Editable<ChangePersonPage> {

	private final String appRoot;

	public PeoplePage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
		this.appRoot = appRoot;
	}

	public ChangePersonPage selectEditCard(int index) {
		WebElement editPersonLink = driver.findElements(By.name("editPersonLink")).get(index);
		editPersonLink.click();
		Wait.forFrame(driver, Constants.LIGHTWINDOW_IFRAME, 5000);
		Wait.forElement(driver, By.name("firstName"), 6000);
		return new ChangePersonPage(appRoot, driver, ChangeTypePage.Edit);
	}

	@Override
    public String getAction() {
	    return "List";
    }

	@Override
    public String getController() {
	    return "People";
    }

}