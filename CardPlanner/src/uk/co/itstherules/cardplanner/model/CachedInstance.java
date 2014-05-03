package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;
import uk.co.itstherules.yawf.model.*;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public final class CachedInstance {

	public enum Identities implements Identity {
		CONFIGURATION_MODEL("1952bba9-bee2-43e5-a202-81637bf6e479", ConfigurationModel.class),

		PLANNED("6ea38f66-9c49-48a4-8e26-03e3c888de35", StatusModel.class),
		IN_PROGRESS("1e5da83b-0a9d-4428-af28-7187b8718d2b", StatusModel.class),
		DONE("f828ca79-7cca-43ef-89bd-10b3e5825958", StatusModel.class),
		
		EMPTY_BODY("3e898331-0492-4dab-863b-56f65c21d4be", TemplateModel.class),
		
		CARDPLANNER_TEMPLATE("d3fb23f7-e862-4522-974f-c9af0ec4c0ff", TemplateModel.class), 
		CLEAN_TEMPLATE("03e20d90-2957-4524-8723-5931fede5001", TemplateModel.class),
		SIMPLE_TEMPLATE("ccbd639c-22f1-4bdf-b23f-75eb4fdebbe7", TemplateModel.class), 

		CURRENCY_VALUE_TYPE("00a2df98-7293-471d-965e-365a963b5cfa", ValueTypeModel.class),
		IDEAL_DAY_EFFORT_TYPE("bed78a3f-9c04-40a9-b565-0889f9d782e2", EffortTypeModel.class),
		DEFAULT_ATTACHMENT("894301b2-f8d0-4999-af7a-5747a742fefb", SimpleAttachmentModel.class),
		INVISIBLE_CARD("0a312c5f-a8ae-423e-86d9-7f9ddf84d3ff", CardModel.class),
		USER_STORY_ITEM_TYPE("8d423afc-bc42-47e4-a8ba-f9d0726fa7b9", CardTypeModel.class),
		INVISIBLE_PERSON("82f0fd2a-c3c0-48c8-8cbd-df999eb4f913", PersonModel.class),
		THE_BACKLOG("6efda9ea-ef05-4511-b80e-3c5ac2b98f4d", StatusModel.class),

		DEFAULT_EFFORT("d0ee62e6-7232-4376-ba4f-53e8fbcae34e", EffortModel.class),
		DEFAULT_VALUE("9e54ecdf-9d22-4d22-8186-b73edfc554e8", ValueModel.class),
		
		BASE_PERSON("fba82635-9578-4801-b377-05423ea059dd", PersonModel.class),
		;
		

		/* 
		
		168b676f-d45a-485b-b4e1-41805a3af456
		105e77ab-8917-41d0-8f1b-d54528911cd1
		9cacefdd-880f-4120-abdb-aecd25303039
		 */
		
		
		private final String identity;
		private final Class<? extends IdentityDeleteable<?>> type;

		private Identities(String identity, Class<? extends IdentityDeleteable<?>> type) { 
			this.identity = identity;
			this.type = type; 
		}
		public String getIdentity() { return identity; }
		public Class<? extends IdentityDeleteable<?>> toInstantiate() { return type; }
	}
	
	public void initialise(ObjectCache objectCache) {
		//Order matters - need the fine grained objects to be there first for the collaborators
		
		SpecialInstances instances = new SpecialInstances(objectCache);
		instances.persistIfNotExists(Identities.CONFIGURATION_MODEL);
		ConfigurationModel configurationModel = objectCache.retrieveByIdentity(ConfigurationModel.class, Identities.CONFIGURATION_MODEL.getIdentity());
		if(configurationModel.getFirstRun()) {
			instances.persistIfNotExists(Identities.PLANNED);
			instances.persistIfNotExists(Identities.IN_PROGRESS);
			instances.persistIfNotExists(Identities.DONE);
			instances.persistIfNotExists(Identities.IDEAL_DAY_EFFORT_TYPE);
			instances.persistIfNotExists(Identities.DEFAULT_EFFORT);
			instances.persistIfNotExists(Identities.CURRENCY_VALUE_TYPE);
			instances.persistIfNotExists(Identities.DEFAULT_VALUE);
			instances.persistIfNotExists(Identities.THE_BACKLOG);
			instances.persistIfNotExists(Identities.USER_STORY_ITEM_TYPE);
			instances.persistIfNotExists(Identities.DEFAULT_ATTACHMENT);
			instances.persistIfNotExists(Identities.INVISIBLE_PERSON);
			instances.persistIfNotExists(Identities.INVISIBLE_CARD);
			instances.persistIfNotExists(Identities.CARDPLANNER_TEMPLATE);
			instances.persistIfNotExists(Identities.CLEAN_TEMPLATE);
			instances.persistIfNotExists(Identities.SIMPLE_TEMPLATE);
			instances.persistIfNotExists(Identities.EMPTY_BODY);
			instances.persistIfNotExists(Identities.BASE_PERSON);
			configurationModel.firstRunComplete();
			objectCache.save(configurationModel);
		}
    }

}
