package uk.co.itstherules.cardplanner.model.filter;

public interface CardFilterable <T> {
	
	public boolean accept(T t);
	
}
