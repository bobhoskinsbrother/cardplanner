package uk.co.itstherules.ui.functions;

import java.io.IOException;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.CardTypeModel;
import uk.co.itstherules.cardplanner.model.EffortModel;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.cardplanner.model.ValueModel;
import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.ObjectCacheFactory;

public class DataInitializer {
	
	public static void initializeData(String dataScenarioKey, String applicationName) throws IOException {
		ObjectCache objectCache = ObjectCacheFactory.get(applicationName);
		SpecialInstances specialInstances = new SpecialInstances(objectCache);
		if("simple_one_card".equals(dataScenarioKey)) { addSimpleOneCard(specialInstances); }
		objectCache.close();
	}
	
	private static void addSimpleOneCard(SpecialInstances specialInstances) {
        specialInstances.resetOrPersist(new ID("894301b2-f8d0-4999-af7a-5747a742fefb", SimpleAttachmentModel.class));
		specialInstances.resetOrPersist(new ID("82f0fd2a-c3c0-48c8-8cbd-df999eb4f913", PersonModel.class));
        specialInstances.resetOrPersist(new ID("00a2df98-7293-471d-965e-365a963b5cfa", ValueTypeModel.class));
        specialInstances.resetOrPersist(new ID("9e54ecdf-9d22-4d22-8186-b73edfc554e8", ValueModel.class));
		specialInstances.resetOrPersist(new ID("8d423afc-bc42-47e4-a8ba-f9d0726fa7b9", CardTypeModel.class));
        specialInstances.resetOrPersist(new ID("bed78a3f-9c04-40a9-b565-0889f9d782e2", EffortTypeModel.class));
		specialInstances.resetOrPersist(new ID("d0ee62e6-7232-4376-ba4f-53e8fbcae34e", EffortModel.class));
		specialInstances.resetOrPersist(new ID("658c9802-f227-458d-958a-ceba3b88ddd0", CardModel.class));
	}
	
}