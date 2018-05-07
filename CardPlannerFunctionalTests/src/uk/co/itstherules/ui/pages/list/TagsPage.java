package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.pages.Page;

public class TagsPage extends Page<TagsPage> {

	public TagsPage(WebDriver driver, String appRoot) {
	    super(driver, appRoot);
    }


    public TagsPage setTitle(String title) {
        clearAndPopulate(driver.findElement(By.id("title")), title);
        return this;
    }

	@Override
    public String getAction() {
	    return "List";
    }

	@Override
    public String getController() {
	    return "Tags";
    }

    public TagsPage clickOnAdd() {
        driver.findElement(By.id("addTag")).click();
        return this;
    }

    public TagsPage completeAction() {
        Wait.untilVisible(driver, By.id("completeAction"), 5000);
        driver.findElement(By.id("completeAction")).click();
        return this;
    }
}
