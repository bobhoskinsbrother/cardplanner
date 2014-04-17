package uk.co.itstherules.junit.extension;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class WebMatcher {


    public static Matcher<String> onThePage(final WebDriver driver) {
        return new TypeSafeMatcher<String>() {
            @Override public boolean matchesSafely(String s) {
                return driver.findElement(By.tagName("body")).getText().contains(s);
            }

            @Override public void describeTo(Description description) {
                description.appendText("to be on the page");
            }
        };
    }

}
