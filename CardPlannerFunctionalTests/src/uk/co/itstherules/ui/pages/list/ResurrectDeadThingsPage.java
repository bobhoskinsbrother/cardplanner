package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.pages.Page;

import java.util.List;

public class ResurrectDeadThingsPage extends Page<ResurrectDeadThingsPage> {

	public ResurrectDeadThingsPage(WebDriver driver, String appRoot) {
	    super(driver, appRoot);
    }

	@Override
    public String getAction() {
	    return "List";
    }

	@Override
    public String getController() {
	    return "Resurrection";
    }

    public void undelete(String cardId) {
        driver.findElement(By.id("undelete" + cardId)).click();
    }

    public void selectType(String type) {
        List<WebElement> select = driver.findElement(By.id("title")).findElements(By.tagName("option"));
        for (WebElement element : select) {
            if(type.equals(element.getText())) {
                element.click();
                break;
            }
        }
    }
}
