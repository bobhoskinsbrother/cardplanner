package uk.co.itstherules.yawf.view.helper;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.itstherules.string.manipulation.UrlEncoder;
import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.view.xhtml.QueryStringBuilder;

public final class UrlBuilder {
	
	private static final String ZERO = "0";
	private final String root;
	public UrlBuilder(String root) { this.root = root; }

	
	public String add(String controller, String title) {
		return url(controller, "Add", ZERO, title);
	}
	
	public String add(String controller, String title, Collection<Entity<?>> queryStringValues) {
		return url(controller, "Add", ZERO, title, queryStringValues);
	}
	
	public String create(String controller, String title) {
		return url(controller, "Create", ZERO, title);
	}

	public String delete(String controller, String title) {
		return url(controller, "Delete", ZERO, title);
	}
	
	public String delete(String controller, String identity, String title) {
		return url(controller, "Delete", identity, title);
	}
	
	public String delete(String controller, String identity, String title, Collection<Entity<?>> hash) {
		return url(controller, "Delete", identity, title, hash);
	}

	public String edit(String controller, String title) {
		return url(controller, "Edit", ZERO, title);
	}

	public String edit(String controller, String identity, String title) {
		return url(controller, "Edit", identity, title);
	}

	public String edit(String controller, String identity, String title, Collection<Entity<?>> queryStringValues) {
		return url(controller, "Edit", identity, title, queryStringValues);
	}

	public String feed(String controller, String identity, String title) {
		return url(controller, "Feed", identity, title);
	}

	public String list(String controller, String title) {
		return url(controller, "List", ZERO, title);
	}

	public String list(String controller, String identity, String title) {
		return url(controller, "List", identity, title);
	}

	public String list(String controller, String identity, String title, Collection<Entity<?>> queryStringHash) {
		return url(controller, "List", identity, title, queryStringHash);
	}

	public String image(String title) {
		return show("Images", ZERO, title);
	}

	public String show(String controller, String title) {
		return url(controller, "Show", ZERO, title);
	}

	public String show(String controller, String identity, String title) {
		return url(controller, "Show", identity, title);
	}

	public String show(String controller, String identity, String title, Collection<Entity<?>> hash) {
		return url(controller, "Show", identity, title, hash);
	}

	public String show(String controller, String title, Collection<Entity<?>> hash) {
		return url(controller, "Show", ZERO, title, hash);
	}

	public String update(String controller, String title) {
		return url(controller, "Update", ZERO, title);
	}

	public String update(String controller, String identity, String title) {
		return url(controller, "Update", identity, title);
	}
	
	public String url(String controller, String action, String identity, String title) {
		return url(controller, action, identity, title, new ArrayList<Entity<?>>());
	}

	public String url(String controller, String action, String identity, String title, String queryString) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(root);
		buffer.append("/");
		buffer.append(controller);
		buffer.append("/");
		buffer.append(action);
		buffer.append("/");
		buffer.append(identity);
		buffer.append("/");
		buffer.append(new UrlEncoder().manipulate(title));
		buffer.append(queryString);
		return buffer.toString();
	}
	
	public String url(String controller, String action, String identity, String title, Collection<Entity<?>> queryStringHash) {
		String queryString = "";
		if(queryStringHash != null && !queryStringHash.isEmpty()) { 
			queryString = new QueryStringBuilder().build(queryStringHash); 
		} 
		return url(controller, action, identity, title, queryString);
	}
		
}