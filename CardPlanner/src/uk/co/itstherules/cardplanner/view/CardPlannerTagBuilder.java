package uk.co.itstherules.cardplanner.view;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateHashModelEx;
import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.view.helper.TagBuilder;
import uk.co.itstherules.yawf.view.helper.XHtmlTagBuilder;

import java.util.Collection;
import java.util.List;

public class CardPlannerTagBuilder implements TagBuilder {

    private TagBuilder delegate;

    public CardPlannerTagBuilder(String root) {
        this.delegate = new XHtmlTagBuilder(root);
    }

    public String addButton(String controller, String title) {
        return addButton(controller, title, "");
    }

    public String addButton(String controller, String title, String redirect) {
        SimpleHash queryString = new SimpleHash();
        if (!"".equals(redirect)) {
            queryString.put("redirect", redirect);
        }
        SimpleHash attributes = new SimpleHash();
        attributes.put("title", title);
        attributes.put("name", "addButton");
        attributes.put("class", "lightwindow");
        attributes.put("params", "lightwindow_type=external,lightwindow_width=740,lightwindow_height=560");
        return addLink(controller, "index.xhtml", showImage("add.png"), queryString, attributes);
    }

    public String addLink(String controller, String title) {
        return delegate.addLink(controller, title);
    }

    public String addLink(String controller, String title, String name) {
        return delegate.addLink(controller, title, name);
    }

    public String addLink(String controller, String title, String name, TemplateHashModelEx queryStringValues, TemplateHashModelEx attributes) {
        return delegate.addLink(controller, title, name, queryStringValues, attributes);
    }

    public String addUrl(String controller, String title) {
        return delegate.addUrl(controller, title);
    }

    public String addUrl(String controller, String title, TemplateHashModelEx queryStringValues) {
        return delegate.addUrl(controller, title, queryStringValues);
    }

    public String button(TemplateHashModelEx hash, String value) {
        return delegate.button(hash, value);
    }

    public String checkbox(TemplateHashModelEx hash, String value, boolean isError, String errorMessage) {
        return delegate.checkbox(hash, value, isError, errorMessage);
    }

    public String createUrl(String controller, String title) {
        return delegate.createUrl(controller, title);
    }

    public String deleteButton(String controller, Entity<?> identifiableDeleteable) {
        return delegate.deleteButton(controller, identifiableDeleteable);
    }

    public String deleteButton(String controller, Entity<?> identifiableDeleteable, TemplateHashModelEx queryStringValues) {
        return delegate.deleteButton(controller, identifiableDeleteable, queryStringValues);
    }

    public String deleteLink(String controller, String title) {
        return delegate.deleteLink(controller, title);
    }

    public String deleteLink(String controller, String title, String name) {
        return delegate.deleteLink(controller, title, name);
    }

    public String deleteLink(String controller, String title, String identity, String name) {
        return delegate.deleteLink(controller, title, identity, name);
    }

    public String deleteLink(String controller, String title, String identity, String name, TemplateHashModelEx hash) {
        return delegate.deleteLink(controller, title, identity, name, hash);
    }

    public String deleteUrl(String controller, String title) {
        return delegate.deleteUrl(controller, title);
    }

    public String deleteUrl(String controller, String identity, String title) {
        return delegate.deleteUrl(controller, identity, title);
    }

    public String deleteUrl(String controller, String identity, String title, TemplateHashModelEx hash) {
        return delegate.deleteUrl(controller, identity, title, hash);
    }

    public String editButton(String controller, String title, Entity<?> identifiableDeleteable) {
        SimpleHash hash = new SimpleHash();
        return editButton(controller, title, identifiableDeleteable, hash);
    }

    public String editButton(String controller, String title, Entity<?> identifiableDeleteable, TemplateHashModelEx queryStringValues) {
        SimpleHash hash = new SimpleHash();
        hash.put("title", title);
        hash.put("class", "lightwindow");
        hash.put("params", "lightwindow_type=external,lightwindow_width=740,lightwindow_height=540");
        return editLink(controller, identifiableDeleteable.getTitle(), identifiableDeleteable.getIdentity(), showImage("edit_button.png"), queryStringValues, hash);
    }

    public String editLink(String controller, String title) {
        return delegate.editLink(controller, title);
    }

    public String editLink(String controller, String title, String name) {
        return delegate.editLink(controller, title, name);
    }

    public String editLink(String controller, String title, String identity, String name) {
        return delegate.editLink(controller, title, identity, name);
    }

    public String editLink(String controller, String title, String identity, String name, TemplateHashModelEx hash) {
        return delegate.editLink(controller, title, identity, name, hash);
    }

    public String editLink(String controller, String title, String identity, String name, TemplateHashModelEx queryStringValues, TemplateHashModelEx attributes) {
        return delegate.editLink(controller, title, identity, name, queryStringValues, attributes);
    }

    public String editUrl(String controller, String title) {
        return delegate.editUrl(controller, title);
    }

    public String editUrl(String controller, String identity, String title) {
        return delegate.editUrl(controller, identity, title);
    }

    public String editUrl(String controller, String identity, String title, TemplateHashModelEx queryStringValues) {
        return delegate.editUrl(controller, identity, title, queryStringValues);
    }

    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    public String feedLink(String controller, String title, String identity, String name) {
        return delegate.feedLink(controller, title, identity, name);
    }

    public String feedUrl(String controller, String identity, String title) {
        return delegate.feedUrl(controller, identity, title);
    }

    public String atomUrl(String controller) {
        return delegate.atomUrl(controller);
    }

    public String fileUpload(TemplateHashModelEx hash, String value, boolean isError, String errorMessage) {
        return delegate.fileUpload(hash, value, isError, errorMessage);
    }

    public String form(String identity, String url, String form) {
        return delegate.form(identity, url, form);
    }

    public String formEnd() {
        return delegate.formEnd();
    }

    public String formStart(String identity, String url) {
        return delegate.formStart(identity, url);
    }

    public String formStart(String formIdentity, String controller, String identity, String action) {
        return delegate.formStart(formIdentity, controller, identity, action);
    }

    public String formStart(String formIdentity, String controller, String identity, String title, String action) {
        return delegate.formStart(formIdentity, controller, identity, title, action);
    }

    public String hidden(TemplateHashModelEx hash, String value) {
        return delegate.hidden(hash, value);
    }

    @Override
    public String inputText(TemplateHashModelEx hash, String value, boolean isError, List<String> errorMessages) {
        return delegate.inputText(hash, value, isError, errorMessages);
    }

    public String inputText(TemplateHashModelEx hash, String value, boolean isError, String errorMessage) {
        return delegate.inputText(hash, value, isError, errorMessage);
    }

    @Override
    public String inputText(TemplateHashModelEx hash, String value, List<String> errorMessages) {
        return delegate.inputText(hash, value, errorMessages);
    }

    public String link(String url, String title) {
        return delegate.link(url, title);
    }

    public String link(String url, String title, TemplateHashModelEx hash) {
        return delegate.link(url, title, hash);
    }

    @Override public String link(String controller, String action, String identity, String title, TemplateHashModelEx attributes) {
        return delegate.link(controller, action, identity, title, attributes);
    }

    public String listLink(String controller, String title) {
        return delegate.listLink(controller, title);
    }

    public String listLink(String controller, String title, String name) {
        return delegate.listLink(controller, title, name);
    }

    public String listLink(String controller, String title, String identity, String name) {
        return delegate.listLink(controller, title, identity, name);
    }

    public String listLink(String controller, String title, String identity, String name, TemplateHashModelEx queryStringHash) {
        return delegate.listLink(controller, title, identity, name, queryStringHash);
    }

    public String listLink(String controller, String title, String identity, String name, TemplateHashModelEx queryStringHash, TemplateHashModelEx attributesHash) {
        return delegate.listLink(controller, title, identity, name, queryStringHash, attributesHash);
    }

    public String listUrl(String controller, String title) {
        return delegate.listUrl(controller, title);
    }

    public String listUrl(String controller, String identity, String title) {
        return delegate.listUrl(controller, identity, title);
    }

    public String listUrl(String controller, String identity, String title, TemplateHashModelEx queryStringHash) {
        return delegate.listUrl(controller, identity, title, queryStringHash);
    }

    public String multiSelect(String identifier, Collection<? extends Entity<?>> available, Collection<? extends Entity<?>> selected) {
        return delegate.multiSelect(identifier, available, selected);
    }

    @Override
    public String passwordText(TemplateHashModelEx hash, String value, boolean isError, List<String> errorMessage) {
        return delegate.passwordText(hash, value, isError, errorMessage);
    }

    public String passwordText(TemplateHashModelEx hash, String value, boolean isError, String errorMessage) {
        return delegate.passwordText(hash, value, isError, errorMessage);
    }

    @Override
    public String passwordText(TemplateHashModelEx hash, String value, List<String> errorMessage) {
        return delegate.passwordText(hash, value, errorMessage);
    }

    public String playButton(String controller, String title, Entity<?> identifiableDeleteable) {
        return showLink(controller, identifiableDeleteable.getTitle(), identifiableDeleteable.getIdentity(), showImage("play_button.png"), new SimpleHash(), new SimpleHash());
    }

    public String popOverButton(String identity, String url, int width, int height, String title, String buttonImage) {
        return delegate.popOverButton(identity, url, width, height, title, buttonImage);
    }

    public String select(String name, Collection<? extends Entity<?>> cards, String selected, TemplateHashModelEx hash) {
        return delegate.select(name, cards, selected, hash);
    }

    public String select(String name, Collection<? extends Entity<?>> cards, String selected, TemplateHashModelEx hash, TemplateHashModelEx additionalCards) {
        return delegate.select(name, cards, selected, hash, additionalCards);
    }

    public String showCss(String title) {
        return delegate.showCss(title);
    }

    public String showImage(String title) {
        return delegate.showImage(title);
    }

    public String showImage(String controller, String identity, String title) {
        return delegate.showImage(controller, identity, title);
    }

    public String showImage(String controller, String identity, String title, TemplateHashModelEx hash) {
        return delegate.showImage(controller, identity, title, hash);
    }

    public String showImage(String controller, String title, TemplateHashModelEx hash) {
        return delegate.showImage(controller, title, hash);
    }

    public String showImage(String title, TemplateHashModelEx hash) {
        return delegate.showImage(title, hash);
    }

    public String showImageUrl(String title) {
        return delegate.showImageUrl(title);
    }

    public String showLink(String controller, String title) {
        return delegate.showLink(controller, title);
    }

    public String showLink(String controller, String title, String name) {
        return delegate.showLink(controller, title, name);
    }

    public String showLink(String controller, String title, String identity, String name) {
        return delegate.showLink(controller, title, identity, name);
    }

    public String showLink(String controller, String title, String identity, String name, TemplateHashModelEx queryStringHash) {
        return delegate.showLink(controller, title, identity, name, queryStringHash);
    }

    public String showLink(String controller, String title, String identity, String name, TemplateHashModelEx queryStringHash, TemplateHashModelEx attributesHash) {
        return delegate.showLink(controller, title, identity, name, queryStringHash, attributesHash);
    }

    public String showScript(String title) {
        return delegate.showScript(title);
    }

    public String loadScripts(Collection<String> files) {
        return delegate.loadScripts(files);
    }

    public String loadCss(Collection<String> files) {
        return delegate.loadCss(files);
    }

    public String showUrl(String controller, String title) {
        return delegate.showUrl(controller, title);
    }

    public String showUrl(String controller, String identity, String title) {
        return delegate.showUrl(controller, identity, title);
    }

    public String showUrl(String controller, String identity, String title, TemplateHashModelEx hash) {
        return delegate.showUrl(controller, identity, title, hash);
    }

    public String showUrl(String controller, String title, TemplateHashModelEx hash) {
        return delegate.showUrl(controller, title, hash);
    }

    public String slider(String identifier, Integer value, Integer minimum, Integer maximum) {
        return delegate.slider(identifier, value, minimum, maximum);
    }

    public String submit(String value) {
        return delegate.submit(value);
    }

    public String textArea(TemplateHashModelEx hash, String value, boolean isError, String errorMessage) {
        return delegate.textArea(hash, value, isError, errorMessage);
    }

    @Override
    public String tipAttribute(String message) {
        return delegate.tipAttribute(message);
    }

    public String toString() {
        return delegate.toString();
    }

    public String twoHandleSlider(Integer minimum, Integer maximum, Integer number) {
        return delegate.twoHandleSlider(minimum, maximum, number);
    }

    public String updateUrl(String controller, String title) {
        return delegate.updateUrl(controller, title);
    }

    public String updateUrl(String controller, String identity, String title) {
        return delegate.updateUrl(controller, identity, title);
    }

    public String url(String controller, String action, String identity, String title) {
        return delegate.url(controller, action, identity, title);
    }

    public String url(String controller, String action, String identity, String title, String queryString) {
        return delegate.url(controller, action, identity, title, queryString);
    }

    public String url(String controller, String action, String identity, String title, TemplateHashModelEx queryStringHash) {
        return delegate.url(controller, action, identity, title, queryStringHash);
    }

    public String yesNo(String name, Boolean selected, TemplateHashModelEx hash) {
        return delegate.yesNo(name, selected, hash);
    }

    @Override
    public String submitImage(String image) {
        return delegate.submitImage(image);
    }

    @Override
    public String submitImage(String image, TemplateHashModelEx hash) {
        return delegate.submitImage(image, hash);
    }

    @Override
    public String legend(String name, String text) {
        return delegate.legend(name, text);
    }

}
