package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.view.AttachmentsChange;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public class AttachmentsAdd extends AttachmentsChange {

	@Override protected String action() {
	    return "Add";
    }

    public SimpleAttachmentModel model(ObjectCache objectCache, ValuesProvider provider) {
	    return new SimpleAttachmentModel().defaultSetup(objectCache);
    }
}