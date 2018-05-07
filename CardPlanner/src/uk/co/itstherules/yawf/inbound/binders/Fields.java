package uk.co.itstherules.yawf.inbound.binders;


import java.lang.reflect.Field;

public class Fields {
	
	private Fields() { }
	
	public static void set(Object model, Field field, Object value) {
		try {
			field.set(model, value);
        } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
	        throw new IllegalArgumentException(e);
        }

    }
	
}
