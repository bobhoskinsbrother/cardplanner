package uk.co.itstherules.yawf.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public class TestBaseImpl extends BaseController {
	public void add(ObjectCache objectCache, ValuesProvider provider,
	        HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void create(ObjectCache objectCache, ValuesProvider provider,
	        HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void list(ObjectCache objectCache, ValuesProvider provider,
	        HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void show(ObjectCache objectCache, ValuesProvider provider,
	        HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void edit(ObjectCache objectCache, ValuesProvider provider,
	        HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void update(ObjectCache objectCache, ValuesProvider provider,
	        HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void delete(ObjectCache objectCache, ValuesProvider provider,
	        HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean requiresObjectCache() {
		throw new RuntimeException("TODO: Not yet implemented");
	}
}
