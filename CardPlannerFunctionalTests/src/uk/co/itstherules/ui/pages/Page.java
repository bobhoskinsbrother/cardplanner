package uk.co.itstherules.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.functions.Wait;

public abstract class Page<T> implements Navigable<T> { 
	
	protected final WebDriver driver;
	protected final String appRoot;

	public Page(WebDriver driver, String appRoot) {
		this.driver = driver;
		this.appRoot = appRoot;
    }
	
	public String identifier() {
		return this.getController() + "." + this.getAction();
	}
	
    @SuppressWarnings("unchecked")
    @Override public T navigateTo(String identity, String... optional) {
    	StringBuilder optionalString = new StringBuilder();
    	String toAppend = "";
    	if(optional.length > 0) {
            optionalString.append("?");
    		for (String string : optional) {
	            optionalString.append(string);
	            optionalString.append("&");
            }
    		toAppend = optionalString.substring(0, optionalString.lastIndexOf("&"));
    	}
        final String path = appRoot + "/" + getController() + "/" + getAction() + "/" + identity + "/index.xhtml" + toAppend;
        driver.get(path);
		return (T) this;
	}
	
	public abstract String getAction();

	public abstract String getController();

	public boolean containsText(String text) {
		return containsText(text, 1000);
	}

	public boolean containsText(String text, long waitPeriod) {
		try {
			Wait.forText(driver, text, waitPeriod);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void focus() {
		this.driver.switchTo().defaultContent();
	}
    
	protected void clearAndPopulate(WebElement element, String text) {
    	element.clear();
    	element.sendKeys(text);
    }
	
	public void confirmAs(boolean confirm) {
		//hacky crap to disable the confirm box for that page
	    ((JavascriptExecutor)driver).executeScript("window.confirm = function(msg){ return "+String.valueOf(confirm)+"; };");
	}

	public void scrollTo(int pixels) {
		//more hacky crap to scrollTo on the page
	    ((JavascriptExecutor)driver).executeScript("scrollTo(0, "+String.valueOf(pixels)+");");
    }

    public void clickLinksTab() {
        final WebElement sideTab = driver.findElement(By.id("sideBarTab"));
        sideTab.click();
    }


}