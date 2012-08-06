package uk.co.itstherules.yawf.model.atom;

import java.util.Date;
import java.util.Set;

public interface AtomModel {
	
	String getTitle();
	String getSubtitle(); 
	Date getUpdated();
	String getId();
	Set<Link> getLinks();
	String getRights();
	Generator getGenerator();
	Set<Entry> getEntries();
	
}
