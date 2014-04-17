package uk.co.itstherules.cardplanner.controller.processor;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import uk.co.itstherules.yawf.controller.processor.ProcessStep;
import uk.co.itstherules.yawf.controller.processor.ProcessStep.ProcessStepType;
import uk.co.itstherules.yawf.controller.processor.ProcessStepRegisterListener;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;

public class ProcessStepIOProvider {
	
	public List<String> getInput(String key) {
		List<String> required = new LinkedList<String>();
		ClassDescriptionModel[] requiredClasses = ProcessStepRegisterListener.getStep(key).getInputs();
		for (ClassDescriptionModel current : requiredClasses) {
			required.add(current.toString());
        }
		return required;
	}
	
	public String getOutput(String key) {
		return ProcessStepRegisterListener.getStep(key).getOutput().toString();
	}
	
	public ProcessStepType getProcessStepType(String key) {
		return ProcessStepRegisterListener.getStep(key).getProcessStepType();
	}

	public Set<ProcessStep<?>> allProcessSteps() {
		return ProcessStepRegisterListener.availableSteps();
    }

	
}
