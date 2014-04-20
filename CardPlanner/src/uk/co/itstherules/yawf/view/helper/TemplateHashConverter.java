package uk.co.itstherules.yawf.view.helper;

import freemarker.template.*;
import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.EntityComparator;
import uk.co.itstherules.yawf.model.SimpleEntityModel;

import java.util.*;

public final class TemplateHashConverter {
	
	public Collection<Entity<?>> convert(TemplateHashModelEx hash, String... ignore){
		List<Entity<?>> entities = new LinkedList<Entity<?>>();
		List<String> ignoreList = Arrays.asList(ignore);
		TemplateCollectionModel keys;
        try {
	        keys = hash.keys();
	        for (TemplateModelIterator iterator = keys.iterator(); iterator.hasNext();) {
	            TemplateModel model = iterator.next();
	            String key = model.toString();
	            if(!ignoreList.contains(key)) {
	            	entities.add(new SimpleEntityModel(key, hash.get(key).toString()));
	            }
	        }
            Collections.sort(entities, new EntityComparator());
            return entities;
        } catch (TemplateModelException e) {
	        throw new RuntimeException(e);
        }
		
	}
	
}
