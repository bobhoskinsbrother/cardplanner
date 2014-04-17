package uk.co.itstherules.yawf.controller.processor;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolationsException;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.NullEntity;
import uk.co.itstherules.yawf.model.ProcessStepModel;
import uk.co.itstherules.yawf.model.ProcessStepReferenceModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public class ProcessStepConnectorPlanExecutor extends BaseProcessStep<NullEntity> {

	private final ProcessStepReferenceModel lastStep;
	
	public ProcessStepConnectorPlanExecutor(ProcessStepReferenceModel lastStep) {
		this.lastStep = lastStep;
    }
	
	@Override public NullEntity process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) throws QueryKeyViolationsException {
		ProcessStep<?> processStep = getProcessStepAndPopulate(this.lastStep);
		processStep.process(objectCache, provider, response, viewFactory, violations);
		return new NullEntity();
    }

	private ProcessStep<?> getProcessStepAndPopulate(ProcessStepReferenceModel outputModel) {
		ProcessStep<?> outputProcessStep = getProcessStepForModelAndPopulateWithStringInputFromModel(outputModel.getStep());
		List<ProcessStepReferenceModel> inputModelsForOutput = outputModel.getInputSteps();
		for (ProcessStepReferenceModel inputForOutput : inputModelsForOutput) {
			ProcessStep<?> inputProcessStep = getProcessStepForModelAndPopulateWithStringInputFromModel(inputForOutput.getStep());
			if(inputProcessStep.getProcessStepType() != ProcessStepType.TERMINAL_START) {
				// chain the population of the whole ProcessStep object graph, up to every TERMINAL_START;
				inputProcessStep = getProcessStepAndPopulate(inputForOutput);
			}
			outputProcessStep.addInputStep(inputProcessStep);
        }
		return outputProcessStep;
    }

	private ProcessStep<?> getProcessStepForModelAndPopulateWithStringInputFromModel(ProcessStepModel processStepModel) {
		Assertion.checkNotNull(processStepModel);
		ProcessStep<?> step = createNewProcessStepForKey(processStepModel.getProcessKey());
		String input = processStepModel.getInput();
		if(input != null && !"".equals(input)) {
			ProcessStep<String> stringProcessStep = new StringProcessStep(input);
			step.addInputStep(stringProcessStep);
		}
	    return step;
    }

	private ProcessStep<?> createNewProcessStepForKey(String key) {
		return ProcessStepRegisterListener.createStepFor(key);
	}
	
	@Override public String getKey() { return "ProcessStepConnectorPlanExecutor"; }
	@Override public ProcessStepType getProcessStepType() { return ProcessStepType.TERMINAL_END; }
	@Override public ClassDescriptionModel[] getInputs() { return new ClassDescriptionModel[]{}; }
	@Override public ClassDescriptionModel getOutput() { return new ClassDescriptionModel(NullEntity.class); }

}
