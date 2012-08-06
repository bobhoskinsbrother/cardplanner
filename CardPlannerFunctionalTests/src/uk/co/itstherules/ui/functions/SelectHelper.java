package uk.co.itstherules.ui.functions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SelectHelper {
	
	public static void byNodeText(WebElement select, String text) {
		if(!select.getTagName().toLowerCase().equals("select")) {
			throw new IllegalArgumentException("WebElement is not a select box");
		}
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if(text.equals(option.getText())){
	        	option.click();
	        	return;
	        }
        }
		throw new IllegalStateException(text+" not found as in select box");
	}
}
