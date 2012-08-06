package uk.co.itstherules.ui;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.co.itstherules.junit.extension.WebDriverInstance;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        /*Reflects the Links panel*/
        CardPlannerTest.class,
        CardsTest.class,
        CardsFeedTest.class,
        CardAttachmentsTest.class,
        CanSignUpForWorkTest.class,
        MyCardsTest.class,
        /**/
        TimeSpansTest.class,
        StatusesTest.class,
        PostItTest.class,
        PeopleTest.class,
        TagsTest.class,
        /**/
        PagesTest.class,
        AdminTest.class,
        LogsTest.class,
        /**/
        EffortTypesTest.class,
        CardTypesTest.class,
        ValueTypesTest.class,
        /**/
        HardSumsTest.class,
        ResurrectDeadThingsTest.class,
        ExcelExportTest.class,
        /**/
        LoginTest.class
})
public class UITestSuite {
    @BeforeClass
    public static void setAsSuite() {
        WebDriverInstance.asSuite();
    }

    @AfterClass
    public static void destroyWebDriver() {
        WebDriverInstance.forceDestroy();
    }

    @After
    public static void reap() {
        System.gc();
    }
}
