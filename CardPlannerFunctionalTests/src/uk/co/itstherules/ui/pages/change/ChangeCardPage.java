package uk.co.itstherules.ui.pages.change;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.list.StoryBoardPage;
import uk.co.itstherules.ui.pages.list.CardsPage;

public class ChangeCardPage extends Page<ChangeCardPage> {

    protected final WebDriver driver;
    protected final String appRoot;
    private WebElement titleField;
    private WebElement bodyField;
    private WebElement completeActionButton;
    private WebElement effortAmountField;
    private WebElement valueAmountField;
    private WebElement addTagButton;
    private WebElement tag;
    private final ChangeTypePage changeType;

    public ChangeCardPage(String appRoot, WebDriver driver, ChangeTypePage changeType) {
        super(driver, appRoot);
        this.appRoot = appRoot;
        this.driver = driver;
        this.changeType = changeType;
        titleField = driver.findElement(By.name("title"));
        bodyField = driver.findElement(By.name("body"));
        completeActionButton = driver.findElement(By.name("completeAction"));
        effortAmountField = driver.findElement(By.name("effort.amount"));
        valueAmountField = driver.findElement(By.name("value.amount"));
        addTagButton = driver.findElement(By.id("addTagButton"));
        tag = driver.findElement(By.name("tag"));
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
        if (driver.getTitle().contains("List Cards")) {
            return new CardsPage(appRoot, driver);
        } else if (driver.getTitle().contains("Plan Cards")) {
            return new StoryBoardPage(appRoot, driver);
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
