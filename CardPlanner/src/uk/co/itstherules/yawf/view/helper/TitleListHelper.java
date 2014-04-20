package uk.co.itstherules.yawf.view.helper;

import java.util.List;

import uk.co.itstherules.yawf.model.Title;

public final class TitleListHelper implements ListHelper<Title> {
	
	public String flatten(List<Title> cards) {
		if(cards!=null) {
		StringBuffer buffer = new StringBuffer();
		for (Title card : cards) {
			buffer.append(",");
			buffer.append("'");
	        buffer.append(card.getTitle());
			buffer.append("'");
        }
		if(buffer.length()>0) return buffer.substring(1);
		}
		return "";
    }
}
