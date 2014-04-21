package uk.co.itstherules.yawf.inbound.binders;

import net.sf.oval.Validator;
import org.apache.commons.fileupload.FileItem;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.FileModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import java.lang.reflect.Field;
import java.lang.reflect.Type;


public class FileModelBinder implements QueryValueBinder {
	
	public FileModelBinder() {  }

	public void bind(Object model, Field fileModelField, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
        if(!fileModelField.getType().equals(FileModel.class)) { throw new RuntimeException("@UseBinder(FileModelBinder.class) can only be used on a FileModel field"); }

		FileModel fileModel = null;
		if(!"".equals(provider.getIdentity())) {
			fileModel = objectCache.retrieveByIdentity(FileModel.class, provider.getIdentity());
		}
		if(fileModel == null) {
			FileItem fileItem = provider.getFileItem(fullQueryKey);
			fileModel = new FileModel(fileItem);
		}
		if(fileModel != null) {
			new BasicValuesProviderBinder().bind(provider, fileModel, objectCache);
            Fields.set(model, fileModelField, fileModel);
		}
		violations.add(fullQueryKey, new Validator().validateFieldValue(model, fileModelField, fileModel));
    }

	public boolean canHandle(Class<?> fieldClass, Type genericType) {
	    return true;
    }

}