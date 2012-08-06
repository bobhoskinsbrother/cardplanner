package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.model.BurnUpModel;
import uk.co.itstherules.cardplanner.view.BaseBurnUp;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class EditBurnUp extends BaseBurnUp {
    @Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) throws IllegalArgumentException {
        final BurnUpModel burnUp = objectCache.retrieveByIdentity(BurnUpModel.class, provider.getIdentity());
        return asText(objectCache, burnUp, "Update", provider, mixInContext, violations);
    }
}
