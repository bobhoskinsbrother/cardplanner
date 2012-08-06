package uk.co.itstherules.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import uk.co.itstherules.ui.pages.show.LoginPage;

public abstract class TemplatePage<T> extends Page<T> { 
	
	@FindBy(id="sideBarTab") private WebElement sideBarTab;
	@FindBy(id="logout") private WebElement logoutButton;
	
	public TemplatePage(WebDriver driver, String appRoot) {
		super(driver, appRoot);
    }

	public LoginPage clickLogout(Page<?> page) {
		logoutButton.click();
	    return new LoginPage(appRoot, driver);
    }

	public void openLinksPanel() {
		sideBarTab.click();
    }
	
}