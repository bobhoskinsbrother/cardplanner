package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.model.StatusModel;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.json.JsonView;


public final class StatusOrder extends BaseController {
	
	@Action("Update") public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		List<String> statuses = provider.getList(ProviderKey.statuses.name());
		int count = 0;
		for (String identity : statuses) {
			StatusModel status = objectCache.retrieveByIdentity(StatusModel.class, identity);
	        status.setSortOrder(count);
	        objectCache.save(status);
	        count++;
        }
		new JsonView(Collections.singletonMap("updated", "true")).renderTo(new EmptyContext(), response, "");
	}
	
}
