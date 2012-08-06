package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.cardplanner.view.active.ExcelExportXls;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;

public final class ExcelExport extends BaseController {

	@Action("List") public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		View view = new MergedTextView("excelexport/show.freemarker");
		String root = provider.getApplicationRoot();
		new TemplateCompositeModelView(false, view.asText(new EmptyContext(), root), getTitle(), "", false).renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}

	@Action("Feed") public void feed(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
	    new ExcelExportXls().renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}
	
}