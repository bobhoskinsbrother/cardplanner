package uk.co.itstherules.cardplanner.controller.shared;

import java.util.Collection;

public interface SharedObjectSpaceClient {

    public Collection<SharedObject> getSharedObjects(Class<?> type);
    public void shareObject(Class<?> type, SharedObject object);
	String getIdentity();
    
}