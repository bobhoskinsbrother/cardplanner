package uk.co.itstherules.cardplanner.model.filter;

import java.util.ArrayList;
import java.util.List;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.LogModel;
import uk.co.itstherules.cardplanner.model.StatusModel;

public final class LogFilter {

	public static List<CardModel> filterLogs(List<LogModel> logs) {
		List<CardModel> cards = new ArrayList<CardModel>();
		for (LogModel log : logs) {
			CardModel card = log.getCard();
			if(!cards.contains(card)) cards.add(card);
		}
		return cards;
    }

	public static List<CardModel> filterLogs(List<LogModel> logs, StatusModel backlog) {
		List<CardModel> cards = new ArrayList<CardModel>();
		for (LogModel log : logs) {
			if (log.getFromStatus().equals(backlog)) {
				CardModel card = log.getCard();
				if(!card.getDeleted().booleanValue() && !cards.contains(card)) cards.add(card);
			}
		}
		return cards;
		
    }
}
