package uk.co.itstherules.yawf.view.helper;

import freemarker.template.TemplateHashModelEx;
import uk.co.itstherules.yawf.model.Entity;

import java.util.Collection;
import java.util.List;

public interface TagBuilder {
	String addLink(String controller, String title);

	String addLink(String controller, String title, String name);

	String addLink(String controller, String title, String name,
	        TemplateHashModelEx queryStringValues,
	        TemplateHashModelEx attributes);

	
	String addUrl(String controller, String title);

	String addUrl(String controller, String title,
	        TemplateHashModelEx queryStringValues);

	String button(TemplateHashModelEx hash, String value);

	String checkbox(TemplateHashModelEx hash, String value, boolean isError,
	        String errorMessage);

	String createUrl(String controller, String title);

	String deleteButton(String controller,
			Entity<?> entity);

	String deleteButton(String controller, Entity<?> entity, TemplateHashModelEx queryStringValues);

	String deleteLink(String controller, String title);

	String deleteLink(String controller, String title, String name);

	String deleteLink(String controller, String title, String identity, String name);

	String deleteLink(String controller, String title, String identity, String name, TemplateHashModelEx querystring);

	String deleteUrl(String controller, String title);

	String deleteUrl(String controller, String identity, String title);
	
	String deleteUrl(String controller, String identity, String title,
	        TemplateHashModelEx hash);

	String editLink(String controller, String title);

	String editLink(String controller, String title, String name);

	String editLink(String controller, String title, String identity,
	        String name);

	String editLink(String controller, String title, String identity,
	        String name, TemplateHashModelEx querystringValues);

	String editLink(String controller, String title, String identity,
	        String name, TemplateHashModelEx queryStringValues,
	        TemplateHashModelEx attributes);

	String editUrl(String controller, String title);

	String editUrl(String controller, String identity, String title);

	String editUrl(String controller, String identity, String title,
	        TemplateHashModelEx queryStringValues);

	String feedLink(String controller, String title, String identity,
	        String name);

	String feedUrl(String controller, String identity, String title);

	String fileUpload(TemplateHashModelEx hash, String value, boolean isError,
	        String errorMessage);

	String form(String identity, String url, String form);

	String formEnd();

	String formStart(String identity, String url);

	String formStart(String formIdentity, String controller, String identity,
	        String action);

	String formStart(String formIdentity, String controller, String identity,
	        String title, String action);

	String hidden(TemplateHashModelEx hash, String value);

	String inputText(TemplateHashModelEx hash, String value, boolean isError,
	        List<String> errorMessages);

	String inputText(TemplateHashModelEx hash, String value, boolean isError,
	        String errorMessage);

	String inputText(TemplateHashModelEx hash, String value,
	        List<String> errorMessages);

	String link(String url, String title);

	String link(String url, String title, TemplateHashModelEx hash);

    String link(String controller, String action, String identity, String title, TemplateHashModelEx attributes);

	String listLink(String controller, String title);

	String listLink(String controller, String title, String name);

	String listLink(String controller, String title, String identity,
	        String name);

	String listLink(String controller, String title, String identity,
	        String name, TemplateHashModelEx queryStringHash);

	String listLink(String controller, String title, String identity,
	        String name, TemplateHashModelEx queryStringHash,
	        TemplateHashModelEx attributesHash);

	String listUrl(String controller, String title);

	String listUrl(String controller, String identity, String title);

	String listUrl(String controller, String identity, String title,
	        TemplateHashModelEx queryStringHash);

	String passwordText(TemplateHashModelEx hash, String value,
	        boolean isError, List<String> errorMessage);

	String passwordText(TemplateHashModelEx hash, String value,
	        boolean isError, String errorMessage);

	String passwordText(TemplateHashModelEx hash, String value,
	        List<String> errorMessage);

	String popOverButton(String identity, String url, int width, int height,
	        String title, String buttonImage);

	String select(String name, Collection<? extends Entity<?>> cards, String selected,
	        TemplateHashModelEx hash);

	String select(String name, Collection<? extends Entity<?>> cards, String selected,
	        TemplateHashModelEx hash, TemplateHashModelEx additionalCards);

	String showCss(String title);

	String showImage(String title);

	String showImage(String controller, String identity, String title);

	String showImage(String controller, String identity, String title,
	        TemplateHashModelEx hash);

	String showImage(String controller, String title, TemplateHashModelEx hash);

	String showImage(String title, TemplateHashModelEx hash);

	String showImageUrl(String title);

	String showLink(String controller, String title);

	String showLink(String controller, String title, String name);

	String showLink(String controller, String title, String identity,
	        String name);

	String showLink(String controller, String title, String identity,
	        String name, TemplateHashModelEx queryStringHash);

	String showLink(String controller, String title, String identity,
	        String name, TemplateHashModelEx queryStringHash,
	        TemplateHashModelEx attributesHash);

	String showScript(String title);

    String loadScripts(Collection<String> scripts);

    String loadCss(Collection<String> files);

	String showUrl(String controller, String title);

	String showUrl(String controller, String identity, String title);

	String showUrl(String controller, String identity, String title,
	        TemplateHashModelEx hash);

	String showUrl(String controller, String title, TemplateHashModelEx hash);

	String submit(String value);

	String submitImage(String image);
	
	String submitImage(String image, TemplateHashModelEx hash);

	String textArea(TemplateHashModelEx hash, String value, boolean isError,
	        String errorMessage);

	String tipAttribute(String message);

	String updateUrl(String controller, String title);

	String updateUrl(String controller, String identity, String title);

	String url(String controller, String action, String identity, String title);

	String url(String controller, String action, String identity, String title,
	        String queryString);

	String url(String controller, String action, String identity, String title,
	        TemplateHashModelEx queryStringHash);

}