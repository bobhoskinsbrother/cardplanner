package uk.co.itstherules.junit.extension;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;


public class WebDriverInstance {

    public static WebDriver make() {
        System.setProperty("webdriver.chrome.driver", "binary/chromedriver");
        ChromeDriver driver = new ChromeDriver(defaultChromeOptions());
        return precookedDriver(driver);
    }

    private static ChromeOptions defaultChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--test-type");
        options.addArguments("--js-flags=--expose-gc");
        options.addArguments("--enable-precise-memory-info");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-infobars");
        return options;
    }

    public static ChromeOptions kioskModeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--test-type");
        options.addArguments("--start-maximized");  // this is where the popover full screen magic happens on windows
        options.addArguments("--kiosk");            // this is where the popover full screen magic happens on mac / linux
        options.addArguments("--js-flags=--expose-gc");
        options.addArguments("--enable-precise-memory-info");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-infobars");
        return options;
    }


    private static WebDriver precookedDriver(ChromeDriver driver) {
        setAsFullSizeForMacWithChrome(driver);
        focusOnBrowserWindow(driver);
        return driver;
    }

    private static void focusOnBrowserWindow(WebDriver driver) {
        JavascriptExecutor.class.cast(driver).executeScript("alert(\"Start Recording Window\")");
        driver.switchTo().alert().accept();
    }

    private static void setAsFullSizeForMacWithChrome(WebDriver driver) {
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        driver.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
        driver.manage().window().setSize(new org.openqa.selenium.Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight()));
    }

    public static void destroy(WebDriver driver) {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

}
