package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;

interface ModelModifier<T> {
    T modify(ObjectCache objectCache, T board);
}
