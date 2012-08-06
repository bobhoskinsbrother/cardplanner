package uk.co.itstherules.ui.functions;

import java.text.MessageFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BrowserWait {
	
	public static void tryToClick(WebDriver driver, By finderType, long millis) {
		Question<By> question = new Question<By>() { public boolean isAnswered(WebDriver driver, By by) throws Exception { driver.findElement(by).click(); return true;}};
		forQuestion(driver, question, finderType, millis);
	}
	
	public static void forElement(WebDriver driver, By finderType, long millis) {
		Question<By> question = new Question<By>() { public boolean isAnswered(WebDriver driver, By by) throws Exception { driver.findElement(by); return true;}};
		forQuestion(driver, question, finderType, millis);
	}

	public static void forFrame(WebDriver driver, String frameIdentity, final long millis) {
		Question<String> question = new Question<String>() { public boolean isAnswered(WebDriver driver, String name) throws Exception { driver.switchTo().frame(name); return true;}};
		forQuestion(driver, question, frameIdentity, millis);
	}

	public static void forTitle(WebDriver driver, String title, final long millis) {
		Question<String> question = new Question<String>() { public boolean isAnswered(WebDriver driver, String title) throws Exception { return title.equals(driver.getTitle()); }};
		forQuestion(driver, question, title, millis);
	}
	
	public static void forText(WebDriver driver, String text, long millis) {
		Question<String> question = new Question<String>() { 
			public boolean isAnswered(WebDriver driver, String string) throws Exception { 
				return driver.getPageSource().contains(string); 
			}};
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
					Thread.sleep(50);
					current = Calendar.getInstance().getTimeInMillis();
					System.out.println("Retrying browser at " + Calendar.getInstance().getTime());
				}
			} catch (Exception e) {
				try {
					Thread.sleep(50);
					current = Calendar.getInstance().getTimeInMillis();
					System.out.println("Retrying browser at "
					        + Calendar.getInstance().getTime());
				} catch (InterruptedException e1) {
					throw new RuntimeException(e);
				}
			}
		}
		throw new RuntimeException(MessageFormat.format(
		        "Timed out after {0} milliseconds", millis));
	}

	private interface Question<T> { boolean isAnswered(WebDriver driver, T object) throws Exception; }
}
