package uk.co.itstherules.cardplanner.controller.shared;

public class SharedObject {


    public enum Action { ARCHIVE, CREATE, DELETE, UPDATE }

    private final Action action;
    private final String model;

	public SharedObject(SharedObject.Action action, String model) {
		this.action = action;
		this.model = model;
    }
	
	@Override public String toString() { 
		return new StringBuffer("{").append("\"action\":\"").append(action.name()).append("\",\"model\":").append(model).append("}").toString();
	}

}