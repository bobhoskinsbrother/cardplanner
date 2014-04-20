package uk.co.itstherules.yawf.model.atom;

import java.util.Date;
import java.util.Set;

public interface Entry {
	
	String getTitle();
	Set<Link> getLinks();
	String getId();
	Date getUpdated();
	Date getPublished();
	EntryAuthor getAuthor();
	Set<EntryContributer> getContributers();
	EntryContent getContent();
	
}
