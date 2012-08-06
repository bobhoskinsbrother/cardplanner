package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.WebDriver;

public class EffortTypesPage extends TypesPage<EffortTypesPage> {


	public EffortTypesPage(String appRoot, WebDriver driver) {
		super(appRoot, driver);
	}

	@Override
    public String getController() {
	    return "EffortTypes";
    }

}
