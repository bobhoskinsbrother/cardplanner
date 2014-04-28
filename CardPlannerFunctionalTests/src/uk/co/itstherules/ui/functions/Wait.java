package uk.co.itstherules.ui.functions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.MessageFormat;
import java.util.Calendar;

public class Wait {

    public static void tryToClick(WebDriver driver, By finderType, long millis) {
        Question<By> question = new Question<By>() {
            public boolean isAnswered(WebDriver driver, By by) throws Exception {
                driver.findElement(by).click();
                return true;
            }
        };
        forQuestion(driver, question, finderType, millis);
    }

    public static void untilVisible(WebDriver driver, By finderType, long millis) {
        Question<By> question = new Question<By>() {
            public boolean isAnswered(WebDriver driver, By by) throws Exception {
                return driver.findElement(by).isDisplayed();
            }
        };
        forQuestion(driver, question, finderType, millis);
    }

    public static void untilElementIsGone(WebDriver driver, By finderType, long millis) {
        Question<By> question = new Question<By>() {
            public boolean isAnswered(WebDriver driver, By by) throws Exception {
                try {
                    WebElement element = driver.findElement(by);
                    return !element.isDisplayed();
                } catch (NoSuchElementException e) {
                    return true;
                }
            }
        };
        forQuestion(driver, question, finderType, millis);
    }

    public static void forElement(WebDriver driver, By finderType, long millis) {
        Question<By> question = new Question<By>() {
            public boolean isAnswered(WebDriver driver, By by) throws Exception {
                driver.findElement(by);
                return true;
            }
        };
        forQuestion(driver, question, finderType, millis);
    }

    public static void forFrame(WebDriver driver, String frameIdentity, final long millis) {
        Question<String> question = new Question<String>() {
            public boolean isAnswered(WebDriver driver, String name) throws Exception {
                driver.switchTo().frame(name);
                return true;
            }
        };
        forQuestion(driver, question, frameIdentity, millis);
    }

    public static void forPageWithTitle(WebDriver driver, String title, final long millis) {
        Question<String> question = new Question<String>() {
            public boolean isAnswered(WebDriver driver, String title) throws Exception { return title.equals(driver.getTitle()); }
        };
        forQuestion(driver, question, title, millis);
    }

    public static void forText(WebDriver driver, String text, long millis) {
        Question<String> question = new Question<String>() {
            public boolean isAnswered(WebDriver driver, String string) throws Exception {
                return driver.getPageSource().contains(string);
            }
        };
        forQuestion(driver, question, text, millis);
    }

    public static <T> void forQuestion(WebDriver driver, Question<T> question, T object, long millis) {
        long current = Calendar.getInstance().getTimeInMillis();
        final long timeOut = current + millis;
        while (timeOut > current) {
            try {
                if (question.isAnswered(driver, object)) {
                    return;
                } else {
                    current = sleepABit();
                }
            } catch (Exception e) {
                try {
                    current = sleepABit();
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException(MessageFormat.format("Timed out after {0} milliseconds", millis));
    }

    private static long sleepABit() throws InterruptedException {
        Thread.sleep(50);
        System.out.println("Retrying browser at " + Calendar.getInstance().getTime());
        return Calendar.getInstance().getTimeInMillis();
    }

    private interface Question<T> {

        boolean isAnswered(WebDriver driver, T object) throws Exception;
    }
}
