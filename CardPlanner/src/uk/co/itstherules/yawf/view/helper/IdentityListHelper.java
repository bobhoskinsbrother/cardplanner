package uk.co.itstherules.yawf.view.helper;

import java.util.List;

import uk.co.itstherules.yawf.model.IdentityDeleteable;

public final class IdentityListHelper implements ListHelper<IdentityDeleteable<?>>  {
	
	public String flatten(List<IdentityDeleteable<?>> cards) {
		StringBuffer buffer = new StringBuffer();
		for (IdentityDeleteable<?> card : cards) {
			buffer.append(",\"");
	        buffer.append(card.getIdentity());
			buffer.append("\"");
        }
		if(buffer.length() > 0) return buffer.substring(1);
		return "";
    }
}
