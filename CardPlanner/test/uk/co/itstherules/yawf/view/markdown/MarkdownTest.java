package uk.co.itstherules.yawf.view.markdown;

import org.junit.Test;
import uk.co.itstherules.yawf.view.Markdown;

import static org.junit.Assert.assertEquals;

public class MarkdownTest {
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	@Test public void willWrapToMakeXhtmlSafe() {
		Markdown unit = new Markdown();
		String string = "passthrough string";
		String reply = unit.manipulate(string);
		assertEquals("<p>passthrough string</p>" + LINE_SEPARATOR, reply);
	}

	@Test public void canConvertLinks() {
		Markdown unit = new Markdown();
		String reply = unit.manipulate("not passthrough [http://badgers.on.a.rampage.com](http://badgers.on.a.rampage.com) ");
		assertEquals("<p>not passthrough <a href=\"http://badgers.on.a.rampage.com\">http://badgers.on.a.rampage.com</a></p>" + LINE_SEPARATOR, reply);
	}
	
	@Test public void canParseInvalidUri() {
		Markdown unit = new Markdown();
		String reply = unit.manipulate("not passthrough [BadgersRampage](Waaaa) fred");
		assertEquals("<p>not passthrough <a href=\"Waaaa\">BadgersRampage</a> fred</p>\n", reply);
	}

	@Test public void willConvertBreaks() {
		Markdown unit = new Markdown();
		String reply = unit.manipulate("not passthroug"+LINE_SEPARATOR+"a.rampage.com ");
		assertEquals("<p>not passthroug"+LINE_SEPARATOR+"" + "<br  />a.rampage.com</p>"+LINE_SEPARATOR, reply
        );
	}

	@Test public void willForceBreak() {
		Markdown unit = new Markdown();
		String reply = unit.manipulate("not passthroug\\\\a.rampage.com ");
		assertEquals("<p>not passthroug\\a.rampage.com</p>" + LINE_SEPARATOR, reply);
	}

	@Test public void willConvertDoubleBreaks() {
		Markdown unit = new Markdown();
		String reply = unit.manipulate("not passthroug"+LINE_SEPARATOR+LINE_SEPARATOR+"a.rampage.com ");
		assertEquals("<p>not passthroug</p>" + LINE_SEPARATOR + "<p>a.rampage.com</p>" + LINE_SEPARATOR, reply);
	}
	
	@Test public void willBold() {
		Markdown unit = new Markdown();
		String reply = unit.manipulate("not **passthroug**");
		assertEquals("<p>not <strong>passthroug</strong></p>" + LINE_SEPARATOR, reply);
		reply = unit.manipulate("not __passthroug__");
		assertEquals("<p>not <strong>passthroug</strong></p>" + LINE_SEPARATOR, reply);
	}
	
	@Test public void willItalic() {
		Markdown unit = new Markdown();
		String reply = unit.manipulate("not _passthrough_");
		assertEquals("<p>not <em>passthrough</em></p>" + LINE_SEPARATOR, reply);
		reply = unit.manipulate("not *passthrough*");
		assertEquals("<p>not <em>passthrough</em></p>" + LINE_SEPARATOR, reply);
	}
	
	@Test public void whatAboutEscapingTagsHuh() {
		Markdown unit = new Markdown();
		String reply = unit.manipulate("not <br/> //passthroug//");
		assertEquals("<p>not <br /> //passthroug//</p>" + LINE_SEPARATOR, reply);
	}
	
	
}
