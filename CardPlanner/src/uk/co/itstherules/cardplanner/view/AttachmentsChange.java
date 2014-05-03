package uk.co.itstherules.cardplanner.view;

import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

import java.util.Set;


public abstract class AttachmentsChange extends BaseModelView {

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		Set<SimpleAttachmentModel> attachments = objectCache.all(SimpleAttachmentModel.class);
		attachments.remove(SpecialInstances.retrieve(objectCache, Identities.DEFAULT_ATTACHMENT));
		ViewContext context = new EmptyContext();
		if(mixInContext!=null) {context.putAll(mixInContext);}
		context.put("attachments", attachments);
		context.put("attachment", model(objectCache, valuesProvider));
		context.put("action", action());
		context.put("violations", violations);
		View view = new MergedTextView("attachments/list.freemarker");
		String root = valuesProvider.getApplicationRoot();
		return new TemplateCompositeModelView(true,  view.asText(context, root), getKey(), "").asText(objectCache, valuesProvider, new EmptyContext(), violations);
	}
	
	protected abstract String action();
	public abstract SimpleAttachmentModel model(ObjectCache objectCache, ValuesProvider valuesProvider);

}    

