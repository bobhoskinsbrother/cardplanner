package uk.co.itstherules.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.pages.show.LoginPage;

public abstract class TemplatePage<T> extends Page<T> {

    private WebElement sideBarTab;
    private WebElement logoutButton;

    public TemplatePage(WebDriver driver, String appRoot) {
        super(driver, appRoot);
        sideBarTab = driver.findElement(By.id("sideBarTab"));
        logoutButton = driver.findElement(By.id("logout"));
    }

    public LoginPage clickLogout(Page<?> page) {
        logoutButton.click();
        return new LoginPage(appRoot, driver);
    }

    public void openLinksPanel() {
        sideBarTab.click();
    }

}