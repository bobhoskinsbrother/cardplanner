package uk.co.itstherules.ui.pages.show;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import uk.co.itstherules.ui.pages.Page;

public class LoginPage extends Page<LoginPage> {

	@FindBy(name="username") private WebElement username;
	@FindBy(name="password") private WebElement password;
	@FindBy(name="completeAction") private WebElement submitButton;

	
	public LoginPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
		PageFactory.initElements(driver, this);
    }

	@Override
    public String getAction() {
	    return "Show";
    }

	@Override
    public String getController() {
	    return "Login";
    }

	public void setUsername(String username) {
		clearAndPopulate(this.username, username);
    }

	public void setPassword(String password) {
		clearAndPopulate(this.password, password);
    }

	@SuppressWarnings("unchecked")
    public <T> T submit() {
		submitButton.click();
		try {
			return (T) new HomePage(appRoot, driver);
		} catch (IllegalStateException e) {
			return (T) this;
		}
    }

}
