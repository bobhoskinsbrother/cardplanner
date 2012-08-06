package uk.co.itstherules.cardplanner.controller.processor.active;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.NullEntity;
import uk.co.itstherules.yawf.model.PageModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.modelview.PageModelView;

public final class ViewPageProcessStep extends BaseProcessStep<NullEntity> {
	
	public static final String KEY = "ViewPage";

	public NullEntity process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
		Object object = processFirstInboundStep(objectCache, provider, response, viewFactory, violations);
		Assertion.checkIsInstanceOf(PageModel.class, object);
		PageModel page = PageModel.class.cast(object);
		Assertion.checkNotInvisibleObject(page, "When using the ViewPage process step, the Page cannot be an invisible object");
		new PageModelView(page, viewFactory).renderTo(objectCache, provider, response, null, violations);
		return new NullEntity();
    }

	public String getKey() { return KEY; }

	
	@Override
	public ProcessStepType getProcessStepType() { return ProcessStepType.TERMINAL_END; }

	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(PageModel.class); }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(NullEntity.class); }

}
