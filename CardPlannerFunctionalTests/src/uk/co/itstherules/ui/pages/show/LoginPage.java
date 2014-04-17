package uk.co.itstherules.ui.pages.show;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.pages.Page;

public class LoginPage extends Page<LoginPage> {

	private WebElement username;
	private WebElement password;
	private WebElement submitButton;

	
	public LoginPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
        username = driver.findElement(By.name("username"));
        password = driver.findElement(By.name("password"));
        submitButton = driver.findElement(By.name("completeAction"));
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
