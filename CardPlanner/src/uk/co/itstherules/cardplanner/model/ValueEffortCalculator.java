package uk.co.itstherules.cardplanner.model;

import java.util.Set;

import uk.co.itstherules.cardplanner.model.finder.CardCollectionManipulator;
import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;


public final class ValueEffortCalculator {
	
	public void rollUpValueEffort(Set<CardModel> cards) {
		CardCollectionManipulator finder = new CardCollectionManipulator(cards);
		for (CardModel cardModel : cards) {
            double rolledUpEffort = finder.rolledUpEffort(cardModel) / cardModel.getEffort().getType().getRate();
            double rolledUpValue = finder.rolledUpValue(cardModel) / cardModel.getValue().getType().getRate();

            cardModel.getEffort().setAmount(rolledUpEffort);
			cardModel.getValue().setAmount(rolledUpValue);
        }
	}
	
	public ValueModel calculateValue(Set<CardModel> cards, ValueTypeModel baseValueType) {
		double value = 0;
		for (CardModel card : cards) {
	        ValueModel valueModel = card.getValue();
			double amount = valueModel.getAmount();
	        value += valueModel.getType().asRate(amount);
        }
		value = baseValueType.asRate(value);
		ValueModel valueModel = new ValueModel();
		valueModel.setAmount(value);
		valueModel.setType(baseValueType);
		return valueModel;
	}

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