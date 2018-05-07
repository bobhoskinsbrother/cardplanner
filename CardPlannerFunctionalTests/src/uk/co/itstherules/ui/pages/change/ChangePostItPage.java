package uk.co.itstherules.ui.pages.change;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.list.PostItPage;

public class ChangePostItPage extends Page<ChangePostItPage> {

    protected final WebDriver driver;
    protected final String appRoot;
    private WebElement titleField;
    private WebElement bodyField;
    private WebElement colourField;
    private WebElement completeActionButton;
    private final ChangeTypePage changeType;

    public ChangePostItPage(String appRoot, WebDriver driver, ChangeTypePage changeType) {
        super(driver, appRoot);
        this.appRoot = appRoot;
        this.driver = driver;
        this.changeType = changeType;
        titleField = driver.findElement(By.name("title"));
        bodyField = driver.findElement(By.name("body"));
        colourField = driver.findElement(By.name("colour"));
        completeActionButton = driver.findElement(By.name("completeAction"));
    }

    public ChangePostItPage setColour(String colour) {
        clearAndPopulate(colourField, colour);
        return this;
    }

    public ChangePostItPage setTitle(String cardTitle) {
        clearAndPopulate(titleField, cardTitle);
        return this;
    }

    public ChangePostItPage setBody(String cardBody) {
        clearAndPopulate(bodyField, cardBody);
        return this;
    }

    public Page<?> clickComplete() {
        completeActionButton.click();
        if (driver.getTitle().contains("PostIt")) {
            return new PostItPage(appRoot, driver);
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
        return "PostIt";
    }

}
