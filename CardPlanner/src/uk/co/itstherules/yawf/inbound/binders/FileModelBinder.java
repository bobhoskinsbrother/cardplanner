package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import net.sf.oval.Validator;

import org.apache.commons.fileupload.FileItem;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.FileModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public class FileModelBinder implements QueryValueBinder {
	
	public FileModelBinder() {  }

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		provider = provider.limitContext(currentQueryKey);
		FileModel fileModel = null;
		if(!"".equals(provider.getIdentity())) {
			fileModel = objectCache.retrieveByIdentity(FileModel.class, provider.getIdentity());
		}
		if(fileModel == null) {
			FileItem fileItem = provider.getFileItem("file");
			fileModel = new FileModel(fileItem);
		}
		if(fileModel != null) {
			new BasicValuesProviderBinder().bind(provider, fileModel, objectCache);
		}
		if(fileModel != null) { Fields.set(model, field, fileModel); }
		violations.add(fullQueryKey, new Validator().validateFieldValue(model, field, fileModel));
    }

	public boolean canHandle(Class<?> fieldClass, Type genericType) {
	    return true;
    }

}