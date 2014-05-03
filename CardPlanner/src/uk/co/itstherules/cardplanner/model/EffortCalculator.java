package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;

import java.util.Set;


public final class EffortCalculator {
	
	public EffortModel calculateEffort(Set<CardModel> cards, EffortTypeModel dayEffortType) {
		double effort = 0;
		for (CardModel card : cards) {
	        EffortModel effortModel = card.getEffort();
			double amount = effortModel.getAmount();
	        effort += effortModel.getType().asRate(amount);
        }
		EffortModel effortModel = new EffortModel();
		effortModel.setAmount(effort);
		effortModel.setType(dayEffortType);
		return effortModel;
    }
	
}