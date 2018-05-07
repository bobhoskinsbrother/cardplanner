package uk.co.itstherules.cardplanner.controller.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SharedObjectSpacesListener implements ServletContextListener {

	private static ClientsAndSpaces spaces;

	@Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
    }

	public static SharedObjectSpaceClient reAttachClient(String identity) {
		return spaces.reAttachClient(identity);
	}
	
	@Override
    public void contextInitialized(ServletContextEvent contextEvent) {
		spaces = new ClientsAndSpaces();
    }

    private final class InternalClient implements SharedObjectSpaceClient {

        private String identity;
        private ClientsAndSpaces spaces;

        private InternalClient(String identity, ClientsAndSpaces spaces) {
            this.identity = identity;
            this.spaces = spaces;
        }

        @Override
        public Collection<SharedObject> getSharedObjects(Class<?> type) {
            return spaces.getSharedObjects(identity, type);
        }

        @Override
        public void shareObject(Class<?> type, SharedObject object) {
            spaces.shareObject(identity, type, object);
        }

        @Override
        public String getIdentity() { return identity; }
    }

    private final class ClientsAndSpaces {

        private final Map<String, Spaces> clientsAndSpaces = new ConcurrentHashMap<String, Spaces>();

        public SharedObjectSpaceClient reAttachClient(String identity) {
            clearOld(clientsAndSpaces);
            getSpaces(identity);
            return new InternalClient(identity, this);
        }

        private void clearOld(Map<String, Spaces> clientsAndSpaces) {
            Set<String> keys = new HashSet<String>();
            for(String key : clientsAndSpaces.keySet()) {
                Spaces spaces = clientsAndSpaces.get(key);
                if(spaces.isExpired()) {
                    spaces.clear();
                    keys.add(key);
                }
            }
            clientsAndSpaces.keySet().removeAll(keys);
        }

        public Collection<SharedObject> getSharedObjects(String identity, Class<?> type) {
            return getSpaces(identity).getSharedObjects(type);
        }

        public void shareObject(String identity, Class<?> type, SharedObject object) {
            Set<String> identities = clientsAndSpaces.keySet();
            for(String currentIdentity : identities) {
                if(!identity.equals(currentIdentity)) {
                    getSpaces(currentIdentity).sharedObject(type, object);
                }
            }
        }

        private Spaces getSpaces(String identity) {
            Spaces spaces = clientsAndSpaces.get(identity);
            if(spaces == null || spaces.isExpired()) {
                spaces = new Spaces(System.currentTimeMillis());
                clientsAndSpaces.put(identity, spaces);
            }
            spaces.updateTimestamp();
            return spaces;
        }
    }

    private final class Spaces {

        private long timestamp;
        private final Map<Class<?>, Space> spaces = new ConcurrentHashMap<Class<?>, Space>();

        public Spaces(long timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isExpired() {
            int fiveMinutes = 300000;
            long fiveMinutesAgo = System.currentTimeMillis() - fiveMinutes;
            return timestamp < fiveMinutesAgo;
        }

        public void clear() {
            spaces.clear();
        }

        private Collection<SharedObject> getSharedObjects(Class<?> type) {
            updateTimestamp();
            Space space = spaces.get(type);
            if(space != null) {
                return space.getSharedObjects();
            }
            return new ArrayList<SharedObject>();
        }

        public void sharedObject(Class<?> type, SharedObject object) {
            Space space = spaces.get(type);
            if(space != null) {
                space.add(object);
            } else {
                Space newSpace = new Space();
                newSpace.add(object);
                spaces.put(type, newSpace);
            }
        }

        public void updateTimestamp() {
            timestamp = System.currentTimeMillis();
        }
    }

    private final class Space {

        private final Collection<SharedObject> space = new ConcurrentLinkedQueue<SharedObject>();

        private void add(SharedObject object) {
            space.add(object);
        }

        private Collection<SharedObject> getSharedObjects() {
            Collection<SharedObject> reply = Collections.unmodifiableCollection(new ArrayList<SharedObject>(space));
            space.clear();
            return reply;
        }

    }

}
