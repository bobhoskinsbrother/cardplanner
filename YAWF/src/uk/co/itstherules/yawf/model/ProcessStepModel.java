package uk.co.itstherules.yawf.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import net.sf.oval.constraint.NotBlank;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity 
public final class ProcessStepModel extends IdentifiableDeleteableModel<ProcessStepModel> {
	
	@QueryKey("processKey") @NotBlank private String processKey;
	@QueryKey("body") private String body;
	@QueryKey("input") private String input = "";
	@OneToMany private Set<ProcessStepReferenceModel> references;
	
	@Override
	public ProcessStepModel defaultSetup(ObjectCache objectCache) {
		this.references = new LinkedHashSet<ProcessStepReferenceModel>();
	    return this;
	}
	
	public String getProcessKey() { return processKey; }
	public String getBody() { return body; }
	public String getInput() { return input; }
	
	public ProcessStepModel setInput(String input) {
		this.input = input;
		return this;
	}
	public ProcessStepModel setProcessKey(String processKey) { 
		this.processKey = processKey; 
		return this;
	}
	
	public Set<ProcessStepReferenceModel> getReferences() { return references; }
}
