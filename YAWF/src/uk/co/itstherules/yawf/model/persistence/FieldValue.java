package uk.co.itstherules.yawf.model.persistence;

public final class FieldValue {
	
	private final String field;
	private final Object value;

	public FieldValue(final String field, final Object value) {
		this.field = field;
		this.value = value;
    }
	
	public String getField() { return field; }
	public Object getValue() { return value; }

}