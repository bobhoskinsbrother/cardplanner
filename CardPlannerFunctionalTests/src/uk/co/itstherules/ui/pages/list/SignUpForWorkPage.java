package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.WebDriver;

import uk.co.itstherules.ui.pages.Page;

public class SignUpForWorkPage extends Page<SignUpForWorkPage> {

	public SignUpForWorkPage(WebDriver driver, String appRoot) {
	    super(driver, appRoot);
    }

	@Override
    public String getAction() {
	    return "List";
    }

	@Override
    public String getController() {
	    return "SignUpForWork";
    }

}
