package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import uk.co.itstherules.ui.functions.Wait;
import uk.co.itstherules.ui.functions.Constants;
import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.show.ShowPostItPage;

public class PostItPage extends Page<PostItPage> {
	
	private final String appRoot;

	public PostItPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
		this.appRoot = appRoot;
	}

	public ShowPostItPage selectShowCard(int selectableIndex) {
		WebElement showButton = driver.findElements(By.name("showPostIt")).get(selectableIndex);
		showButton.click();
		Wait.forFrame(driver, Constants.LIGHTWINDOW_IFRAME, 5000);
		return new ShowPostItPage(appRoot, driver);
    }

	@Override
    public String getAction() {
	    return "List";
    }

	@Override
    public String getController() {
	    return "PostIt";
    }


}
