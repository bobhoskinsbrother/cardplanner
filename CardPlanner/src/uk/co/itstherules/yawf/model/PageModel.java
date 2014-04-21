package uk.co.itstherules.yawf.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import net.sf.oval.constraint.NoSelfReference;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity 
public final class PageModel extends IdentifiableDeleteableModel<PageModel> {
	
	@ManyToOne(cascade=CascadeType.PERSIST) @QueryKey(value="parent", cache=CacheInstruction.FromCache) @NoSelfReference private PageModel parent;
	@ManyToOne(cascade=CascadeType.PERSIST) @QueryKey(value="template", cache=CacheInstruction.FromCache) private TemplateModel template;
	@OneToOne(cascade=CascadeType.PERSIST) @QueryKey(value="body", cache=CacheInstruction.FromCache) private TemplateModel body;
	@QueryKey("navigable") private Boolean navigable = Boolean.TRUE;
	
	@Override
	public PageModel defaultSetup(ObjectCache objectCache) {
		return this;
	}
	public TemplateModel getBody() { return body; }
	public PageModel getParent() { return parent; }
	public TemplateModel getTemplate() { return template; }
	public void setParent(PageModel parent) { this.parent = parent; }
	public void setTemplate(TemplateModel template) { this.template = template; }
	public void setBody(TemplateModel body) { this.body = body; }
	public Boolean getNavigable() { return navigable; }
	public void setNavigable(Boolean navigable) { this.navigable = navigable; }
}