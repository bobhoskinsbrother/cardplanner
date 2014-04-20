package uk.co.itstherules.yawf.view.helper;

import freemarker.template.SimpleHash;
import org.junit.Test;
import test.models.to.instantiate.TestModel;
import uk.co.itstherules.yawf.model.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class XHtmlTagBuilderTest {
	
	@Test public void addLink() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/Add/0/Title\" >Add Controller</a>", unit.addLink("Controller", "Title"));
    }
	
	@Test public void addLinkWithName() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/Add/0/Title\" >Name</a>", unit.addLink("Controller", "Title", "Name"));
    }	
	
	@Test public void addLinkWithNameQueryStringAttributes() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		SimpleHash queryStringValues = new SimpleHash();
		SimpleHash attributesValues = new SimpleHash();
		assertEquals("<a href=\"/Root/Controller/Add/0/Title\" >Name</a>", unit.addLink("Controller", "Title", "Name", queryStringValues, attributesValues));
		queryStringValues.put("key", "value");
		assertEquals("<a href=\"/Root/Controller/Add/0/Title?key=value\" >Name</a>", unit.addLink("Controller", "Title", "Name", queryStringValues, attributesValues));
		attributesValues.put("key", "value");
		assertEquals("<a href=\"/Root/Controller/Add/0/Title?key=value\" key=\"value\" >Name</a>", unit.addLink("Controller", "Title", "Name", queryStringValues, attributesValues));
    }
	
	@Test public void addUrl() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Add/0/Title", unit.addUrl("Controller", "Title"));
    }
	
	@Test public void checkbox() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<input type=\"checkbox\" value=\"Value\" />", unit.checkbox(new SimpleHash(), "Value", false, "There is an error"));
		assertEquals("<input type=\"checkbox\"  class=\"errorbackground errorborder\" value=\"Value\" /><div class=\"errorMessageText\">There is an error<br/></div>", unit.checkbox(new SimpleHash(), "Value", true, "There is an error"));

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", "fred");
		map.put("name", "wilma");
		assertEquals("<input type=\"checkbox\" id=\"fred\" name=\"wilma\" value=\"Value\" />", unit.checkbox(new SimpleHash(map), "Value", false, "There is an error"));
	}
	
	@Test public void createUrl() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Create/0/Title", unit.createUrl("Controller", "Title"));
    }
	
	@Test public void deleteLink() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals(
                "<a name=\"deleteButton\" href=\"javascript:TemplateController.confirmDeletion('Title','%2FRoot%2FController%2FDelete%2F0%2FTitle');\"><img src=\"/Root/Images/Show/0/delete.png\" border=\"0\" alt=\"Delete Title\" title=\"Delete Title\"  /></a>", unit.deleteLink("Controller", "Title"));
    }
	
	
	@Test public void deleteLinkIdentityName() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a name=\"deleteButton\" href=\"javascript:TemplateController.confirmDeletion('Name','%2FRoot%2FController%2FDelete%2F0000%2FTitle');\"><img src=\"/Root/Images/Show/0/delete.png\" border=\"0\" alt=\"Delete Title\" title=\"Delete Title\"  /></a>", unit.deleteLink("Controller", "Title", "0000", "Name"));
    }
	
	@Test public void deleteUrl() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Delete/0/Title", unit.deleteUrl("Controller", "Title"));
    }
	
	@Test public void deleteUrlIdentity() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Delete/0000/Title", unit.deleteUrl("Controller", "0000", "Title"));
    }

	@Test public void editLink() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/Edit/0/Title\" >Edit Controller</a>", unit.editLink("Controller", "Title"));
    }
	
	@Test public void editLinkIdentityName() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/Edit/000000/Title\" >Name</a>", unit.editLink("Controller", "Title", "000000", "Name"));
    }
	
	@Test public void editLinkName() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/Edit/0/Title\" >Name</a>", unit.editLink("Controller", "Title", "Name"));
    }	
	
	@Test public void editUrl() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Edit/0/Title", unit.editUrl("Controller", "Title"));
    }
	
	@Test public void editUrlIdentity() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Edit/0000/Title", unit.editUrl("Controller", "0000", "Title"));
    }
	
	@Test public void feedLinkIdentityName() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/Feed/000000/Title\" >Name</a>", unit.feedLink("Controller", "Title", "000000", "Name"));
    }		
	
	@Test public void feedUrlIdentity() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Feed/0000/Title", unit.feedUrl("Controller", "0000", "Title"));
    }
	
	@Test public void fileUpload() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<input type=\"file\" value=\"Value\" />", unit.fileUpload(new SimpleHash(), "Value", false, "There has been an error"));
		assertEquals("<input type=\"file\" id=\"wilma\" value=\"Value\" />", unit.fileUpload(new SimpleHash(Collections.singletonMap("id", "wilma")), "Value", false, "There has been an error"));
		assertEquals("<input type=\"file\"  class=\"errorbackground errorborder\" value=\"Value\" /><div class=\"errorMessageText\">There has been an error<br/></div>", unit.fileUpload(new SimpleHash(), "Value", true, "There has been an error"));
		assertEquals("<input type=\"file\" id=\"wilma\"  class=\"errorbackground errorborder\" value=\"Value\" /><div class=\"errorMessageText\">There has been an error<br/></div>", unit.fileUpload(new SimpleHash(Collections.singletonMap("id", "wilma")), "Value", true, "There has been an error"));
		
	}
	
	
	@Test public void inputText() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<input type=\"text\" value=\"Value\" />", unit.inputText(new SimpleHash(), "Value", false, "There has been an error"));
		assertEquals("<input type=\"text\" id=\"wilma\" value=\"Value\" />", unit.inputText(new SimpleHash(Collections.singletonMap("id", "wilma")), "Value", false, "There has been an error"));
		assertEquals("<input type=\"text\"  class=\"errorbackground errorborder\" value=\"Value\" /><div class=\"errorMessageText\">There has been an error<br/></div>", unit.inputText(new SimpleHash(), "Value", true, "There has been an error"));
		assertEquals("<input type=\"text\" id=\"wilma\"  class=\"errorbackground errorborder\" value=\"Value\" /><div class=\"errorMessageText\">There has been an error<br/></div>", unit.inputText(new SimpleHash(Collections.singletonMap("id", "wilma")), "Value", true, "There has been an error"));
    }
	
	@Test public void link() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"http:/www.google.com\" >Name</a>", unit.link("http:/www.google.com", "Name"));
    }
	
	@Test public void listLink() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/List/0/Title\" >List Controller</a>", unit.listLink("Controller", "Title"));
    }
	
	@Test public void listLinkIdentityName() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/List/1001/Title\" >Name</a>", unit.listLink("Controller", "Title", "1001", "Name"));
    }
	
	@Test public void listLinkIdentityNameHash() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/List/1001/Title\" >Name</a>", unit.listLink("Controller", "Title", "1001", "Name", new SimpleHash()));
		assertEquals("<a href=\"/Root/Controller/List/1001/Title?id=jebe\" >Name</a>", unit.listLink("Controller", "Title", "1001", "Name", new SimpleHash(Collections.singletonMap("id", "jebe"))));
    }
	
	@Test public void listLinkName() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/List/0/Title\" >Name</a>", unit.listLink("Controller", "Title", "Name"));
    }
	
	@Test public void listUrl() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/List/0/Title", unit.listUrl("Controller", "Title"));
    }
	
	@Test public void listUrlIdentity() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/List/101010/Title", unit.listUrl("Controller", "101010", "Title"));
    }
	
	@Test public void listUrlIdentityHash() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/List/1001/Title", unit.listUrl("Controller", "1001", "Title", new SimpleHash()));
		assertEquals("/Root/Controller/List/1001/Title?id=jebe", unit.listUrl("Controller", "1001", "Title", new SimpleHash(Collections.singletonMap("id", "jebe"))));
    }
	
	@Test public void passwordText() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<input type=\"password\" value=\"Value\" />", unit.passwordText(new SimpleHash(), "Value", false, "Danger Will Robinson"));
		assertEquals("<input type=\"password\" id=\"jebe\" value=\"Value\" />", unit.passwordText(new SimpleHash(Collections.singletonMap("id", "jebe")), "Value", false, "Danger Will Robinson"));
		assertEquals("<input type=\"password\"  class=\"errorbackground errorborder\" value=\"Value\" /><div class=\"errorMessageText\">Danger Will Robinson<br/></div>", unit.passwordText(new SimpleHash(), "Value", true, "Danger Will Robinson"));
		assertEquals("<input type=\"password\" id=\"jebe\"  class=\"errorbackground errorborder\" value=\"Value\" /><div class=\"errorMessageText\">Danger Will Robinson<br/></div>", unit.passwordText(new SimpleHash(Collections.singletonMap("id", "jebe")), "Value", true, "Danger Will Robinson"));
    }
	
	@Test public void select() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		Collection<Entity<?>> cards = new ArrayList<Entity<?>>();
		cards.add(new TestModel("10", "Title10"));
		cards.add(new TestModel("100", "Title100"));
		cards.add(new TestModel("10000", "Title1000"));
		assertEquals("<select name=\"Name\" >" +
                "<option value=\"10\" >Title10</option>" +
                "<option value=\"100\" selected=\"selected\">Title100</option>" +
                "<option value=\"10000\" >Title1000</option>" +
                "</select>", unit.select("Name", cards, "100", new SimpleHash()));
		
    }
	
	@Test public void selectAdditionalAttributes() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		Collection<Entity<?>> cards = new ArrayList<Entity<?>>();
		cards.add(new TestModel("10", "Title10"));
		cards.add(new TestModel("100", "Title100"));
		cards.add(new TestModel("10000", "Title1000"));
		assertEquals("<select name=\"Name\" id=\"di\" >" +
                "<option value=\"10\" >Title10</option>" +
                "<option value=\"100\" selected=\"selected\">Title100</option>" +
                "<option value=\"10000\" >Title1000</option>" +
                "</select>", unit.select("Name", cards, "100", new SimpleHash(Collections.singletonMap("id", "di"))));
    }
	
	
	@Test public void selectNoDefault() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		Collection<Entity<?>> cards = new ArrayList<Entity<?>>();
		cards.add(new TestModel("10", "Title10"));
		cards.add(new TestModel("100", "Title100"));
		cards.add(new TestModel("10000", "Title1000"));
		assertEquals("<select name=\"Name\" >" +
                "<option value=\"10\" >Title10</option>" +
                "<option value=\"100\" >Title100</option>" +
                "<option value=\"10000\" >Title1000</option>" +
                "</select>", unit.select("Name", cards, "1", new SimpleHash()));
		
    }
	
	@Test public void selectNoCards() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		Collection<Entity<?>> cards = new ArrayList<Entity<?>>();
		assertEquals("<select name=\"Name\" >" + "</select>", unit.select("Name", cards, "100", new SimpleHash()));
    }	
	
	@Test public void showCss() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<link href=\"/Root/Css/Show/0/Title\" rel=\"stylesheet\" type=\"text/css\" media=\"print,screen\"/>", unit.showCss("Title"));
    }

	@Test public void showImage() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<img src=\"/Root/Images/Show/0/Image.png\" alt=\"\"  />", unit.showImage("Image.png"));
    }

	@Test public void showLink() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/Show/0/Title\" >Show Controller</a>", unit.showLink("Controller", "Title"));
    }

	@Test public void showLinkIdentityName() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/Show/999/Title\" >Name</a>", unit.showLink("Controller", "Title", "999", "Name"));
    }
	
	@Test public void showLinkName() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<a href=\"/Root/Controller/Show/0/Title\" >Name</a>", unit.showLink("Controller", "Title", "Name"));
    }	

	@Test public void showScript() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<script type=\"text/javascript\" src=\"/Root/JavaScript/Show/0/Script.js\"></script>", unit.showScript("Script.js"));
    }

	@Test public void showUrl() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Show/0/Title", unit.showUrl("Controller", "Title"));
    }

	@Test public void showUrlHash() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Show/0/Title", unit.showUrl("Controller", "Title", new SimpleHash()));
		assertEquals("/Root/Controller/Show/0/Title?identity=ytitnedi", unit.showUrl("Controller", "Title", new SimpleHash(Collections.singletonMap("identity", "ytitnedi"))));
    }
	
	@Test public void showUrlIdentity() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Show/969/Title", unit.showUrl("Controller", "969", "Title"));
    }
	
	@Test public void submit() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<input type=\"submit\" name=\"completeAction\" value=\"Create\" />", unit.submit("Create"));
    }
	
	@Test public void textArea() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<textarea  >Value</textarea>", unit.textArea(new SimpleHash(), "Value", false, "There has been an error"));
		assertEquals("<textarea id=\"wilma\"  >Value</textarea>", unit.textArea(new SimpleHash(Collections.singletonMap("id", "wilma")), "Value", false, "There has been an error"));
		assertEquals("<textarea  class=\"errorbackground errorborder\"  title=\"There has been an error\">Value</textarea>", unit.textArea(new SimpleHash(), "Value", true, "There has been an error"));
		assertEquals("<textarea id=\"wilma\"  class=\"errorbackground errorborder\"  title=\"There has been an error\">Value</textarea>", unit.textArea(new SimpleHash(Collections.singletonMap("id", "wilma")), "Value", true, "There has been an error"));
    }

	@Test public void updateUrl() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Update/0/Title", unit.updateUrl("Controller", "Title"));
    }
	
	@Test public void updateUrlIdentity() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Update/969/Title", unit.updateUrl("Controller", "969", "Title"));
    }

	@Test public void url() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Fish/101/Title", unit.url("Controller", "Fish", "101", "Title"));
    }

	@Test public void urlHash() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Fish/101/Title?bad=good", unit.url("Controller", "Fish", "101", "Title", new SimpleHash(Collections.singletonMap("bad", "good"))));
    }

	@Test public void urlQuerystring() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("/Root/Controller/Fish/101/Title?bad=good", unit.url("Controller", "Fish", "101", "Title", "?bad=good"));
    }
	
	@Test public void yesNo() {
		TagBuilder unit = new XHtmlTagBuilder("/Root");
		assertEquals("<select name=\"Name\" bad=\"good\" >" +
                "<option value=\"true\" selected=\"selected\">Yes</option>" +
                "<option value=\"false\" >No</option>" +
                "</select>", unit.yesNo("Name", Boolean.TRUE, new SimpleHash(Collections.singletonMap("bad", "good"))));
		assertEquals("<select name=\"Name\" bad=\"good\" >" +
                "<option value=\"true\" >Yes</option>" +
                "<option value=\"false\" selected=\"selected\">No</option>" +
                "</select>", unit.yesNo("Name", Boolean.FALSE, new SimpleHash(Collections.singletonMap("bad", "good"))));
		assertEquals("<select name=\"Name\" >" +
                "<option value=\"true\" selected=\"selected\">Yes</option>" +
                "<option value=\"false\" >No</option>" +
                "</select>", unit.yesNo("Name", Boolean.TRUE, new SimpleHash()));
		assertEquals("<select name=\"Name\" >" +
                "<option value=\"true\" >Yes</option>" +
                "<option value=\"false\" selected=\"selected\">No</option>" +
                "</select>", unit.yesNo("Name", Boolean.FALSE, new SimpleHash()));
	}

}