package uk.co.itstherules.ui.personas;

import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.change.ChangeCardPage;


public class Scrappy2 extends BasicPersona {

	public Scrappy2() {
	    super("scrappy2");
    }
	
	@Override
	public Page<?> add(ChangeCardPage cardPage) {
		String title = personaData.getCardTitle();
		String body = personaData.getCardBody();
		String effortAmount = personaData.getEffortAmount();
		String valueAmount = personaData.getValueAmount();
		String[] tags = personaData.getTags();
		if (!"".equals(title)) {
			cardPage = cardPage.setTitle(title);
		}
		if (!"".equals(body)) {
			cardPage = cardPage.setBody(body);
		}
		if (!"".equals(effortAmount)) {
			cardPage = cardPage.setEffortAmount(effortAmount);
		}
		if (!"".equals(valueAmount)) {
			cardPage = cardPage.setValueAmount(valueAmount);
		}
		if (tags.length>0) {
			for (String tag : tags) {
				cardPage = cardPage.addTag(tag);
			}
		}
		return cardPage.clickComplete();
	}
}
