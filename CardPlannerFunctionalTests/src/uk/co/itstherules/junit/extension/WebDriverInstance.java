package uk.co.itstherules.junit.extension;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class WebDriverInstance {

    public static WebDriver make(boolean useNative) {
        WebDriver driver;
        if (useNative) {
            FirefoxProfile profile = new FirefoxProfile();
            profile.setEnableNativeEvents(true);
            driver = new FirefoxDriver(profile);
        } else {
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriver make() {
        return make(false);
    }

    public static void destroy(WebDriver driver) {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

}
