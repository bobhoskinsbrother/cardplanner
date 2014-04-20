package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.lang.reflect.Field;

public class ObjectAndField {
	
	private final Object model;
	private final Field field;
	private final String fullQueryKey;

	public ObjectAndField(Object model, Field field, String fullQueryKey) {
		this.model = model;
		this.field = field;
		this.fullQueryKey = fullQueryKey;
	}
	public Field getField() {return field;}
	public Object getModel() {return model;}
	public String getFullQueryKey() { return fullQueryKey; }
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof ObjectAndField)) return false;
		
		ObjectAndField otherObjectAndField = (ObjectAndField) other;
		
		return 
		this.field.getName().equals(otherObjectAndField.field.getName()) &&
		this.model.equals(otherObjectAndField.model);
	}
}
