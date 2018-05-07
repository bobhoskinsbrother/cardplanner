package uk.co.itstherules.cardplanner.model.type;

public enum TypeType {
	
	NumericBased("Numeric Based"), 
	TextBased("Text Based");
	
	private String asString;

	private TypeType(String asString) {
		this.asString = asString;
    }
	
	@Override
	public String toString() {
		return asString;
	}
	
}
