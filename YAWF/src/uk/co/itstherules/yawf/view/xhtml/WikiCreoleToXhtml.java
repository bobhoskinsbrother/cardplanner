package uk.co.itstherules.yawf.view.xhtml;

import uk.co.itstherules.string.manipulation.StringManipulator;
import uk.co.itstherules.yawf.view.helper.TagBuilder;
import ys.wikiparser.WikiParser;

public final class WikiCreoleToXhtml implements StringManipulator {
	private final TagBuilder tagBuilder;

	public WikiCreoleToXhtml(TagBuilder tagBuilder) {
		this.tagBuilder = tagBuilder;
    }
	
	public String manipulate(String text) {
		return WikiParser.renderXHTML(text, this.tagBuilder);
	}
}
