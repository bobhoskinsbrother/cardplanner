package uk.co.itstherules.yawf.view.xhtml;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.yawf.view.helper.XHtmlTagBuilder;

public class WikiCreoleToXhtmlTest {
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	@Test public void willWrapToMakeXhtmlSafe() {
		WikiCreoleToXhtml unit = new WikiCreoleToXhtml(new XHtmlTagBuilder("/Hairy"));
		String string = "passthrough string";
		String reply = unit.manipulate(string);
		Assert.assertEquals("<p>passthrough string</p>"+LINE_SEPARATOR, reply);
	}

	@Test public void canConvertLinks() {
		WikiCreoleToXhtml unit = new WikiCreoleToXhtml(new XHtmlTagBuilder("/Hairy"));
		String reply = unit.manipulate("not passthrough http://badgers.on.a.rampage.com ");
		Assert.assertEquals("<p>not passthrough <a href=\"http://badgers.on.a.rampage.com\" target=\"_blank\" rel=\"nofollow\">http://badgers.on.a.rampage.com</a></p>"+LINE_SEPARATOR, reply);
	}
	
	@Test public void canPlaceLocalLinks() {
		WikiCreoleToXhtml unit = new WikiCreoleToXhtml(new XHtmlTagBuilder("/Hairy"));
		String reply = unit.manipulate("not passthrough [[BadgersRampage]] fred");
		Assert.assertEquals("<p>not passthrough <a href=\"/Hairy/Pages/Show/0/BadgersRampage\" target=\"_top\" >BadgersRampage</a> fred</p>"+LINE_SEPARATOR, reply);
		
	}

	@Test public void wontConvertBreaks() {
		WikiCreoleToXhtml unit = new WikiCreoleToXhtml(new XHtmlTagBuilder("/Hairy"));
		String reply = unit.manipulate("not passthroug"+LINE_SEPARATOR+"a.rampage.com ");
		Assert.assertEquals(
				"<p>not passthroug" + LINE_SEPARATOR +
				"a.rampage.com</p>" + LINE_SEPARATOR, reply);
	}

	@Test public void willForceBreak() {
		WikiCreoleToXhtml unit = new WikiCreoleToXhtml(new XHtmlTagBuilder("/Hairy"));
		String reply = unit.manipulate("not passthroug\\\\a.rampage.com ");
		Assert.assertEquals("<p>not passthroug<br/>a.rampage.com</p>"+LINE_SEPARATOR, reply);
	}

	@Test public void willConvertDoubleBreaks() {
		WikiCreoleToXhtml unit = new WikiCreoleToXhtml(new XHtmlTagBuilder("/Hairy"));
		String reply = unit.manipulate("not passthroug"+LINE_SEPARATOR+LINE_SEPARATOR+"a.rampage.com ");
		Assert.assertEquals("<p>not passthroug</p>"+LINE_SEPARATOR+"<p>a.rampage.com</p>"+LINE_SEPARATOR, reply);
	}
	
	@Test public void willBold() {
		WikiCreoleToXhtml unit = new WikiCreoleToXhtml(new XHtmlTagBuilder("/Hairy"));
		String reply = unit.manipulate("not **passthroug**");
		Assert.assertEquals("<p>not <strong>passthroug</strong></p>"+LINE_SEPARATOR, reply);
	}
	
	@Test public void willItalic() {
		WikiCreoleToXhtml unit = new WikiCreoleToXhtml(new XHtmlTagBuilder("/Hairy"));
		String reply = unit.manipulate("not //passthroug//");
		Assert.assertEquals("<p>not <em>passthroug</em></p>"+LINE_SEPARATOR, reply);
	}
	
	@Test public void whatAboutEscapingTagsHuh() {
		WikiCreoleToXhtml unit = new WikiCreoleToXhtml(new XHtmlTagBuilder("/Hairy"));
		String reply = unit.manipulate("not <br/> //passthroug//");
		Assert.assertEquals("<p>not &lt;br/&gt; <em>passthroug</em></p>"+LINE_SEPARATOR, reply);
	}
	
	
}
