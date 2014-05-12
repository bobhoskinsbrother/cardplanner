package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.controller.shared.SharedObject;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpaceClient;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpacesListener;
import uk.co.itstherules.cardplanner.model.*;
import uk.co.itstherules.cardplanner.view.SerializeModel;
import uk.co.itstherules.yawf.MapBuilder;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.TextStringView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.SingleValueContext;
import uk.co.itstherules.yawf.view.helper.UrlBuilder;
import uk.co.itstherules.yawf.view.json.JsonView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class StoryBoard extends BaseController {


    @Action("MoveCard") public void moveCard(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws Exception {
        CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity(), ObjectState.Active, ObjectState.Archived, ObjectState.Invisible);
        StatusModel fromStatus = card.getStatus();
        new BasicValuesProviderBinder().bind(provider, card, objectCache);
        StatusModel toStatus = card.getStatus();
        objectCache.save(card);
        LogModel log = new LogModel(card, fromStatus, toStatus, "Update");
        objectCache.save(log);
        shareCard(provider, card, SharedObject.Action.UPDATE);
        replyWithObject(provider, response, card);
    }

    @Action("AddCard") public void addCard(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        viewRegister.get("AddCard").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }

    @Action("EditCard") public void editCard(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        viewRegister.get("EditCard").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }

    @Action("DeleteCard") public void deleteCard(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity(), ObjectState.Active, ObjectState.Archived, ObjectState.Invisible);
        objectCache.delete(card);
        LogModel log = new LogModel(card, card.getStatus(), card.getStatus(), "Delete");
        objectCache.save(log);
        shareCard(provider, card, SharedObject.Action.DELETE);
        replyWithObject(provider, response, card);
    }

    @Action("ArchiveCard") public void archiveCard(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity(), ObjectState.Active, ObjectState.Archived, ObjectState.Invisible);
        card.archive();
        objectCache.save(card);
        StatusModel status = card.getStatus();
        LogModel log = new LogModel(card, status, status, "Archive");
        objectCache.save(log);
        shareCard(provider, card, SharedObject.Action.ARCHIVE);
        replyWithObject(provider, response, card);
    }

    @Action("UnArchiveCard") public void unArchiveCard(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity(), ObjectState.Archived, ObjectState.Active);
        card.activate();
        objectCache.save(card);
        StatusModel status = card.getStatus();
        LogModel log = new LogModel(card, status, status, "Update");
        objectCache.save(log);
        shareCard(provider, card, SharedObject.Action.UPDATE);
        replyWithObject(provider, response, card);
    }

    @Action("ShowCard") public void showCard(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        viewRegister.get("ShowCard").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }

    @Action("CreateCard") public void createCard(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        CardModel card = new CardModel().defaultSetup(objectCache);
        QueryKeyViolations violations = new BasicValuesProviderBinder().bind(provider, card, objectCache);
        String cardIdentity = provider.getString("cardIdentity");
        String cardTitle = provider.getString("cardTitle");
        if(!violations.isRegistered()) {
            objectCache.save(card);
            StatusModel backlog = new StatusService().backlog(objectCache);
            LogModel log = new LogModel(card, backlog, card.getStatus(), "Create");
            objectCache.save(log);
            shareCard(provider, card, SharedObject.Action.CREATE);
            String url = new UrlBuilder(provider.getApplicationRoot()).show(getKey(), cardIdentity, cardTitle);
            viewRegister.get("Done").renderTo(objectCache, provider, response, new SingleValueContext("redirect", url), violations);
        } else {
            viewRegister.get("AddCard").renderTo(objectCache, provider, response, new EmptyContext(), violations);
        }
    }

    @Action("UpdateCard") public void updateCard(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity(), ObjectState.Active, ObjectState.Archived, ObjectState.Invisible);
        StatusModel fromStatus = card.getStatus();
        QueryKeyViolations violations = new BasicValuesProviderBinder().bind(provider, card, objectCache);
        String cardIdentity = provider.getString("cardIdentity");
        String cardTitle = provider.getString("cardTitle");
        if(!violations.isRegistered()) {
            objectCache.save(card);
            StatusModel toStatus = card.getStatus();
            LogModel log = new LogModel(card, fromStatus, toStatus, "Update");
            objectCache.save(log);
            shareCard(provider, card, SharedObject.Action.UPDATE);
            String url = new UrlBuilder(provider.getApplicationRoot()).show(getKey(), cardIdentity, cardTitle);
            viewRegister.get("Done").renderTo(objectCache, provider, response, new SingleValueContext("redirect", url), violations);
        } else {
            viewRegister.get("EditCard").renderTo(objectCache, provider, response, new EmptyContext(), violations);
        }
    }





    

    @Action("AddPostIt") public void addPostIt(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        viewRegister.get("AddPostIt").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }

    @Action("EditPostIt") public void editPostIt(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        viewRegister.get("EditPostIt").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }

    @Action("DeletePostIt") public void deletePostIt(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        PostItModel postIt = objectCache.retrieveByIdentity(PostItModel.class, provider.getIdentity());
        StoryBoardModel storyBoard = objectCache.retrieveByIdentity(StoryBoardModel.class, provider.getString("boardIdentity"));
        storyBoard.removePostIt(postIt);
        objectCache.delete(postIt);
        objectCache.save(storyBoard);
        sharePostIt(provider, postIt, SharedObject.Action.DELETE);
        replyWithObject(provider, response, postIt);
    }

    @Action("ShowPostIt") public void showPostIt(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        viewRegister.get("ShowPostIt").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }

    @Action("CreatePostIt") public void createPostIt(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        PostItModel postIt = new PostItModel().defaultSetup(objectCache);
        QueryKeyViolations violations = new BasicValuesProviderBinder().bind(provider, postIt, objectCache);
        String cardIdentity = provider.getString("cardIdentity");
        String cardTitle = provider.getString("cardTitle");
        if(!violations.isRegistered()) {
            CardModel card = objectCache.retrieveByIdentity(CardModel.class, cardIdentity);
            card.getStoryBoard().addPostIt(postIt);
            objectCache.save(postIt);
            objectCache.save(card);
            sharePostIt(provider, postIt, SharedObject.Action.CREATE);
            String url = new UrlBuilder(provider.getApplicationRoot()).show(getKey(), cardIdentity, cardTitle);
            viewRegister.get("Done").renderTo(objectCache, provider, response, new SingleValueContext("redirect", url), violations);
        } else {
            viewRegister.get("AddPostIt").renderTo(objectCache, provider, response, new EmptyContext(), violations);
        }
    }

    @Action("UpdatePostIt") public void updatePostIt(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws IOException {
        PostItModel postIt = objectCache.retrieveByIdentity(PostItModel.class, provider.getIdentity());
        QueryKeyViolations violations = new BasicValuesProviderBinder().bind(provider, postIt, objectCache);
        String cardIdentity = provider.getString("cardIdentity");
        String cardTitle = provider.getString("cardTitle");
        if(!violations.isRegistered()) {
            objectCache.save(postIt);
            sharePostIt(provider, postIt, SharedObject.Action.UPDATE);
            String url = new UrlBuilder(provider.getApplicationRoot()).show(getKey(), cardIdentity, cardTitle);
            viewRegister.get("Done").renderTo(objectCache, provider, response, new SingleValueContext("redirect", url), violations);
        } else {
            viewRegister.get("EditPostIt").renderTo(objectCache, provider, response, new EmptyContext(), violations);
        }
    }

    @Action("MovePostIt") public void movePostIt(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws Exception {
		PostItModel postIt = objectCache.retrieveByIdentity(PostItModel.class, provider.getIdentity());
		new BasicValuesProviderBinder().bind(provider, postIt, objectCache);
		objectCache.save(postIt);
        sharePostIt(provider, postIt, SharedObject.Action.UPDATE);
        replyWithObject(provider, response, postIt);
	}







    @Action("Update") public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws Exception {
        StoryBoardModel storyBoard = objectCache.retrieveByIdentity(StoryBoardModel.class, provider.getIdentity());
        storyBoard.clear(objectCache);
        new BasicValuesProviderBinder().bind(provider, storyBoard, objectCache);
        objectCache.save(storyBoard);
        CardModel card = storyBoard.getCard();
        LogModel log = new LogModel(card, card.getStatus(), card.getStatus(), "Update");
        objectCache.save(log);
        shareCard(provider, card, SharedObject.Action.UPDATE);
        new JsonView(Collections.singletonMap("updated", true)).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
    }

    @Action("Feed") public void feed(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws Exception {
        String clientIdentity = provider.getString("clientIdentity");
        SharedObjectSpaceClient client = SharedObjectSpacesListener.reAttachClient(clientIdentity);
        Map<String,Collection<SharedObject>> map = new MapBuilder<String, Collection<SharedObject>>().put("cards", client.getSharedObjects(CardModel.class)).put("postIts", client.getSharedObjects(PostItModel.class)).build();
        response.setContentType(ContentType.json.toString());
        new TextStringView(SerializeModel.modelsToValidJson(map)).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
    }

    @Action("Build") public void build(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws Exception {
		viewRegister.get("StoryBoardBuild").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}
	
	@Action("Show") public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws Exception {
        CardModel card = new CardService().getCardOrDefault(objectCache, provider.getIdentity());
        StoryBoardModel board = card.getStoryBoard();
        getClient(provider).getSharedObjects(CardModel.class);
        if(board.isBlank()) {
            new StoryBoardService().modifyStoryBoard(StoryBoardTemplate.ModifyTo.KanBanTemplate, objectCache, board);
            objectCache.save(board);
        }
        viewRegister.get("StoryBoardShow").renderTo(objectCache, provider, response, new SingleValueContext("card", card), new QueryKeyViolations());
    }

	@Action(value="Help", requiresObjectCache=false) public void help(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewRegister) throws Exception {
		viewRegister.get("StoryBoardHelp").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}

    private void sharePostIt(ValuesProvider provider, PostItModel postIt, SharedObject.Action action) {
        SharedObjectSpaceClient client = getClient(provider);
        String serializedPostIt = SerializeModel.postIt(postIt);
        client.shareObject(PostItModel.class, new SharedObject(action, serializedPostIt));
    }

    private void shareCard(ValuesProvider provider, CardModel card, SharedObject.Action action) {
        SharedObjectSpaceClient client = getClient(provider);
        String serializedCard = SerializeModel.card(card);
        client.shareObject(CardModel.class, new SharedObject(action, serializedCard));
    }

    private SharedObjectSpaceClient getClient(ValuesProvider provider) {
        return SharedObjectSpacesListener.reAttachClient(provider.getString("clientIdentity"));
    }

    private void replyWithObject(ValuesProvider provider, HttpServletResponse response, IdentityDeleteable<?> model) {
        new JsonView(model).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
    }
}