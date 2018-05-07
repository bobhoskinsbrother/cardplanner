package uk.co.itstherules.yawf.assertion;

import uk.co.itstherules.yawf.model.Entity;

import java.text.MessageFormat;
import java.util.Collection;


public final class Assertion {
	
	private Assertion() {}
	
	public static void checkNotNull(Object object) throws IllegalArgumentException { checkNotNull(object, "Value cannot be null"); }

	public static void checkNotNullObject(Entity<?> object) throws IllegalArgumentException { 
		checkNotInvisibleObject(object, "Object is Invisible");
	}
	
	public static void checkNotInvisibleObject(Entity<?> object, String message) throws IllegalArgumentException { 
		checkNotNull(object, "Value cannot be null"); 
		checkIsFalse(object.getInvisible(), message);
	}
	
	public static void checkNotNull(Object object, String message) throws IllegalArgumentException { if(object==null) { throw new IllegalArgumentException(message); } }

	public static void checkIsInstanceOf(Class<?> of, Object object) throws IllegalArgumentException {
		checkNotNull(of);
		checkNotNull(object);
		checkIsTrue(of.isInstance(object), MessageFormat.format("{0} is not an instance of {1}", object, of));
	}
	
	public static void checkIsTrue(Boolean value) throws IllegalArgumentException { 
		checkIsTrue(value, "Value is not true");
	}

	public static void checkIsTrue(Boolean value, String message) throws IllegalArgumentException { 
		checkIsSame(Boolean.TRUE, value, message);
	}

	public static void checkIsFalse(Boolean value) throws IllegalArgumentException { 
		checkIsFalse(value, "Value is not false");
	}

	public static void checkIsFalse(Boolean value, String message) throws IllegalArgumentException { 
		checkIsSame(Boolean.FALSE, value, message);
	}
	
	public static void checkIsSame(Object expected, Object actual, String message) throws IllegalArgumentException {
		checkNotNull(expected);
		checkNotNull(actual);
		if(!expected.equals(actual)) { throw new IllegalArgumentException(message); }
    }

	public static void checkIsSize(int size, Collection<?> collection) throws IllegalArgumentException {
		if(collection.size()!=size) {
			throw new IllegalArgumentException(MessageFormat.format("Collection can only have {0} cards", size));
		}
    }
	
	public static void checkNotEmpty(Collection<?> collection) throws IllegalArgumentException {
		checkIsFalse(collection.isEmpty(), "Collection should not be empty");
    }
	
	public static void checkIsInstance(Class<?> toCheck, Object instance) throws IllegalArgumentException {
		checkNotNull(toCheck);
		checkNotNull(instance);
		if(!toCheck.isInstance(instance)) {
			throw new IllegalArgumentException(MessageFormat.format("{0} is not an instance of {1}", instance, toCheck));
		}
    }
}