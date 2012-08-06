package uk.co.itstherules.ui.pages.list;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class CardsPage extends CardManipulationPage<CardsPage> {
	public CardsPage(String appRoot, WebDriver driver) {
		super(appRoot, driver);
	}

	@Override
	public CardsPage navigateTo(String identity, String... opts) {
		CardsPage page = super.navigateTo(identity);
		try {
			//Wait for js to render page
	        Thread.sleep(500);
        } catch (InterruptedException e) {
	        throw new RuntimeException(e);
        }
		return page;
	}

	public CardsPage dragCardOntoCard(int toDragIndex, int toBeDraggedOntoIndex) {
		List<WebElement> elements = driver.findElements(By.name("smallGroup"));
		WebElement toDrag = elements.get(toDragIndex);
		WebElement toDragOnto = elements.get(toBeDraggedOntoIndex);
		
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(toDrag).moveToElement(toDragOnto).release(toDragOnto).build();
		dragAndDrop.perform();
		return this;
	}

	@Override
    public String getAction() {
		return "List";
    }

	@Override
    public String getController() {
		return "Cards";
    }

}
