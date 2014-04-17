package uk.co.itstherules.ui.pages.add;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.change.ChangeTypePage;
import uk.co.itstherules.ui.pages.list.PeoplePage;

public class AddSignUpPage extends Page<AddSignUpPage> {

	protected final WebDriver driver;
	protected final String appRoot;
	
	private WebElement titleField;
	private WebElement firstNameField;
	private WebElement lastNameField;
	private WebElement initialsField;
	private WebElement emailField;
	private WebElement passwordField;
	private WebElement confirmPasswordField;
	private WebElement completeActionButton;

	private final ChangeTypePage changeType = ChangeTypePage.Add;
	
	
	public AddSignUpPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
		this.appRoot = appRoot;
		this.driver = driver;
        titleField = driver.findElement(By.name("title"));
        firstNameField = driver.findElement(By.name("firstName"));
        lastNameField = driver.findElement(By.name("lastName"));
        initialsField = driver.findElement(By.name("initials"));
        emailField = driver.findElement(By.name("email"));
        passwordField = driver.findElement(By.name("password"));
        confirmPasswordField = driver.findElement(By.name("confirmPassword"));
        completeActionButton = driver.findElement(By.name("completeAction"));
    }


	public AddSignUpPage setTitle(String cardTitle) {
		clearAndPopulate(titleField, cardTitle);
		return this;
    }

	public AddSignUpPage setInitials(String initials) {
		clearAndPopulate(initialsField, initials);
		return this;
    }

	public AddSignUpPage setEmail(String email) {
		clearAndPopulate(emailField, email);
		return this;
    }
	
	public AddSignUpPage setPassword(String password) {
		clearAndPopulate(passwordField, password);
		return this;
    }

	public AddSignUpPage setConfirmPassword(String password) {
		clearAndPopulate(confirmPasswordField, password);
		return this;
    }
	
	public Page<?> clickComplete() {
		completeActionButton.click();
		if(driver.getTitle().contains("List People")) {
			PeoplePage peoplePage = new PeoplePage(appRoot, driver);
			peoplePage.focus();
			return peoplePage;
		} else { 
			return this;
		}
    }
	
	public boolean containsErrorForTitleField() {
		String classAttribute = titleField.getAttribute("class");
		return classAttribute.contains("errorbackground") && classAttribute.contains("errorborder");
	}

	@Override
    public String getAction() {
	    return changeType.name();
    }


	@Override
    public String getController() {
	    return "SignUp";
    }


	public AddSignUpPage setFirstName(String firstName) {
		clearAndPopulate(firstNameField, firstName);
	    return this;
    }


	public AddSignUpPage setLastName(String lastName) {
		clearAndPopulate(lastNameField, lastName);
	    return this;
    }
}