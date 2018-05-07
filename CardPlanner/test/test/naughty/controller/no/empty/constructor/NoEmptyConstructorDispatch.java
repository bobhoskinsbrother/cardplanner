package test.naughty.controller.no.empty.constructor;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.controller.interfaces.Crudd;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public class NoEmptyConstructorDispatch implements Crudd {
	
	public NoEmptyConstructorDispatch(String nonEmptyConstructor) { }
	
	public void control(ValuesProvider provider,
	        HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getTitle() {
		return "NoEmptyConstructorDispatch";
	}

	public void create(ObjectCache objectCache, ValuesProvider provider,
            HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    }

	public void add(ObjectCache objectCache, ValuesProvider provider,
            HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    }

	public void show(ObjectCache objectCache, ValuesProvider provider,
            HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    }

	public void list(ObjectCache objectCache, ValuesProvider provider,
            HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    }

	public void update(ObjectCache objectCache, ValuesProvider provider,
            HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    }

	public void edit(ObjectCache objectCache, ValuesProvider provider,
            HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    }

	public void delete(ObjectCache objectCache, ValuesProvider provider,
            HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    }

	public void feed(ObjectCache objectCache, ValuesProvider provider,
            HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    }
	@Override public String getKey() { return getTitle(); }
}
