package uk.co.itstherules.ui.pages.change;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.pages.Completeable;
import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.list.StatusesPage;

public class ChangeStatusPage extends Page<ChangeStatusPage> implements Completeable<StatusesPage> {

    protected final String appRoot;
    protected WebElement title;
    private WebElement completeActionButton;
    private final ChangeTypePage changeType;

    public ChangeStatusPage(String appRoot, WebDriver driver, ChangeTypePage changeType) {
        super(driver, appRoot);
        this.appRoot = appRoot;
        this.changeType = changeType;
        title = driver.findElement(By.name("title"));
        completeActionButton = driver.findElement(By.name("completeAction"));
    }

    public ChangeStatusPage setTitle(String title) {
        clearAndPopulate(this.title, title);
        return this;
    }

    public StatusesPage complete() {
        completeActionButton.click();
        StatusesPage statusesPage = new StatusesPage(appRoot, driver);
        statusesPage.focus();
        return statusesPage;
    }

    @Override
    public String getAction() {
        return changeType.name();
    }

    @Override
    public String getController() {
        return "Statuses";
    }

}
