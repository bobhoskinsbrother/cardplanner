package uk.co.itstherules.ui.pages.add;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.change.ChangeTypePage;
import uk.co.itstherules.ui.pages.list.PeoplePage;

public class AddSignUpPage extends Page<AddSignUpPage> {

	protected final WebDriver driver;
	protected final String appRoot;
	
	@FindBy(name = "title") private WebElement titleField;
	@FindBy(name = "firstName") private WebElement firstNameField;
	@FindBy(name = "lastName") private WebElement lastNameField;
	@FindBy(name = "initials") private WebElement initialsField;
	@FindBy(name = "email") private WebElement emailField;
	@FindBy(name = "password") private WebElement passwordField;
	@FindBy(name = "confirmPassword") private WebElement confirmPasswordField;
	@FindBy(name = "completeAction") private WebElement completeActionButton;

	private final ChangeTypePage changeType = ChangeTypePage.Add;
	
	
	public AddSignUpPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
		this.appRoot = appRoot;
		this.driver = driver;
		PageFactory.initElements(driver, this);
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