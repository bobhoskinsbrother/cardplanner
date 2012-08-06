package uk.co.itstherules.ui.pages.show;

import org.openqa.selenium.WebDriver;

import uk.co.itstherules.ui.pages.Page;

public class ShowSignUpPage extends Page<ShowSignUpPage> {
	
	public ShowSignUpPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
    }

	@Override
    public String getAction() {
	    return "Show";
    }

	@Override
    public String getController() {
	    return "SignUp";
    }

}
