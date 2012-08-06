package uk.co.itstherules.ui.pages.change;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.list.PeoplePage;

public class ChangePersonPage extends Page<ChangePersonPage> {

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

	private final ChangeTypePage changeType;
	
	
	public ChangePersonPage(String appRoot, WebDriver driver, ChangeTypePage changeType) {
		super(driver, appRoot);
		this.appRoot = appRoot;
		this.driver = driver;
		this.changeType = changeType;
		PageFactory.initElements(driver, this);
    }


	public ChangePersonPage setTitle(String cardTitle) {
		clearAndPopulate(titleField, cardTitle);
		return this;
    }

	public ChangePersonPage setInitials(String initials) {
		clearAndPopulate(initialsField, initials);
		return this;
    }

	public ChangePersonPage setEmail(String email) {
		clearAndPopulate(emailField, email);
		return this;
    }
	
	public ChangePersonPage setPassword(String password) {
		clearAndPopulate(passwordField, password);
		return this;
    }

	public ChangePersonPage setConfirmPassword(String password) {
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

	public ChangePersonPage setImage(String image) {
		driver.findElement(By.id(image)).click();
		return this;
    }


	@Override
    public String getAction() {
	    return changeType.name();
    }


	@Override
    public String getController() {
	    return "People";
    }


	public ChangePersonPage setFirstName(String firstName) {
		clearAndPopulate(firstNameField, firstName);
	    return this;
    }


	public ChangePersonPage setLastName(String lastName) {
		clearAndPopulate(lastNameField, lastName);
	    return this;
    }


}
