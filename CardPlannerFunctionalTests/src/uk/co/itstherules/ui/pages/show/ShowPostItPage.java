package uk.co.itstherules.ui.pages.show;

import org.openqa.selenium.WebDriver;

import uk.co.itstherules.ui.pages.Page;

public class ShowPostItPage extends Page<ShowPostItPage> {

	public ShowPostItPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
    }

	@Override
    public String getAction() {
	    return "Show";
    }

	@Override
    public String getController() {
	    return "PostIt";
    }

}
