package uk.co.itstherules.cardplanner.view;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import uk.co.itstherules.cardplanner.controller.shared.SharedObject;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.PostItModel;
import uk.co.itstherules.cardplanner.model.StatusModel;
import uk.co.itstherules.yawf.model.serializer.Json;

public final class SerializeModel {

    private SerializeModel(){}

    public static String card(CardModel card) {
        return new Json<Object>().serialize(card, "model", "*.people", "*.facts", "*.tags");
    }

    public static String postIt(PostItModel postIt) {
        return new Json<Object>().serialize(postIt);
    }

    public static String status(StatusModel status) {
        return new Json<Object>().serialize(status);
    }

    public static String modelsToValidJson(Map<String, Collection<SharedObject>> models) {
        Iterator<String> iterator = models.keySet().iterator();
        StringBuffer buffer = new StringBuffer("{");
        while(iterator.hasNext()) {
            String key = iterator.next();
            Collection<SharedObject> objects = models.get(key);
            buffer.append("\"").append(key).append("\":[");
            for (SharedObject object : objects) {
                buffer.append(object.toString());
            }
            buffer.append("]");
            if(iterator.hasNext()) { buffer.append(","); }
        }
        return buffer.append("}").toString();
    }
    
}
