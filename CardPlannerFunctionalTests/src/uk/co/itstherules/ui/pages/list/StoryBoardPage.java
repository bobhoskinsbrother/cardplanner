package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import uk.co.itstherules.ui.functions.Wait;

import java.util.List;

public class StoryBoardPage extends CardManipulationPage<StoryBoardPage> {


	public StoryBoardPage(String appRoot, WebDriver driver) {
		super(appRoot, driver);
    }

	public StoryBoardPage dragOnto(WebElement toDrag, WebElement toDragOnto) {
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

    
    public StoryBoardPage dragOnto(String toDragId, String toDragOntoId) {
        WebElement toDrag = driver.findElement(By.id(toDragId));
		WebElement toDragOnto = driver.findElement(By.id(toDragOntoId));
        return dragOnto(toDrag, toDragOnto);
	}

	public StoryBoardPage selectSwimLaneAmount(int swimLaneAmount) {
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
	    return "Show";
    }

	@Override
    public String getController() {
	    return "StoryBoard";
    }

    public StoryBoardPage openBacklog() {
        driver.findElement(By.id("backlog_tab")).click();
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
