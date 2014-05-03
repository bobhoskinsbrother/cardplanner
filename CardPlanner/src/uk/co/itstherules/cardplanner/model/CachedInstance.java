package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;
import uk.co.itstherules.yawf.MapBuilder;
import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.Identity;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import java.util.Map;

import static uk.co.itstherules.cardplanner.model.CachedInstance.Identities.*;

public final class CachedInstance {

	public enum Identities implements Identity {

		CONFIGURATION_MODEL(ConfigurationModel.class, m("1952bba9-bee2-43e5-a202-81637bf6e479").put("firstRun", "true").put("objectState","Invisible").put("title","Default Configuration").build()),

		PLANNED(StatusModel.class, m("6ea38f66-9c49-48a4-8e26-03e3c888de35").put("title", "Planned").build()),
		IN_PROGRESS(StatusModel.class, m("1e5da83b-0a9d-4428-af28-7187b8718d2b").put("title", "In Progress").build()),
		DONE(StatusModel.class, m("f828ca79-7cca-43ef-89bd-10b3e5825958").put("title", "Done").build()),
		THE_BACKLOG(StatusModel.class, m("6efda9ea-ef05-4511-b80e-3c5ac2b98f4d").put("objectState","Invisible").put("title","The Backlog").build()),

		CURRENCY_VALUE_TYPE(ValueTypeModel.class, m("00a2df98-7293-471d-965e-365a963b5cfa").put("objectState","Invisible").put("title","Currency").put("rate","1.0").build()),

		IDEAL_DAY_EFFORT_TYPE(EffortTypeModel.class, m("bed78a3f-9c04-40a9-b565-0889f9d782e2").put("objectState","Invisible").put("title","Ideal Day").put("rate","1.0").put("type","NumericBased").build()),
		DEFAULT_ATTACHMENT(SimpleAttachmentModel.class, m("894301b2-f8d0-4999-af7a-5747a742fefb").put("objectState","Invisible").put("title","Empty").build()),
		USER_STORY_ITEM_TYPE(CardTypeModel.class, m("8d423afc-bc42-47e4-a8ba-f9d0726fa7b9").put("objectState","Invisible").put("title","User Story").put("colour","#EEEEFF").build()),
		INVISIBLE_CARD(CardModel.class, m("0a312c5f-a8ae-423e-86d9-7f9ddf84d3ff").put("objectState","Invisible").put("title","Empty").put("effort.identity","d0ee62e6-7232-4376-ba4f-53e8fbcae34e").put("type.identity","8d423afc-bc42-47e4-a8ba-f9d0726fa7b9").put("value.identity","9e54ecdf-9d22-4d22-8186-b73edfc554e8").put("body","Empty").put("tags","").put("people.0.identity","82f0fd2a-c3c0-48c8-8cbd-df999eb4f913").build()),
		INVISIBLE_PERSON(PersonModel.class, m("82f0fd2a-c3c0-48c8-8cbd-df999eb4f913").put("objectState","Invisible").put("picture.identity","894301b2-f8d0-4999-af7a-5747a742fefb").put("firstName","Empty").put("lastName","Empty").put("initials","NIL").build()),

		DEFAULT_EFFORT(EffortModel.class, m("d0ee62e6-7232-4376-ba4f-53e8fbcae34e").put("amount","0.0").put("title","Empty").put("type.identity","bed78a3f-9c04-40a9-b565-0889f9d782e2").build()),
		DEFAULT_VALUE(ValueModel.class, m("9e54ecdf-9d22-4d22-8186-b73edfc554e8").put("amount","0.0").put("type.identity","00a2df98-7293-471d-965e-365a963b5cfa").build()),
		;

		private final Class<? extends IdentityDeleteable<?>> type;
        private final Map<String, Object> v;

        private Identities(Class<? extends IdentityDeleteable<?>> type, Map<String, Object> v) {
			this.type = type;
            this.v = v;
        }
        public ValuesProvider provider() { return new MapValuesProvider(v); }
		public String getIdentity() { return String.valueOf(v.get("identity")); }
		public Class<? extends IdentityDeleteable<?>> toInstantiate() { return type; }

	}

    private static MapBuilder<String, Object> m(String identity) {
        final MapBuilder<String, Object> builder = new MapBuilder<>();
        return builder.put("identity", identity);
    }

    public void initialise(ObjectCache objectCache) {
		//Order matters - need the fine grained objects to be there first for the collaborators
		
		SpecialInstances instances = new SpecialInstances(objectCache);
		instances.persistIfNotExists(Identities.CONFIGURATION_MODEL);
		ConfigurationModel configurationModel = objectCache.retrieveByIdentity(ConfigurationModel.class, Identities.CONFIGURATION_MODEL.getIdentity());
		if(configurationModel.getFirstRun()) {
			instances.persistIfNotExists(PLANNED);
			instances.persistIfNotExists(IN_PROGRESS);
			instances.persistIfNotExists(DONE);
			instances.persistIfNotExists(IDEAL_DAY_EFFORT_TYPE);
			instances.persistIfNotExists(DEFAULT_EFFORT);
			instances.persistIfNotExists(CURRENCY_VALUE_TYPE);
			instances.persistIfNotExists(DEFAULT_VALUE);
			instances.persistIfNotExists(THE_BACKLOG);
			instances.persistIfNotExists(USER_STORY_ITEM_TYPE);
			instances.persistIfNotExists(DEFAULT_ATTACHMENT);
			instances.persistIfNotExists(INVISIBLE_PERSON);
			instances.persistIfNotExists(INVISIBLE_CARD);
			configurationModel.firstRunComplete();
			objectCache.save(configurationModel);
		}
    }

}
