package uk.co.itstherules.ui.pages.show;

import org.openqa.selenium.WebDriver;
import uk.co.itstherules.ui.pages.TemplatePage;

public class HomePage extends TemplatePage<HomePage> {

	public HomePage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
    }

	@Override
    public String getAction() {
	    return "Show";
    }

	@Override
    public String getController() {
	    return "Home";
    }

}
