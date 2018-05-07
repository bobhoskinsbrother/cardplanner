package uk.co.itstherules.yawf.model.instantiator;

public class Instantiator {
	
	public <T> T instantiate(Class<T> classToInstantiate) {
		try {
	        return classToInstantiate.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
	        throw new RuntimeException(e);
        }
    }
	
	@SuppressWarnings("unchecked") public <T> T instantiate(String classToInstantiate) {
		try {
	        return (T) Class.forName(classToInstantiate).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
	        throw new RuntimeException(e);
        }
    }
	
}
