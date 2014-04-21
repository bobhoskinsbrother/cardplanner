package uk.co.itstherules.ui.pages.list;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import uk.co.itstherules.ui.functions.Wait;

public class CardPlannerPage extends CardManipulationPage<CardPlannerPage> {


	public CardPlannerPage(String appRoot, WebDriver driver) {
		super(appRoot, driver);
    }

	public CardPlannerPage dragOnto(WebElement toDrag, WebElement toDragOnto) {
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(toDrag).moveToElement(toDragOnto).release(toDragOnto).build();
		dragAndDrop.perform();
		try {
			//TODO: a less horrible way to handle AJAX events
	        Thread.sleep(750);
        } catch (InterruptedException e) {
	        throw new RuntimeException(e);
        }
		return this;
	}

    
    public CardPlannerPage dragOnto(String columnIdentity, String cardIdentity) {
		WebElement column = driver.findElement(By.id(columnIdentity));
		WebElement card = driver.findElement(By.id(cardIdentity));
		WebElement toDrag = card;
		WebElement toDragOnto = column;
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(toDrag).moveToElement(toDragOnto).release(toDragOnto).build();
		dragAndDrop.perform();
		try {
			//TODO: a less horrible way to handle AJAX events
	        Thread.sleep(750);
        } catch (InterruptedException e) {
	        throw new RuntimeException(e);
        }
		return this;
	}

	public boolean columnContainsCard(String columnIdentity, String cardIdentity) {
		WebElement column = driver.findElement(By.id(columnIdentity));
		try {
			column.findElement(By.id(cardIdentity));
			return true;
		} catch (Exception e) {
			return false;
		}
    }

	public boolean swimLaneExists(String column) {
		try {
			driver.findElement(By.id(column));
			return true;
		} catch (Exception e) {
			return false;
		}
    }

	public CardPlannerPage selectSwimLaneAmount(int swimLaneAmount) {
		String stringLaneAmount = String.valueOf(swimLaneAmount);
		WebElement swimLaneAmountSelect = driver.findElement(By.id("selectSwimLaneAmount"));
		List<WebElement> options = swimLaneAmountSelect.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if(stringLaneAmount.equals(option.getText())){
	        	option.click();
	        	break;
	        }
        }
		Wait.forText(driver, "<!--SwimLaneAmount=" + swimLaneAmount + "-->", 5000);
		return this;
    }

	@Override
    public String getAction() {
	    return "List";
    }

	@Override
    public String getController() {
	    return "CardPlanner";
    }

}
