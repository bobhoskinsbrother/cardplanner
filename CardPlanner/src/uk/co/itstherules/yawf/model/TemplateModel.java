package uk.co.itstherules.yawf.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.register.Keyed;

@Entity 
public final class TemplateModel extends IdentifiableDeleteableModel<TemplateModel> implements Keyed {

	public enum TemplateMarkup { FreeMarker, WikiCreole }
	public enum TemplateType { FromDatabase, FromModelView }
	public enum TemplateModelType {Frame, Body}
	
	
	@QueryKey("content") private String content = "";
	@QueryKey("template") private String template = "";
	@Enumerated(EnumType.STRING) @QueryKey("templateMarkup") private TemplateMarkup templateMarkup = TemplateMarkup.FreeMarker;
	@Enumerated(EnumType.STRING) @QueryKey("templateType") private TemplateType templateType = TemplateType.FromModelView;
	@Enumerated(EnumType.STRING) @QueryKey("modelType") private TemplateModelType modelType = TemplateModelType.Body;
	

	@Override public String getKey() { return getTemplate(); }
	@Override public String toString() { return template; }
	public String getTemplate() { return template; }
	public TemplateType getTemplateType() { return templateType; }
	public TemplateModelType getModelType() { return modelType; }
	public TemplateMarkup getTemplateMarkup() { return templateMarkup; }
	public String getContent() { return content; }

	public void setTemplateType(TemplateType templateType) { this.templateType = templateType; }
	public void setTemplate(String template) { this.template = template; }
	public void setModelType(TemplateModelType modelType) { this.modelType = modelType; }
	public void setTemplateMarkup(TemplateMarkup templateMarkup) { this.templateMarkup = templateMarkup;}

	@Override public TemplateModel defaultSetup(ObjectCache objectCache) {
		this.templateMarkup = TemplateMarkup.FreeMarker;
		return this;
	}
	
}