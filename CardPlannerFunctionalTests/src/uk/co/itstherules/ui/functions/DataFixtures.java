package uk.co.itstherules.ui.functions;

import uk.co.itstherules.cardplanner.model.CardTypeModel;
import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.EntityManagerListener;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.persistence.JPAObjectCache;

import java.util.HashMap;
import java.util.Map;

public class DataFixtures {

    private JPAObjectCache objectCache;

    public DataFixtures() {
        objectCache = new JPAObjectCache(EntityManagerListener.getEntityManagerFactory());
    }

    public void destroy(IdentityDeleteable<?> object) {
        objectCache.destroy(object);
        objectCache.commit();
    }

    public EffortTypeModel saveEffortType(String title, String type, String rate) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("type", type);
        map.put("rate", rate);
        MapValuesProvider provider = new MapValuesProvider(map);
        final EffortTypeModel instance = new EffortTypeModel().defaultSetup(objectCache);
        bind(provider, instance);
        objectCache.save(instance);
        objectCache.commit();
        return instance;
    }

    public CardTypeModel saveCardType(String title, String colour) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("colour", colour);
        MapValuesProvider provider = new MapValuesProvider(map);
        final CardTypeModel instance = new CardTypeModel().defaultSetup(objectCache);
        bind(provider, instance);
        objectCache.save(instance);
        objectCache.commit();
        return instance;
    }

    private void bind(ValuesProvider provider, IdentityDeleteable<?> instance) {
        QueryKeyViolations violations = new BasicValuesProviderBinder().bind(provider, instance, objectCache);
        if (violations.isRegistered()) {
            throw new RuntimeException(violations.toString());
        }
    }

}