package uk.co.itstherules.cardplanner.view.active;

import java.math.BigInteger;

import uk.co.itstherules.cardplanner.model.StatusModel;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.SingleValueContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class ShowAdmin extends BaseModelView {

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		BigInteger statuses = objectCache.count(StatusModel.class);
		View view = new MergedTextView("admin/show.freemarker");
		ViewContext context = new SingleValueContext("showHardSums", new Boolean(statuses.intValue() > 2));
		String root = valuesProvider.getApplicationRoot();
		return new TemplateCompositeModelView(false, view.asText(context, root), getKey(), "", false).asText(objectCache, valuesProvider, new EmptyContext(), violations);
    }
}
