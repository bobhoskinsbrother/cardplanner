package uk.co.itstherules.cardplanner.model.atom;

import uk.co.itstherules.yawf.model.atom.EntryContent;

public final class EntryContentModel implements EntryContent {
	private final String xmlBase;
	private final String value;

	public EntryContentModel(String xmlBase, String value) {
		this.xmlBase = xmlBase;
		this.value = value;
    }

	public String getXmlBase() { return xmlBase; }
	public String getValue() { return value; }

}