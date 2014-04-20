package uk.co.itstherules.yawf.modelview;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.TemplateModel;
import uk.co.itstherules.yawf.model.TemplateModel.TemplateMarkup;
import uk.co.itstherules.yawf.model.TemplateModel.TemplateType;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.view.StringMergedView;
import uk.co.itstherules.yawf.view.context.ViewContext;
import uk.co.itstherules.yawf.view.helper.TagBuilder;
import uk.co.itstherules.yawf.view.helper.XHtmlTagBuilder;
import uk.co.itstherules.yawf.view.xhtml.WikiCreoleToXhtml;


public final class BodyModelView extends BaseModelView {

	private final TemplateModel templateModel;
	private final ModelViewRegister factory;

	public BodyModelView(TemplateModel templateModel, ModelViewRegister factory) {
		this.templateModel = templateModel;
		this.factory = factory;
    }

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
		TemplateType templateType = this.templateModel.getTemplateType();
		String text = "";
		
		if(templateType==TemplateType.FromModelView) {
			ModelView modelView = factory.get(this.templateModel.getTemplate());
			text = modelView.asText(objectCache, provider, mixInContext, violations);
		} else if (TemplateType.FromDatabase==templateType) {
			text = new StringMergedView(this.templateModel.getContent()).asText(mixInContext, provider.getApplicationRoot());
			if(this.templateModel.getTemplateMarkup() == TemplateMarkup.WikiCreole) {
				TagBuilder builder = new XHtmlTagBuilder(provider.getApplicationRoot());
				WikiCreoleToXhtml parser = new WikiCreoleToXhtml(builder);
				text = parser.manipulate(text);
			}
			return text;
		}
		return text;
	}

	public String getViewTitle() { return ""; }
	
}
