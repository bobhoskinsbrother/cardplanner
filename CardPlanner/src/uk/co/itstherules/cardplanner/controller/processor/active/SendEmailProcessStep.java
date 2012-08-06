package uk.co.itstherules.cardplanner.controller.processor.active;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.processor.BaseSetupAccountProcessStep;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.EmailDetailsModel;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.controller.processor.ProcessStep;
import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.PageModel;
import uk.co.itstherules.yawf.model.email.GMailer;
import uk.co.itstherules.yawf.model.email.UnableToSendEmailException;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.modelview.PageModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public final class SendEmailProcessStep extends BaseSetupAccountProcessStep<PersonModel> {
	
	public static final String KEY = "SendEmail";

	public PersonModel process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
		Collection<ProcessStep<?>> inputSteps = getInputProcessSteps();
		String subject = null;
		PersonModel person = null;
		PageModel page = null;
		
		for (ProcessStep<?> processStep : inputSteps) {
	        boolean set = false;
	        Object reply = processStep.process(objectCache, provider, response, viewFactory, violations);
	        if(PersonModel.class.isInstance(reply)) {
	        	person = PersonModel.class.cast(reply);
		        set = true;
	        }
	        if(PageModel.class.isInstance(reply)) {
	        	page = PageModel.class.cast(reply);
		        set = true;
	        }
	        if(String.class.isInstance(reply)) {
	        	subject = String.class.cast(reply);
		        set = true;
	        }
	        Assertion.checkIsTrue(set, MessageFormat.format("Process {0} was expected to either produce a PersonModel, PageModel, or String.  Instead it produced {1}", processStep.getKey(), reply));
        }
		Assertion.checkNotInvisibleObject(person, "PersonModel has not been set in " + getKey());
		Assertion.checkNotInvisibleObject(page, "PageModel has not been set in " + getKey());
		Assertion.checkNotNull(subject, "Subject has not been set in " + getKey());

	    sendEmail(subject, person, page, objectCache, provider, viewFactory);
	    return person;
    }

	private void sendEmail(String subject, PersonModel person, PageModel page, ObjectCache objectCache, ValuesProvider provider, ModelViewRegister viewFactory) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("identity", person.getIdentity());
		MapValuesProvider confirmProvider = new MapValuesProvider(map);
		String fullName = MessageFormat.format("{0} {1}", person.getFirstName(), person.getLastName());
		ViewContext context = new EmptyContext();
		context.put("instance", person);
		context.put("base", provider.getBase());
		String view = new PageModelView(page, viewFactory).asText(objectCache, confirmProvider, context, new QueryKeyViolations());
		EmailDetailsModel emailDetails = SpecialInstances.retrieve(objectCache, Identities.EMAIL_DETAILS);
		try {
			new GMailer().email(person.getEmail(), fullName, subject, view, emailDetails);
		} catch (UnableToSendEmailException e) {
			//TODO: error view for email not being configured.
			throw e;
		}
    }

	public String getKey() { return KEY; }

	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(PersonModel.class, PageModel.class, String.class); }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(PersonModel.class); }

	@Override
    public ProcessStepType getProcessStepType() {
	    return ProcessStepType.MODULE_CALL;
    }

}
