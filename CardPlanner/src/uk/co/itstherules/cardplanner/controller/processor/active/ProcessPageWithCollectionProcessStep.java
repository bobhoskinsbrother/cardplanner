package uk.co.itstherules.cardplanner.controller.processor.active;

import java.text.MessageFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.controller.processor.ProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.PageModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.modelview.PageModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public final class ProcessPageWithCollectionProcessStep extends BaseProcessStep<String> {
	
	public static final String KEY = "ProcessPageWithCollection";

	public String process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
		Collection<ProcessStep<?>> inputSteps = getInputProcessSteps();
		PageModel page = null;
		Collection<?> collection = null;
		
		for (ProcessStep<?> processStep : inputSteps) {
	        boolean set = false;
	        Object reply = processStep.process(objectCache, provider, response, viewFactory, violations);
	        if(PageModel.class.isInstance(reply)) {
	        	page = PageModel.class.cast(reply);
		        set = true;
	        }
	        if(Collection.class.isInstance(reply)) {
	        	collection = Collection.class.cast(reply);
		        set = true;
	        }
	        Assertion.checkIsTrue(set, MessageFormat.format("Process {0} was expected to produce either a PageModel, or Collection.  It did neither.  It produced {1}", processStep.getKey(), reply));
        }

		Assertion.checkNotInvisibleObject(page, "When using the ViewPageWithCollection process step, the Page cannot be an invisible object");
		Assertion.checkNotNull(collection, "When using the ViewPageWithCollection process step, the Collection cannot be null");
		ViewContext context = new EmptyContext();
		context.put("collection", collection);
		return new PageModelView(page, viewFactory).asText(objectCache, provider, context, violations);
    }

	public String getKey() { return KEY; }

	
	@Override
	public ProcessStepType getProcessStepType() { return ProcessStepType.TERMINAL_END; }

	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(PageModel.class, Collection.class); }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(String.class); }

}
