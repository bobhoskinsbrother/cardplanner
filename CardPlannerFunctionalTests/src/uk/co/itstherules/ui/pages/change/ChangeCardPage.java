package uk.co.itstherules.ui.pages.change;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.list.CardPlannerPage;
import uk.co.itstherules.ui.pages.list.CardsPage;

public class ChangeCardPage extends Page<ChangeCardPage>  {

	protected final WebDriver driver;
	protected final String appRoot;
	
	@FindBy(name = "title") private WebElement titleField;
	@FindBy(name = "body") private WebElement bodyField;
	@FindBy(name = "effort.amount") private WebElement effortAmountField;
	@FindBy(name = "value.amount") private WebElement valueAmountField;
	@FindBy(name = "completeAction") private WebElement completeActionButton;
	@FindBy(id = "addTagButton") private WebElement addTagButton;
	@FindBy(name = "tag") private WebElement tag;
	private final ChangeTypePage changeType;
	
	
	public ChangeCardPage(String appRoot, WebDriver driver, ChangeTypePage changeType) {
		super(driver, appRoot);
		this.appRoot = appRoot;
		this.driver = driver;
		this.changeType = changeType;
		PageFactory.initElements(driver, this);
    }
	
    public ChangeCardPage addTag(String tag) {
    	clearAndPopulate(this.tag, tag);
		addTagButton.click();
		return this;
    }

	public ChangeCardPage setTitle(String cardTitle) {
		clearAndPopulate(titleField, cardTitle);
		return this;
    }

	public ChangeCardPage setBody(String cardBody) {
		clearAndPopulate(bodyField, cardBody);
		return this;
    }

	public ChangeCardPage setEffortAmount(String effortAmount) {
		clearAndPopulate(effortAmountField, effortAmount);
		return this;
    }

	public ChangeCardPage setValueAmount(String valueAmount) {
		clearAndPopulate(valueAmountField, valueAmount);
		return this;
    }

	public Page<?> clickComplete() {
		completeActionButton.click();
		if(driver.getTitle().contains("List Cards")) {
			return new CardsPage(appRoot, driver);
		} else if(driver.getTitle().contains("Plan Cards")) {
			return new CardPlannerPage(appRoot, driver);
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
	    return "Cards";
    }

}
