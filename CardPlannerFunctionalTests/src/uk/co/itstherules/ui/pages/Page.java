package uk.co.itstherules.ui.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.functions.BrowserWait;

public abstract class Page<T> implements Navigable<T> { 
	
	protected final WebDriver driver;
	protected final String appRoot;

	public Page(WebDriver driver, String appRoot) {
		this.driver = driver;
		this.appRoot = appRoot;
		//verifyIsCorrectPage();
    }
	
	public String identifier() {
		return this.getController() + "." + this.getAction();
	}
	
    @SuppressWarnings("unchecked")
    @Override public T navigateTo(String identity, String... optional) {
    	StringBuffer optionalString = new StringBuffer();
    	String toAppend = "";
    	if(optional.length > 0) {
            optionalString.append("?");
    		for (String string : optional) {
	            optionalString.append(string);
	            optionalString.append("&");
            }
    		toAppend = optionalString.substring(0, optionalString.lastIndexOf("&"));
    	}
		driver.get(new StringBuilder().append(appRoot).append("/").append(getController()).append("/").append(getAction()).append("/").append(identity).append("/index.xhtml").append(toAppend).toString());
		return (T) this;
	}
	
	public abstract String getAction();

	public abstract String getController();
	
	public void verifyIsCorrectPage() throws IllegalStateException{
		if(driver.getPageSource().contains("YAWF - An error has occurred")) {
			throw new IllegalStateException();
		}
	}

	public boolean containsText(String text) {
		return containsText(text, 1000);
	}
	
	protected boolean displaysErrorFor(WebElement element) {
		String classAttribute = element.getAttribute("class");
		return classAttribute.contains("errorbackground") && classAttribute.contains("errorborder");
    }

	public boolean containsText(String text, long waitPeriod) {
		try {
			BrowserWait.forText(driver, text, waitPeriod);
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

	public boolean containsTextBeforeText(String textExpectedFirst, String textExpectedSecond) {
		return driver.getPageSource().indexOf(textExpectedFirst) < driver.getPageSource().indexOf(textExpectedSecond);
    }

	public void scrollTo(int pixels) {
		//more hacky crap to scrollTo on the page
	    ((JavascriptExecutor)driver).executeScript("scrollTo(0, "+String.valueOf(pixels)+");");
    }

}