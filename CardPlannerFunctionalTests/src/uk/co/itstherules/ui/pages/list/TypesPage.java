package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.pages.Page;

public abstract class TypesPage<T> extends Page<T> {

	public TypesPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
	}

	@Override public String getAction() {
	    return "List";
    }

	public WebElement getRevealFormButton() {
		return driver.findElement(By.id("toggleVisibility"));
    }

	public void setTitle(String title) {
        BrowserWait.untilVisible(driver, By.name("title"), 1000);
		clearAndPopulate(driver.findElement(By.name("title")), title);
    }

	public WebElement getCompleteButton() {
		return driver.findElement(By.name("completeAction"));
    }

	public void setRate(String rate) {
		clearAndPopulate(driver.findElement(By.name("rate")), rate);
    }

	public boolean containsErrorForTitleField() {
		WebElement titleField = driver.findElement(By.name("title"));
		String classAttribute = titleField .getAttribute("class");
		return classAttribute.contains("errorbackground") && classAttribute.contains("errorborder");
    }

	public WebElement getEditCard(int selectableIndex) {
	    return driver.findElements(By.name("editLink")).get(selectableIndex);
    }

	public WebElement getDeleteButton(int selectableIndex) {
	    return driver.findElements(By.name("deleteButton")).get(selectableIndex);
    }
}
