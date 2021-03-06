package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.yawf.ApplicationInfo;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.HeaderCachingInformation;
import uk.co.itstherules.yawf.view.TeenyWeenyJavaScriptView;
import uk.co.itstherules.yawf.view.TextResourceView;
import uk.co.itstherules.yawf.view.context.EmptyContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public final class JavaScript extends BaseController {

	@Action(value="Show", requiresObjectCache=false) public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
        HeaderCachingInformation.setAsNeverAskAgain(response);
		String resourceRootPath = ApplicationInfo.getResourceRootPath(provider.getString(ProviderKey.resourceRoot.name()));
    	response.setContentType(ContentType.js.toString());
        String filePath = new StringBuilder(resourceRootPath).append("js/").append(provider.getTitle()).toString();
        new TextResourceView(filePath).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
	}

    @Action(value="Loader", requiresObjectCache=false) public void loader(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
        HeaderCachingInformation.setAsNeverAskAgain(response);
        response.setContentType(ContentType.js.toString());
		List<String> files = provider.getList("files");
        String resourceRootPath = ApplicationInfo.getResourceRootPath(provider.getString(ProviderKey.resourceRoot.name()));
        boolean acceptsGZip = browserAcceptsGZip(provider);
        new TeenyWeenyJavaScriptView(files, resourceRootPath, acceptsGZip, provider.getBoolean("uncompressed", Boolean.FALSE)).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
	}

    private boolean browserAcceptsGZip(ValuesProvider provider) {
        String encoding = provider.getString("Accept-Encoding");
        return (!"".equals(encoding) && encoding.contains("gzip"));
    }

}