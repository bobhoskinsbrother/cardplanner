package uk.co.itstherules.yawf.modelview;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.PageModel;
import uk.co.itstherules.yawf.model.TemplateModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.view.context.ViewContext;


public final class PageModelView extends BaseModelView {

	private final PageModel pageModel;
	private final ModelViewRegister factory;

	public PageModelView(PageModel pageModel, ModelViewRegister factory) {
		this.pageModel = pageModel;
		this.factory = factory;
    }
	
	@Override public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		TemplateModel bodyModel = pageModel.getBody();
		TemplateModel templateModel = pageModel.getTemplate();
		ModelView bodyView = new BodyModelView(bodyModel, factory);
		ModelView templateView = new TemplateFrameModelView(templateModel, bodyView, factory);
		return templateView.asText(objectCache, valuesProvider, mixInContext, violations);
    }
	
}
