package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.WebDriver;

public class ArchivedCardsPage extends CardManipulationPage<ArchivedCardsPage> {
	public ArchivedCardsPage(String appRoot, WebDriver driver) {
		super(appRoot, driver);
	}

	@Override
    public String getAction() {
	    return "List";
    }

	@Override
    public String getController() {
	    return "ArchivedCards";
    }

}
