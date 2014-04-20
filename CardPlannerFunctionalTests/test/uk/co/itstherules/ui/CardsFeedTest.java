package uk.co.itstherules.ui;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.itstherules.ui.pages.feed.CardsFeed;

import java.io.StringReader;

@Ignore
public class CardsFeedTest {

    private static final String file = "simple";

    @Test
    public void canConsumeAFeed() throws Exception {
        String separator = System.getProperty("line.separator");
        String xml = new CardsFeed("http://localhost:9999/Simple").atomFeedFor("0");
        XMLUnit.setIgnoreWhitespace(true);
        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + separator +
                "<feed xmlns=\"http://www.w3.org/2005/Atom\">" + separator +
                "<title type=\"html\">List Cards</title>" + separator +
                "<subtitle type=\"html\"></subtitle> " + separator +
                "<updated>2010-10-19T16:09:30Z</updated>" + separator +
                "<id>urn:uuid:</id>" + separator +
                "<rights></rights>" + separator +
                "<generator uri=\"/Simple\" version=\"1.0\">CardPlanner Feed Generator</generator>" + separator +
                "<entry>" + separator +
                "	<title>I am an card that requires editing</title>" + separator +
                "	<link rel=\"alternate\" type=\"text/html\" hreflang=\"en\" href=\"/Simple/Cards/Show/658c9802-f227-458d-958a-ceba3b88ddd0/I+am+an+card+that+requires+editing\" title=\"Show I am an card that requires editing\"  />" + separator +
                "	<id>urn:uuid:658c9802-f227-458d-958a-ceba3b88ddd0</id>" + separator +
                "	<updated>2010-10-19T16:09:30Z</updated>" + separator +
                "	<published>2010-10-12T00:00:00Z</published>" + separator +
                "	<author>" + separator +
                "		<name></name>" + separator +
                "		<uri></uri>" + separator +
                "		<email></email>" + separator +
                "	</author>" + separator +
                "	<content type=\"xhtml\" xml:lang=\"en\" xml:base=\"/Simple\">" + separator +
                "		<div xmlns=\"http://www.w3.org/1999/xhtml\">" + separator +
                "			as I may have a few tpyos" + separator +
                "		</div>" + separator +
                "	</content>" + separator +
                "</entry>" + separator +
                "</feed>";
        System.out.println(xml);
        Diff diff = XMLUnit.compareXML(new StringReader(expected), new StringReader(xml));
        Assert.assertTrue(diff.toString(), diff.identical());
    }


    @Test
    public void canConsumeAFeedWithTwo() throws Exception {
        String separator = System.getProperty("line.separator");

        String xml = new CardsFeed("http://localhost:9999/Simple").atomFeedFor("0");

        XMLUnit.setIgnoreWhitespace(true);
        String expected = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + separator +
                "<feed xmlns=\"http://www.w3.org/2005/Atom\">" + separator +
                "<title type=\"html\">List Cards</title>" + separator +
                "<subtitle type=\"html\"></subtitle> " + separator +
                "<updated>2010-10-15T09:48:53Z</updated>" + separator +
                "<id>urn:uuid:</id>" + separator +
                "<rights></rights>" + separator +
                "<generator uri=\"/Simple\" version=\"1.0\">CardPlanner Feed Generator</generator>" + separator +
                "<entry>" + separator +
                "	<title>I am an card that requires editing</title>" + separator +
                "	<link rel=\"alternate\" type=\"text/html\" hreflang=\"en\" href=\"/Simple/Cards/Show/7d4915ab-339a-4754-8f65-59e017759c69/I+am+an+card+that+requires+editing\" title=\"Show I am an card that requires editing\"  />" + separator +
                "	<id>urn:uuid:7d4915ab-339a-4754-8f65-59e017759c69</id>" + separator +
                "	<updated>2010-10-15T09:48:53Z</updated>" + separator +
                "	<published>2010-07-14T00:00:00Z</published>" + separator +
                "	<author>" + separator +
                "		<name></name>" + separator +
                "		<uri></uri>" + separator +
                "		<email></email>" + separator +
                "	</author>" + separator +
                "	<content type=\"xhtml\" xml:lang=\"en\" xml:base=\"/Simple\">" + separator +
                "		<div xmlns=\"http://www.w3.org/1999/xhtml\">" + separator +
                "			as I may have a few tpyos" + separator +
                "		</div>" + separator +
                "	</content>" + separator +
                "</entry>" + separator +
                "<entry>" + separator +
                "	<title>I am an card that you can drop onto</title>" + separator +
                "	<link rel=\"alternate\" type=\"text/html\" hreflang=\"en\" href=\"/Simple/Cards/Show/0506dfcb-fbe0-43a5-90f5-b19387c555ea/I+am+an+card+that+you+can+drop+onto\" title=\"Show I am an card that you can drop onto\"  />" + separator +
                "	<id>urn:uuid:0506dfcb-fbe0-43a5-90f5-b19387c555ea</id>" + separator +
                "	<updated>2010-10-15T09:48:53Z</updated>" + separator +
                "	<published>2010-07-14T00:00:00Z</published>" + separator +
                "	<author>" + separator +
                "		<name></name>" + separator +
                "		<uri></uri>" + separator +
                "		<email></email>" + separator +
                "	</author>" + separator +
                "	<content type=\"xhtml\" xml:lang=\"en\" xml:base=\"/Simple\">" + separator +
                "		<div xmlns=\"http://www.w3.org/1999/xhtml\">" + separator +
                "			Please drop onto me, thank you" + separator +
                "		</div>" + separator +
                "	</content>" + separator +
                "</entry>" + separator +
                "</feed>";
        System.out.println(xml);
        Diff diff = XMLUnit.compareXML(new StringReader(expected), new StringReader(xml));
        Assert.assertTrue(diff.toString(), diff.identical());
    }

}
