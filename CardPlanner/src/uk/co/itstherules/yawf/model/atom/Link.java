package uk.co.itstherules.yawf.model.atom;

public interface Link {
	
	public enum LinkRelValue { 
		ALTERNATE, RELATED, SELF, ENCLOSURE, VIA;
		public String toString() { return name().toLowerCase(); }
	}
	
	LinkRelValue getRel();
	String getType();
	String getHreflang();
	String getHref();

	String getTitle();
    int getLength();

	
}
