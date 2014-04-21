package uk.co.itstherules.ui.functions;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.CardTypeModel;
import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.yawf.MapBuilder;
import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.EntityManagerListener;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.persistence.JPAObjectCache;

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
        EffortTypeModel instance = new EffortTypeModel().defaultSetup(objectCache);
        Map<String, Object> m = new MapBuilder<String, Object>().put("title", title).put("type", type).put("rate", rate).build();
        bindAndSave(instance, m);
        return instance;
    }

    public CardModel saveSimpleCard() {
        CardModel instance = new CardModel().defaultSetup(objectCache);
        MapBuilder<String, Object> b = new MapBuilder<>();
        Map<String, Object> map =
                b.put("identity", "658c9802-f227-458d-958a-ceba3b88ddd0").put("objectState", "Active").put("parent.identity", "0a312c5f-a8ae-423e-86d9-7f9ddf84d3ff")
                .put("title", "I am an card that requires editing").put("effort.identity", "d0ee62e6-7232-4376-ba4f-53e8fbcae34e").put("type.identity", "8d423afc-bc42-47e4-a8ba-f9d0726fa7b9")
                .put("value.identity", "9e54ecdf-9d22-4d22-8186-b73edfc554e8").put("body", "as I may have a few tpyos").put("people.0.identity", "82f0fd2a-c3c0-48c8-8cbd-df999eb4f913").build();
        bindAndSave(instance, map);
        return instance;
    }

    public CardTypeModel saveCardType(String title, String colour) {
        CardTypeModel instance = new CardTypeModel().defaultSetup(objectCache);
        Map<String, Object> map = new MapBuilder<String, Object>().put("title", title).put("colour", colour).build();
        bindAndSave(instance, map);
        return instance;
    }

    private void bindAndSave(IdentityDeleteable<?> instance, Map<String, Object> map) {
        MapValuesProvider provider = new MapValuesProvider(map);
        bind(provider, instance);
        objectCache.save(instance);
        objectCache.commit();
    }

    private void bind(ValuesProvider provider, IdentityDeleteable<?> instance) {
        QueryKeyViolations violations = new BasicValuesProviderBinder().bind(provider, instance, objectCache);
        if (violations.isRegistered()) {
            throw new RuntimeException(violations.toString());
        }
    }

}