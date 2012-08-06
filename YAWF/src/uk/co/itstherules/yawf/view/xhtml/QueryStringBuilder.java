package uk.co.itstherules.yawf.view.xhtml;

import java.util.Collection;
import java.util.Iterator;

import uk.co.itstherules.string.manipulation.UrlEncoder;
import uk.co.itstherules.yawf.model.Entity;

public final class QueryStringBuilder {
	public String build(Collection<Entity<?>> hash) {
		StringBuffer buffer = new StringBuffer();
		if (hash.size() > 0) {
			buffer.append("?");
			
			Iterator<Entity<?>> iterator = hash.iterator();
			while (iterator.hasNext()){
				Entity<?> entity = iterator.next();
				buffer.append(entity.getIdentity());
				buffer.append("=");
				buffer.append(new UrlEncoder().manipulate(entity.getTitle()));
				if(iterator.hasNext()) {
					buffer.append("&amp;");
				}
			}
		}
		return buffer.toString();
	}
}
