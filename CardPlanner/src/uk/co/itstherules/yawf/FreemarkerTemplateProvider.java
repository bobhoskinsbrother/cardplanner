package uk.co.itstherules.yawf;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import uk.co.itstherules.yawf.model.serializer.Json;
import uk.co.itstherules.yawf.view.helper.WordAbbreviator;

import java.util.Locale;

public final class FreemarkerTemplateProvider {
	
    private static final FreemarkerTemplateProvider INSTANCE = new FreemarkerTemplateProvider();

    private FreemarkerTemplateProvider() {  }

    public static FreemarkerTemplateProvider getInstance() {
        return INSTANCE;
    }

    public Configuration provide(Class<?> root) {
		return configuration(new ClassTemplateLoader(root, ""));
	}
	
	public Configuration provide(String templateName, String template) {
		StringTemplateLoader loader = new StringTemplateLoader();
		loader.putTemplate(templateName, template);
		return configuration(loader);
	}
	
	private Configuration configuration(TemplateLoader loader) {
		Configuration freeMarker = new Configuration();
		freeMarker.setEncoding(Locale.UK, "UTF-8");
		freeMarker.setURLEscapingCharset("UTF-8");
		try {
	        freeMarker.setSharedVariable("abbreviator", new WordAbbreviator());
	        freeMarker.setSharedVariable("jsonSerializer", new Json<Object>());
        } catch (TemplateModelException e) {
	        throw new RuntimeException(e);
        }
		freeMarker.setTemplateLoader(loader);
		return freeMarker;
    }
	
}
