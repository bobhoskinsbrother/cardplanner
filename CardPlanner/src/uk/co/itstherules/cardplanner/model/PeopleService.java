package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;

import java.util.Set;

public final class PeopleService {

    public Set<PersonModel> allVisible(ObjectCache objectCache) {
        return objectCache.all(PersonModel.class, new QueryConditions("AND").put("visible", true).put("identity", QueryConditions.Operator.NotEquals, CachedInstance.Identities.INVISIBLE_PERSON.getIdentity()));
    }

}
