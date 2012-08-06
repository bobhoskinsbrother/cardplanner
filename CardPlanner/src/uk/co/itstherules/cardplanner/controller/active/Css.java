package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.Root;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.HeaderCachingInformation;
import uk.co.itstherules.yawf.view.TeenyWeenyCssView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;


public final class Css extends BaseController {

	@Action(value="Show", requiresObjectCache=false) public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
        HeaderCachingInformation.setAsNeverAskAgain(response);
		String title = MessageFormat.format("css/{0}.freemarker", provider.getTitle());
		ViewContext context = new EmptyContext();
		context.put(ProviderKey.root.name(), provider.getApplicationRoot());
		new MergedTextView(title).renderTo(context, response, provider.getApplicationRoot());
	}

    @Action(value="Loader", requiresObjectCache=false) public void loader(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
        response.setContentType(ContentType.css.toString());
        response.setCharacterEncoding("UTF-8");
        HeaderCachingInformation.setAsNeverAskAgain(response);
        List<String> files = provider.getList("files");
        ViewContext context = new EmptyContext();
        String root = provider.getApplicationRoot();
        context.put(ProviderKey.root.name(), root);
        boolean acceptsGZip = browserAcceptsGZip(provider);
        new TeenyWeenyCssView(files, Root.class, acceptsGZip).renderTo(context, response, root);
    }

    private boolean browserAcceptsGZip(ValuesProvider provider) {
        String encoding = provider.getString("Accept-Encoding");
        return (!"".equals(encoding) && encoding.indexOf("gzip") > -1);
    }


}