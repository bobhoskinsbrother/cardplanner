package uk.co.itstherules.cardplanner.controller.active;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.model.BurnUpLineModel;
import uk.co.itstherules.cardplanner.model.BurnUpModel;
import uk.co.itstherules.cardplanner.model.BurnUpService;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelView;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.json.JsonView;

public class BurnUp extends BaseController {

    @Action(value="Create") public void create(ObjectCache cache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
        final BurnUpModel burnUp = new BurnUpModel();
        change(cache, burnUp, viewFactory.get("AddBurnUp"), provider, response, viewFactory);
    }

    @Action(value="Update") public void update(ObjectCache cache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
        final BurnUpModel burnUp = cache.retrieveByIdentity(BurnUpModel.class,  provider.getIdentity());
        change(cache, burnUp, viewFactory.get("EditBurnUp"), provider, response, viewFactory);
    }

    @Action(value="Add") public void add(ObjectCache cache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
        render(viewFactory.get("AddBurnUp"), cache, provider, response, new QueryKeyViolations());
    }

    @Action(value="Edit") public void edit(ObjectCache cache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
        render(viewFactory.get("EditBurnUp"), cache, provider, response, new QueryKeyViolations());
    }

    @Action(value="Feed") public void feed(ObjectCache cache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
        BurnUpModel burnUp = cache.retrieveByIdentity(BurnUpModel.class, provider.getIdentity());
        final BurnUpLineModel burnUpLine = new BurnUpService().betweenDatesForStatus(cache, burnUp.getFrom(), burnUp.getTo(), burnUp.getStatus());
        new JsonView(burnUpLine, "line").renderTo(new EmptyContext(), response, "");
    }

    private void change(ObjectCache cache, BurnUpModel burnUp, ModelView view, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
        final QueryKeyViolations violations = new BasicValuesProviderBinder().bind(provider, burnUp, cache);
        if(violations.isRegistered()) {
            render(view, cache, provider, response, violations);
        } else {
            cache.save(burnUp);
            render(viewFactory.get("AddBurnUp"), cache, provider, response, new QueryKeyViolations());
        }
    }

    private void render(ModelView view, ObjectCache cache, ValuesProvider provider, HttpServletResponse response, QueryKeyViolations violations) {
        view.renderTo(cache, provider, response, new EmptyContext(), violations);
    }

}