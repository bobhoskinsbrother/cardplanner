package uk.co.itstherules.yawf.view.json;

import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.model.serializer.Json;
import uk.co.itstherules.yawf.view.TextStringView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ViewContext;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class JsonView implements View {

	private TextStringView delegate;

	public JsonView(Object object, String... toInclude) {
		delegate = new TextStringView(new Json<Object>().serialize(object, toInclude));
	}

	public JsonView(Object objects, List<String> includes, List<String> excludes) {
		delegate = new TextStringView(new Json<Object>().serialize(objects, includes, excludes));
	}

	@Override public String asText(ViewContext context, String root) { throw new RuntimeException(); }

	@Override public void renderTo(ViewContext context, HttpServletResponse response, String root) {
    	response.setContentType(ContentType.json.toString());
		delegate.renderTo(context, response, root);
    }
    public String getTitle() { return this.getClass().getSimpleName(); }
}