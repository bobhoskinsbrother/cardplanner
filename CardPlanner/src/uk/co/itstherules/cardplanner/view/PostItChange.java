package uk.co.itstherules.cardplanner.view;

import java.util.Set;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.model.CachedInstance;
import uk.co.itstherules.cardplanner.model.PostItModel;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public abstract class PostItChange extends BaseModelView {

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
        String root = provider.getApplicationRoot();
        Set<SimpleAttachmentModel> attachments = objectCache.all(SimpleAttachmentModel.class);
        SimpleAttachmentModel defaultAttachment = SpecialInstances.retrieve(objectCache, CachedInstance.Identities.DEFAULT_ATTACHMENT);
        attachments.remove(defaultAttachment);
        PostItModel postIt = model(objectCache, provider);

        ViewContext context = new ChangeContext("StoryBoard", action(), violations, postIt);

        context.put("nullAttachment", defaultAttachment);
        context.put("postIt", postIt);
        context.put("attachments", attachments);
        context.put("cardIdentity", provider.getString("cardIdentity"));
        context.put("cardTitle", provider.getString("cardTitle"));
        context.put(ProviderKey.root.name(), root);
        View view = new MergedTextView("storyboard/edit_postit.freemarker");
        return new TemplateCompositeModelView(true, view.asText(context, root), "StoryBoard", "", false).asText(objectCache, provider, new EmptyContext(), violations);
    }

    protected abstract String action();

    protected abstract PostItModel model(ObjectCache objectCache, ValuesProvider provider);
}
