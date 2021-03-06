package uk.co.itstherules.cardplanner.view;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.model.*;
import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;
import uk.co.itstherules.yawf.view.helper.TitleListHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class CardChange extends BaseModelView {

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
        CardModel card = model(objectCache, provider);
        String root = provider.getApplicationRoot();
        Set<StatusModel> statuses = objectCache.all(StatusModel.class);
        Set<CardTypeModel> types = objectCache.all(CardTypeModel.class);
        Set<ValueTypeModel> valueTypes = objectCache.all(ValueTypeModel.class);
        Set<EffortTypeModel> effortTypes = objectCache.all(EffortTypeModel.class);

        String parentId;
        CardModel invisibleCard = SpecialInstances.retrieve(objectCache, CachedInstance.Identities.INVISIBLE_CARD);
        if(card.getParent()==null) {
            parentId = invisibleCard.getIdentity();
        } else {
            parentId = card.getParent().getIdentity();
        }
        Set<CardModel> cards = objectCache.all(CardModel.class);
        List<CardModel> parents = new ArrayList<CardModel>();
        parents.addAll(cards);
        parents.remove(card);
        View view = new MergedTextView("storyboard/edit_card.freemarker");
        ViewContext context = new ChangeContext("StoryBoard", action(), violations, card);
        context.put("allTags", objectCache.all(TagModel.class));
        context.put("card", card);
        context.put("statuses", statuses);
        context.put("valueTypes", valueTypes);
        context.put("effortTypes", effortTypes);
        context.put("parents", parents);
        context.put("parentIdentity", parentId);
        context.put("titleListHelper", new TitleListHelper());
        context.put("theBacklog", SpecialInstances.retrieve(objectCache, CachedInstance.Identities.THE_BACKLOG));
        context.put("types", types);
        context.put("cards",  cards);
        context.put("cardIdentity", provider.getString("cardIdentity"));
        context.put("cardTitle", provider.getString("cardTitle"));
        context.put(ProviderKey.root.name(), root);


        return new TemplateCompositeModelView(true, view.asText(context, root), "StoryBoard", "").asText(objectCache, provider, new EmptyContext(), violations);
    }

    protected abstract String action();

    protected abstract CardModel model(ObjectCache objectCache, ValuesProvider provider);
}
