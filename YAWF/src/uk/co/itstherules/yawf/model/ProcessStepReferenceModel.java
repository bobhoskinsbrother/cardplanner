package uk.co.itstherules.yawf.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import net.sf.oval.constraint.NotBlank;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity 
public final class ProcessStepReferenceModel extends IdentifiableDeleteableModel<ProcessStepReferenceModel> {
	
	@ManyToMany @QueryKey(value="inputSteps", cache=CacheInstruction.FromCache) @NotBlank private List<ProcessStepReferenceModel> inputSteps = new LinkedList<ProcessStepReferenceModel>();
	@ManyToOne @QueryKey(value="step", cache=CacheInstruction.FromCache) @NotBlank private ProcessStepModel step;
	public ProcessStepModel getStep() { return step; }
	public List<ProcessStepReferenceModel> getInputSteps() { return inputSteps; }

    @Override
    public ProcessStepReferenceModel defaultSetup(ObjectCache objectCache) {
        return this;
    }
}