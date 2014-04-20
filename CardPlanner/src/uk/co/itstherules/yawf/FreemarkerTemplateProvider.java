package uk.co.itstherules.yawf;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import uk.co.itstherules.string.manipulation.CamelCase;
import uk.co.itstherules.string.manipulation.LineBreakConverter;
import uk.co.itstherules.string.manipulation.Pluraliser;
import uk.co.itstherules.yawf.model.serializer.Json;
import uk.co.itstherules.yawf.view.ResourceBundleBag;
import uk.co.itstherules.yawf.view.helper.*;

import java.util.Locale;
import java.util.ResourceBundle;

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
	        freeMarker.setSharedVariable("bag", new ResourceBundleBag().asMap(ResourceBundle.getBundle("uk/co/itstherules/cardplanner/listener/pagecontent")));
	        freeMarker.setSharedVariable("abbreviator", new WordAbbreviator());
	        freeMarker.setSharedVariable("lineBreakConverter", new LineBreakConverter("\\\\"));
	        freeMarker.setSharedVariable("letterAbbreviator", new LetterAbbreviator());
	        freeMarker.setSharedVariable("jsonSerializer", new Json<Object>());
	        freeMarker.setSharedVariable("pluraliser", new Pluraliser());
	        freeMarker.setSharedVariable("camelCase", new CamelCase());
	        freeMarker.setSharedVariable("dateProvider", new DateProvider());
	        freeMarker.setSharedVariable("math", new MathHelper());
	        freeMarker.setSharedVariable("date", new DateHelper());
        } catch (TemplateModelException e) {
	        throw new RuntimeException(e);
        }
		freeMarker.setTemplateLoader(loader);
		return freeMarker;
    }
	
}
