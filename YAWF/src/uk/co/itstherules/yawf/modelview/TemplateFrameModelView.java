package uk.co.itstherules.yawf.modelview;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.TemplateModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public final class TemplateFrameModelView extends BaseModelView {

	private final TemplateModel templateModel;
	private final ModelViewRegister viewRegister;
	private final ModelView bodyView;

	public TemplateFrameModelView(TemplateModel templateModel, ModelView bodyView, ModelViewRegister viewRegister) {
		this.templateModel = templateModel;
		this.bodyView = bodyView;
		this.viewRegister = viewRegister;
    }

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		ModelView modelView = this.viewRegister.get(this.templateModel.getTemplate());
		if(mixInContext==null) { mixInContext = new EmptyContext(); }
		try {
			mixInContext.put("content", bodyView.asText(objectCache, valuesProvider, mixInContext, violations));
			mixInContext.put("title", bodyView.getViewTitle());
		} catch(Exception e) {
			mixInContext.put("content", e.getMessage());
			mixInContext.put("title", "An error has occurred");
		}
		return modelView.asText(objectCache, valuesProvider, mixInContext, violations);
	}
	
}
