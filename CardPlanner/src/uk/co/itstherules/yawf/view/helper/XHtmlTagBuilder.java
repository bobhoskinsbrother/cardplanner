package uk.co.itstherules.yawf.view.helper;

import freemarker.template.*;
import uk.co.itstherules.string.manipulation.CollectionPrinter;
import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.view.xhtml.AttributesBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;

public class XHtmlTagBuilder implements TagBuilder {
	
	private enum InputType { Button, Checkbox, File, Hidden, Password, Submit, Text, Image }
	private final TemplateHashConverter hashConverter;
	private final UrlBuilder url;
	
	public XHtmlTagBuilder(String root) { 
		this.url = new UrlBuilder(root);
		this.hashConverter = new TemplateHashConverter();
	}
	
	
	public String addLink(String controller, String title) {
		return link(url.add(controller, title), "Add " + controller);
	}

	public String addLink(String controller, String title, String name) {
		return link(url.add(controller, title), name);
	}
	
	public String addLink(String controller, String title, String name, TemplateHashModelEx queryStringValues, TemplateHashModelEx attributes) {
		return link(url.add(controller, title, hashConverter.convert(queryStringValues)), name, attributes);
	}
	
	public String addUrl(String controller, String title) {
		return url.add(controller, title);
	}
	
	public String addUrl(String controller, String title, TemplateHashModelEx queryStringValues) {
		return url.add(controller, title, hashConverter.convert(queryStringValues));
	}
	
	public String button(TemplateHashModelEx hash, String value) {
		return input(hash, InputType.Button, value, false, new ArrayList<String>());
    }
	
	public String checkbox(TemplateHashModelEx hash, String value, boolean isError, String errorMessage) {
		return input(hash, InputType.Checkbox, value, isError, Collections.singletonList(errorMessage));
	}
	
	public String createUrl(String controller, String title) {
		return url.create(controller, title);
	}
	
	private String delete(String url, String title, String text) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<a name=\"deleteButton\" href=\"javascript:TemplateController.confirmDeletion('");
		try {
			buffer.append(URLEncoder.encode(title, "UTF8"));
			buffer.append("','");
	        buffer.append(URLEncoder.encode(url, "UTF8"));
        } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException(e);
        }
		buffer.append("');\">");
		buffer.append(text);
		buffer.append("</a>");
		return buffer.toString();

    }
	
	public String deleteButton(String controller, Entity<?> identifiableDeleteable) {
		return deleteButton(controller, identifiableDeleteable, new SimpleHash());
	}

	public String deleteButton(String controller, Entity<?> identifiableDeleteable, TemplateHashModelEx queryStringValues) {
	    return delete(url.delete(controller, identifiableDeleteable.getIdentity(), identifiableDeleteable.getTitle(), hashConverter.convert(queryStringValues)), identifiableDeleteable.getTitle(), deleteImage(identifiableDeleteable.getTitle(), "delete_button.png"));
	}
	
	private String deleteImage(String title, String image) {
        final LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("alt", "Delete " + title);
		map.put("border", "0");
		map.put("title", "Delete " + title);
        SimpleHash hash = new SimpleHash(map);
		return this.showImage(image, hash);
	}

	private String deleteImage(String title) {
		return deleteImage(title, "delete.png");
	}
	
	public String deleteLink(String controller, String title) {
	    return delete(url.delete(controller, title), title, deleteImage(title));
	}

	public String deleteLink(String controller, String title, String name) {
	    return delete(url.delete(controller, title), name, deleteImage(title));
	}

	public String deleteLink(String controller, String title, String identity, String name) {
		return delete(url.delete(controller, identity, title), name, deleteImage(title));
	}

	public String deleteLink(String controller, String title, String identity, String name, TemplateHashModelEx querystring) {
	    return delete(url.delete(controller, identity, title, hashConverter.convert(querystring)), name, deleteImage(title));
	}

	public String deleteUrl(String controller, String title) {
		return url.delete(controller, title);
	}

	public String deleteUrl(String controller, String identity, String title) {
		return url.delete(controller, identity, title);
	}

	public String deleteUrl(String controller, String identity, String title, TemplateHashModelEx hash) {
		return url.delete(controller, identity, title, hashConverter.convert(hash));
	}

	public String editLink(String controller, String title) {
		return link(url.edit(controller, title), "Edit " + controller);
	}

	public String editLink(String controller, String title, String name) {
		return link(url.edit(controller, title), name);
	}
	
	public String editLink(String controller, String title, String identity, String name) {
		return link(url.edit(controller, identity, title), name);
	}
	
	public String editLink(String controller, String title, String identity, String name, TemplateHashModelEx querystringValues) {
		return link(url.edit(controller, identity, title, hashConverter.convert(querystringValues)), name);
	}

	public String editLink(String controller, String title, String identity, String name, TemplateHashModelEx queryStringValues, TemplateHashModelEx attributes) {
		return link(url.edit(controller, identity, title, hashConverter.convert(queryStringValues)), name, attributes);
	}

	public String editUrl(String controller, String title) {
		return url.edit(controller, title);
	}

	public String editUrl(String controller, String identity, String title) {
		return url.edit(controller, identity, title);
	}

	public String editUrl(String controller, String identity, String title, TemplateHashModelEx queryStringValues) {
		return url.edit(controller, identity, title, hashConverter.convert(queryStringValues));
	}

	public String feedLink(String controller, String title, String identity, String name) {
		return link(url.feed(controller, identity, title), name);
	}

	public String feedUrl(String controller, String identity, String title) {
		return url.feed(controller, identity, title);
	}

	public String fileUpload(TemplateHashModelEx hash, String value, boolean isError, String errorMessage) {
		return input(hash, InputType.File, value, isError, Collections.singletonList(errorMessage));
    }

	public String form(String identity, String url, String form) {
		StringBuilder builder = new StringBuilder(formStart(identity, url));
		builder.append(form);
		builder.append(formEnd());
		return builder.toString();
	}

	public String formEnd(){
		return "</form>";
	}

	public String formStart(String identity, String url) {
		return new StringBuilder("<form method=\"post\" action=\"").append(url).append("\" id=\"").append(identity).append("\">").toString();
	}

	public String formStart(String formIdentity, String controller, String identity, String action) {
		return formStart(formIdentity, controller, identity, "index.xhtml", action);
	}

	public String formStart(String formIdentity, String controller, String identity, String title, String action) {
		String urlString = url.url(controller, action, identity, title);
		return formStart(formIdentity, urlString);
	}
	
	public String hidden(TemplateHashModelEx hash, String value) {
		return input(hash, InputType.Hidden, value, false, new ArrayList<String>());
	}

	private String input(TemplateHashModelEx hash, InputType inputType, String value, boolean isError, List<String> errorMessages) {
		StringBuilder buffer = new StringBuilder();
		buffer = buffer.append("<input type=\"").append(inputType.name().toLowerCase()).append("\" ").append(new AttributesBuilder().build(hashConverter.convert(hash), isError));
		buffer = buffer.append("value=\"").append(value).append("\"");
		buffer.append(" />");
		if(isError) {
			buffer = buffer.append("<div class=\"errorMessageText\">");
			for (String errorMessage : errorMessages) {
				buffer = buffer.append(errorMessage);
				buffer = buffer.append("<br/>");
			} 
			buffer = buffer.append("</div>");
	            
		}
		return buffer.toString();
	}
	
	public String inputText(TemplateHashModelEx hash, String value, boolean isError, List<String> errorMessages) {
		return input(hash, InputType.Text, value, isError, errorMessages);
	}
	
	public String inputText(TemplateHashModelEx hash, String value, boolean isError, String errorMessage) {
		return input(hash, InputType.Text, value, isError, Collections.singletonList(errorMessage));
	}
	public String inputText(TemplateHashModelEx hash, String value, List<String> errorMessages) {
		return inputText(hash, value, (errorMessages.size()>0), errorMessages);
	}
	
	public String link(String url, String title) {
		return link(url, title, new SimpleHash());
	}

	public String link(String url, String title, TemplateHashModelEx attributes) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<a href=\"");
		buffer.append(url);
		buffer.append("\" ");
		buffer.append(new AttributesBuilder().build(hashConverter.convert(attributes, "href")));
		buffer.append(">");
		buffer.append(title);
		buffer.append("</a>");
		return buffer.toString();
	}

	public String link(String controller, String action, String title, String identity, String name, TemplateHashModelEx queryStringHash) {
		return link(url.url(controller, action, identity, title, hashConverter.convert(queryStringHash)), name);
	}

	public String link(String controller, String action, String identity, String title, TemplateHashModelEx attributes) {
		return link(url.url(controller, action, identity, title), title, attributes);
	}

	public String listLink(String controller, String title) {
		return link(url.list(controller, title), "List " + controller);
	}

	public String listLink(String controller, String title, String name) {
		return link(url.list(controller, title), name);
	}

	public String listLink(String controller, String title, String identity, String name) {
		return link(url.list(controller, identity, title), name);
	}

	public String listLink(String controller, String title, String identity, String name, TemplateHashModelEx queryStringHash) {
		return link(url.list(controller, identity, title, hashConverter.convert(queryStringHash)), name);
	}

	public String listLink(String controller, String title, String identity, String name, TemplateHashModelEx queryStringHash, TemplateHashModelEx attributesHash) {
		return link(url.list(controller, identity, title, hashConverter.convert(queryStringHash)), name, attributesHash);
	}

	public String listUrl(String controller, String title) {
		return url.list(controller, title);
	}
	
	public String listUrl(String controller, String identity, String title) {
		return url.list(controller, identity, title);
	}

	public String listUrl(String controller, String identity, String title, TemplateHashModelEx queryStringHash) {
		return url.list(controller, identity, title, hashConverter.convert(queryStringHash));
	}

	public String passwordText(TemplateHashModelEx hash, String value, boolean isError, List<String> errorMessage) {
		return input(hash, InputType.Password, value, isError, errorMessage);
	}

	public String passwordText(TemplateHashModelEx hash, String value, boolean isError, String errorMessage) {
		return passwordText(hash, value, isError, Collections.singletonList(errorMessage));
	}

	public String passwordText(TemplateHashModelEx hash, String value, List<String> errorMessage) {
		return input(hash, InputType.Password, value, (errorMessage.size()>0), errorMessage);
	}

	public String popOverButton(String identity, String url, int width, int height, String title, String buttonImage) {
		StringBuilder builder = new StringBuilder();
		builder.append("<a id=\"");
		builder.append(identity);
		builder.append("\" href=\"");
		builder.append(url);
		builder.append("\" class=\"lightwindow\" params=\"lightwindow_type=external,lightwindow_width=");
		builder.append(width);
		builder.append(",lightwindow_height=");
		builder.append(height);
		builder.append("\" title=\"");
		builder.append(title);
		builder.append("\">");
		SimpleHash hash = new SimpleHash(Collections.singletonMap("style", "border: 0px none #FFFFFF;"));
		builder.append(showImage(buttonImage, hash));
		builder.append("</a>");
		return builder.toString();
	}
	
	public String select(String name, Collection<? extends Entity<?>> cards, String selected, TemplateHashModelEx hash) {
		Assertion.checkNotNull(name, "Name cannot be null");
		Assertion.checkNotNull(selected, "Selected value cannot be null");
		StringBuilder buffer = new StringBuilder();
		buffer.append("<select name=\"");
		buffer.append(name);
		buffer.append("\" ");
		buffer.append(new AttributesBuilder().build(hashConverter.convert(hash)));
		buffer.append(">");
		for (Entity<?> card : cards) {
			buffer.append("<option value=\"");
			buffer.append(card.getIdentity());
			buffer.append("\" ");
			if(card.getIdentity().equals(selected)) {
				buffer.append("selected=\"selected\"");
			}
			buffer.append(">");
			buffer.append(card.getTitle());
			buffer.append("</option>");
		}
		buffer.append("</select>");
		return buffer.toString();
	}
	
	public String select(String name, Collection<? extends Entity<?>> cards, String selected, TemplateHashModelEx hash, TemplateHashModelEx additionalCards) {
		Collection<Entity<?>> converted = hashConverter.convert(additionalCards);
		converted.addAll(cards);
		return select(name, converted, selected, hash);
	}

	public String showCss(String title) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<link href=\"");
		buffer.append(url.show("Css", title));
		buffer.append("\" rel=\"stylesheet\" type=\"text/css\" media=\"print,screen\"/>");
		return buffer.toString();
	}

	public String showImage(String title) {
		SimpleHash hash = new SimpleHash();
		hash.put("alt", "");
		return showImage(title, hash);
	}

	public String showImage(String controller, String identity, String title) {
		SimpleHash hash = new SimpleHash();
		hash.put("alt", "");
		return showImage(controller, identity, title, hash);
	}

	public String showImage(String controller, String identity, String title, TemplateHashModelEx attributes) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<img src=\"");
		buffer.append(url.show(controller, identity, title));
		buffer.append("\" ");
		buffer.append(new AttributesBuilder().build(hashConverter.convert(attributes)));
		buffer.append(" />");
		return buffer.toString();
	}

	public String showImage(String controller, String title, TemplateHashModelEx attributes) {
		return showImage(controller, "0", title, attributes);
	}

	public String showImage(String title, TemplateHashModelEx attributes) {
		return showImage("Images", title, attributes);
	}
	
	public String showImageUrl(String title) {
		return url.image(title);
	}
	
	public String showLink(String controller, String title) {
		return link(url.show(controller, title), "Show " + controller);
	}

	public String showLink(String controller, String title, String name) {
		return link(url.show(controller, title), name);
	}

	public String showLink(String controller, String title, String identity, String name) {
		return link(url.show(controller, identity, title), name);
	}

	public String showLink(String controller, String title, String identity, String name, TemplateHashModelEx queryStringHash) {
		return link(url.show(controller, identity, title, hashConverter.convert(queryStringHash)), name);
	}

	public String showLink(String controller, String title, String identity, String name, TemplateHashModelEx queryStringHash, TemplateHashModelEx attributesHash) {
		return link(url.show(controller, identity, title, hashConverter.convert(queryStringHash)), name, attributesHash);
	}

	public String showScript(String title) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<script type=\"text/javascript\" src=\"");
		buffer.append(url.show("JavaScript", title));
		buffer.append("\"></script>");
		return buffer.toString();
	}

	public String loadScripts(Collection<String> files) {
        String all = MessageFormat.format("?files={0}", CollectionPrinter.print(files));
        StringBuilder buffer = new StringBuilder();
        buffer.append("<script type=\"text/javascript\" src=\"");
        buffer.append(url.url("JavaScript", "Loader", "0", "all.js", all));
        buffer.append("&amp;uncompressed=true\"></script>");
        return buffer.toString();
    }

    public String loadCss(Collection<String> files) {
        String all = MessageFormat.format("?files={0}", CollectionPrinter.print(files));
        StringBuilder buffer = new StringBuilder();
        buffer.append("<link href=\"");
        buffer.append(url.url("Css", "Loader", "0", "all.css", all));
        buffer.append("\" rel=\"stylesheet\" type=\"text/css\" media=\"print,projector,screen\"/>");
        return buffer.toString();
    }
    
	public String showUrl(String controller, String title) {
		return url.show(controller, title);
	}

	public String showUrl(String controller, String identity, String title) {
		return url.show(controller, identity, title);
	}

	public String showUrl(String controller, String identity, String title, TemplateHashModelEx hash) {
		return url.show(controller, identity, title, hashConverter.convert(hash));
	}

	public String showUrl(String controller, String title, TemplateHashModelEx hash) {
		return url.show(controller, title, hashConverter.convert(hash));
	}

	public String submit(String value) {
		SimpleHash hash = new SimpleHash();
		hash.put("name", "completeAction");
		return input(hash, InputType.Submit, value, false, new ArrayList<String>());
    }

	public String submitImage(String value, TemplateHashModelEx hash) {
		SimpleHash newHash = new SimpleHash();
		
		TemplateCollectionModel keys;
        try {
	        keys = hash.keys();
	        for (TemplateModelIterator iterator = keys.iterator(); iterator.hasNext();) {
	        	TemplateModel model = iterator.next();
	            String key = model.toString();
	           	newHash.put(key, hash.get(key).toString());
	        }
        } catch (TemplateModelException e) {
	        throw new RuntimeException(e);
        }
		newHash.put("name", "completeAction");
		newHash.put("src", showImageUrl(value));
		
		return input(newHash, InputType.Image, value, false, new ArrayList<String>());
    }

	public String submitImage(String value) {
		return submitImage(value, new SimpleHash());
    }

	public String textArea(TemplateHashModelEx hash, String value, boolean isError, String errorMessage) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<textarea ");
		buffer.append(new AttributesBuilder().build(hashConverter.convert(hash), isError));
		buffer.append(" ");
		if(isError) {
			buffer.append("title=\"");
			buffer.append(errorMessage);
			buffer.append("\"");
		}
		buffer.append(">");
		buffer.append(value);
		buffer.append("</textarea>");
		return buffer.toString();
	}
	
	public String tipAttribute(String message) {
		return new StringBuilder().append("$(this).addTip(").append("Builder.node('div', {},'").append(message).append("')").append(", { target: true, stem: true, showOn: 'creation' });").toString();
	}

	public String updateUrl(String controller, String title) {
		return url.update(controller, title);
	}
	
	public String updateUrl(String controller, String identity, String title) {
		return url.update(controller, identity, title);
	}
	
	public String url(String controller, String action, String identity, String title) {
		return url.url(controller, action, identity, title);
	}

	public String url(String controller, String action, String identity, String title, String queryString) {
		return url.url(controller, action, identity, title, queryString);
	}

	public String url(String controller, String action, String identity, String title, TemplateHashModelEx queryStringHash) {
		return url.url(controller, action, identity, title, hashConverter.convert(queryStringHash));
	}

}