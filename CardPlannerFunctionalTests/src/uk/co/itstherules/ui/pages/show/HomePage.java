package uk.co.itstherules.ui.pages.show;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import uk.co.itstherules.ui.pages.TemplatePage;

public class HomePage extends TemplatePage<HomePage> {

	public HomePage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
		PageFactory.initElements(driver, this);
    }

	@Override
    public String getAction() {
	    return "Show";
    }

	@Override
    public String getController() {
	    return "Home";
    }

	@Override
    public void verifyIsCorrectPage() throws IllegalStateException {
		if(!driver.getPageSource().contains("<!--Home Page-->")) {
			throw new IllegalStateException("Expected \"<!--Home Page-->\" in the source of this page");
		}
    }

}
