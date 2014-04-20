package uk.co.itstherules.yawf.view.atom;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.date.DateConverter;
import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.model.atom.AtomModel;
import uk.co.itstherules.yawf.view.MergedTextView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public final class AtomView implements View {

	private final AtomModel model;

	public AtomView(AtomModel model) {
		this.model = model;
    }
	
	@Override public String getTitle() { return "Atom"; }

	@Override
    public String asText(ViewContext context, String root) {
		throw new RuntimeException();
    }

	@Override
    public void renderTo(ViewContext context, HttpServletResponse response, String root) {
		response.setContentType(ContentType.xml.toString());
		context = new EmptyContext();
		context.put("atom", model);
		context.put("dateConverter", new DateConverter());
		new MergedTextView("atom.freemarker", AtomView.class).renderTo(context, response, root);
    }
	
	
}
