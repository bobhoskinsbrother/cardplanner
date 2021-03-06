package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.yawf.ApplicationInfo;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.BinaryResourceView;
import uk.co.itstherules.yawf.view.context.EmptyContext;

public final class Images extends BaseController {
	
	@Action(value="Show", requiresObjectCache=false) public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		String resourceRootPath = ApplicationInfo.getResourceRootPath(provider.getString(ProviderKey.resourceRoot.name()));
		new BinaryResourceView(resourceRootPath + "images/"+provider.getTitle()).renderTo(new EmptyContext(), response, "");
	}
	
}