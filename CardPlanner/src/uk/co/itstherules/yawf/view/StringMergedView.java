package uk.co.itstherules.yawf.view;

import java.io.IOException;

import uk.co.itstherules.yawf.FreemarkerTemplateProvider;
import freemarker.template.Template;


public class StringMergedView extends BaseView {

	private final String template;

	public StringMergedView(String template) {
	    super("template");
		this.template = template;
    }

	@Override protected Template getTemplate() throws IOException {
		return FreemarkerTemplateProvider.getInstance().provide(this.templateName, this.template).getTemplate(this.templateName);
	}
	
}
