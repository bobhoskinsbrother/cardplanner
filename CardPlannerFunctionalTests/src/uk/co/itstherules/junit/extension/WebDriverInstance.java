package uk.co.itstherules.junit.extension;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WebDriverInstance {

	private static WebDriver DRIVER;
	private static boolean isSuite = false;

	public static void cleanBrowser() {
		if(DRIVER != null) {
			try {
				DRIVER.get("about:blank");
				DRIVER.manage().deleteAllCookies();
			} catch (Exception e) {
			}
		}
    }

	
	public static WebDriver get() { init(); return DRIVER; }
	public static void asSuite() { isSuite = true; }
	private static void init() { 
		if(DRIVER==null) { 
			WebDriverInstance.DRIVER = new HtmlUnitDriver(true);
		} 
	}
	public static void destroy() { if(!isSuite) { forceDestroy(); } }
	public static void forceDestroy() {
		if(DRIVER != null) {
			DRIVER.close();
			DRIVER.quit();
			DRIVER = null;
		}
    }
}
