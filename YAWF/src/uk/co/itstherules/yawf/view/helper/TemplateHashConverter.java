package uk.co.itstherules.yawf.view.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.SimpleEntityModel;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelIterator;

public final class TemplateHashConverter {
	
	public Collection<Entity<?>> convert(TemplateHashModelEx hash, String... ignore){
		Collection<Entity<?>> entities = new ArrayList<Entity<?>>();
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
	        return entities;
        } catch (TemplateModelException e) {
	        throw new RuntimeException(e);
        }
		
	}
	
}
