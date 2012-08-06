package uk.co.itstherules.cardplanner.model.binder;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import net.sf.oval.Validator;
import uk.co.itstherules.cardplanner.model.TagModel;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.Fields;
import uk.co.itstherules.yawf.inbound.binders.QueryValueBinder;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public final class TagsBinder implements QueryValueBinder {
	
	public boolean canHandle(Class<?> fieldClass, Type genericType) {
		return true;
	}

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		Set<String> tagsString = new LinkedHashSet<String>(provider.getList(fullQueryKey));
		Set<TagModel> tags = new LinkedHashSet<TagModel>();
		for (String tagString : tagsString) {
			TagModel retrieved = objectCache.<TagModel>retrieve(TagModel.class, "title", tagString);
			if (retrieved == null) {
				TagModel tag = new TagModel(tagString.trim());
				objectCache.save(tag);
				tags.add(tag);
			} else {
				tags.add(retrieved);
			}
		}
		Fields.set(model, field, tags);
		violations.add(fullQueryKey, new Validator().validateFieldValue(model, field, tags));
	}
}