package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.view.AttachmentsChange;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public class AttachmentsEdit extends AttachmentsChange {

	@Override protected String action() {
	    return "Edit";
    }

    public SimpleAttachmentModel model(ObjectCache objectCache, ValuesProvider provider) {
    	return objectCache.retrieveByIdentity(SimpleAttachmentModel.class, provider.getIdentity());
    }
}
