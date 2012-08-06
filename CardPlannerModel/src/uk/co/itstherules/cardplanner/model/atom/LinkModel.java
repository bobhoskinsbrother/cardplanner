package uk.co.itstherules.cardplanner.model.atom;

import uk.co.itstherules.yawf.model.atom.Link;

public final class LinkModel implements Link {
	
	private String title;
	private String href;
	private LinkRelValue rel;
	
	public LinkModel(String title, String href, LinkRelValue rel) {
		this.title = title;
		this.href = href;
		this.rel = rel;
    }
	

	public LinkRelValue getRel() {
		return rel;
	}

	public String getType() {
		return "text/html";
	}

	public String getHreflang() {
		return "en";
	}

	public String getHref() {
		return href;
	}

	public String getTitle() {
		return title;
	}

	public int getLength() {
		return 0;
	}
}
