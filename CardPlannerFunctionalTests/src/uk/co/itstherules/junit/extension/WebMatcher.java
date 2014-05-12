package uk.co.itstherules.junit.extension;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class WebMatcher {


    public static Matcher<String> textOnThePage(final WebDriver driver) {
        return new TypeSafeMatcher<String>() {

            private String text;

            @Override public boolean matchesSafely(String s) {
                text = driver.findElement(By.tagName("body")).getText();
                return text.contains(s);
            }

            @Override public void describeTo(Description description) {
                description.appendText(text);
            }
        };
    }

    public static Matcher<String> textNotOnThePage(final WebDriver driver) {
        return new TypeSafeMatcher<String>() {

            private String text;

            @Override public boolean matchesSafely(String s) {
                text = driver.findElement(By.tagName("body")).getText();
                return !text.contains(s);
            }

            @Override public void describeTo(Description description) {
                description.appendText(text);
            }
        };
    }

}
