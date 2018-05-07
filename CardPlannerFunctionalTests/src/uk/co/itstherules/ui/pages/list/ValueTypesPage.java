package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.WebDriver;

public class ValueTypesPage extends TypesPage<ValueTypesPage> {

	public ValueTypesPage(String appRoot, WebDriver driver) {
	    super(appRoot, driver);
	    
    }

	@Override public String getController() {
	    return "ValueTypes";
    }

}
