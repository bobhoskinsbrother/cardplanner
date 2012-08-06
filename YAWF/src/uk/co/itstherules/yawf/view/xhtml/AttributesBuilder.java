package uk.co.itstherules.yawf.view.xhtml;

import java.util.Collection;
import java.util.Iterator;

import uk.co.itstherules.yawf.model.Entity;

public final class AttributesBuilder {
	public String build(Collection<? extends Entity<?>> hash) {
		return this.build(hash, false);
	}

	public String build(Collection<? extends Entity<?>> hash, boolean isViolated) {
		StringBuffer buffer = new StringBuffer();
		Iterator<? extends Entity<?>> iterator = hash.iterator();
		boolean containsClass = false;
		while (iterator.hasNext()) {
			Entity<?> entity = iterator.next();
			buffer.append(entity.getIdentity());
			buffer.append("=\"");
			buffer.append(entity.getTitle());
			if ("class".equalsIgnoreCase(entity.getIdentity()) && isViolated) {
				containsClass = true;
				buffer.append(" errorbackground errorborder");
			}
			buffer.append("\" ");
		}
		if (!containsClass && isViolated) {
			buffer.append(" class=\"errorbackground errorborder\" ");
		}
		return buffer.toString();
	}
}
