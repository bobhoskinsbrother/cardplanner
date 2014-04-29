package uk.co.itstherules.junit.extension;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class WebDriverInstance {

    private static WebDriver DRIVER;
    private static boolean isSuite = false;

    public static void cleanBrowser() {
        if (DRIVER != null) {
            try {
                DRIVER.get("about:blank");
                DRIVER.manage().deleteAllCookies();
            } catch (Exception ignored) {
            }
        }
    }

    public static WebDriver get() {
        init();
        return DRIVER;
    }

    public static void asSuite() { isSuite = true; }

    private static void init() {
        if (DRIVER == null) {
//            FirefoxProfile profile = new FirefoxProfile();
//            profile.setEnableNativeEvents(true);
//            WebDriverInstance.DRIVER = new FirefoxDriver(profile);
            WebDriverInstance.DRIVER = new FirefoxDriver();
        }
    }

    public static void destroy() {
        if (!isSuite) {
            forceDestroy();
        }
    }

    public static void forceDestroy() {
        if (DRIVER != null) {
            DRIVER.close();
            DRIVER.quit();
            DRIVER = null;
        }
    }
}
