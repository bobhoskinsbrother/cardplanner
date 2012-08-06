package uk.co.itstherules.yawf.view;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.FreemarkerTemplateProvider;
import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.view.context.ViewContext;
import uk.co.itstherules.yawf.view.helper.TagBuilder;
import uk.co.itstherules.yawf.view.helper.XHtmlTagBuilder;
import uk.co.itstherules.yawf.view.xhtml.WikiCreoleToXhtml;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class BaseView implements View {

	private Configuration freemarker;
	protected String templateName;

	public BaseView(String templateName) {
		setDefaults(templateName, this.getClass());
	}

	public BaseView(String templateName, Class<?> root) {
		setDefaults(templateName, root);
	}
	
	@Override
    public String getTitle() { return this.getClass().getSimpleName(); }

	private void setDefaults(String templateName, Class<?> root) {
		this.templateName = templateName;
		this.freemarker = FreemarkerTemplateProvider.getInstance().provide(root);
	}
	
	private Map<String,Object> getHelpers(String root) {
		Map<String, Object> context = new LinkedHashMap<String, Object>();
		TagBuilder tagBuilder = new XHtmlTagBuilder(root);
		context.put("tagBuilder", tagBuilder);
		context.put("wikifier", new WikiCreoleToXhtml(tagBuilder));
		context.put("root", root);
		return context;
    }
	
	public void renderTo(ViewContext context, HttpServletResponse response, String root) {
		try {
			if("".equals(response.getContentType())) response.setContentType(ContentType.html.toString());
            HeaderCachingInformation.setAsAlwaysAsk(response);
			Writer writer = getWriter(response);
			writer.write(asText(context, root));
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected Writer getWriter(HttpServletResponse response) throws IOException {
	    Writer writer = response.getWriter();
	    return writer;
    }

	public String asText(ViewContext context, String root) {
		Writer writer = new StringWriter();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(getHelpers(root));
			map.putAll(context.getStore());
			Template template = getTemplate();
			template.createProcessingEnvironment(map, writer).process();
		} catch (TemplateException e) {
			throw new RuntimeException("Programmatic error - unable to process templates", e);
		} catch (IOException e) {
			throw new RuntimeException("Programmatic error - unable to process templates", e);
		}
		return writer.toString();
	}

	protected Template getTemplate() throws IOException {
	    return this.freemarker.getTemplate(this.templateName);
    }
}
