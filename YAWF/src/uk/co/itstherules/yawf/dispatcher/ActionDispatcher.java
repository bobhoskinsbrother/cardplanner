package uk.co.itstherules.yawf.dispatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.controller.interfaces.Controller;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.AlwaysThrowsObjectCache;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.ObjectCacheFactory;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;


public class ActionDispatcher {
	
	public void dispatch(Controller controller, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
		String action = provider.getAction();
        Method method = findMethodOnControllerForAction(controller, action);
        dispatchMethod(controller, method, provider, response, viewFactory);
	}

    private void dispatchMethod(Controller controller, Method method, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IllegalAccessException, InvocationTargetException {
        method.setAccessible(true);
        if(method.getAnnotation(Action.class).requiresObjectCache()) {
            dispatchWithObjectCache(controller, method, provider, response, viewFactory);
        } else {
            dispatchInternally(controller, method, new AlwaysThrowsObjectCache(), provider, response, viewFactory);
        }
    }

    private void dispatchInternally(Controller controller, Method method, ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IllegalAccessException, InvocationTargetException {
        method.invoke(controller, objectCache, provider, response, viewFactory);
    }

    private void dispatchWithObjectCache(Controller controller, Method method, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IllegalAccessException, InvocationTargetException {
        ObjectCache objectCache = ObjectCacheFactory.get();
        dispatchInternally(controller, method, objectCache, provider, response, viewFactory);
        objectCache.close();
    }

    private Method findMethodOnControllerForAction(Controller controller, String action) throws ServletException {
        Method[] methods = controller.getClass().getMethods();
        for (Method m : methods) {
            if(m.isAnnotationPresent(Action.class) && m.getAnnotation(Action.class).value().equals(action)) {
                return m;
            }
        }
        throw new ServletException("Unable to find action: " + action + " on controller: "+controller.getClass().getCanonicalName());
    }
	
}