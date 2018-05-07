package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.WebDriver;

public class CardTypesPage extends TypesPage<CardTypesPage> {

	public CardTypesPage(String appRoot, WebDriver driver) {
	    super(appRoot, driver);
    }

	@Override
    public String getController() {
	    return "CardTypes";
    }
}
