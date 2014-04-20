package uk.co.itstherules.cardplanner.server;

import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpacesListener;
import uk.co.itstherules.cardplanner.filter.ClientIdentityFilter;
import uk.co.itstherules.cardplanner.listener.CardPlannerListener;
import uk.co.itstherules.yawf.controller.processor.ProcessStepRegisterListener;
import uk.co.itstherules.yawf.model.EntityManagerListener;
import uk.co.itstherules.yawf.server.ServerConfigReceiver;
import uk.co.itstherules.yawf.servlet.YAWFServlet;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;

public final class CardPlannerConfigBuilder {

    public enum TargetEnvironment {
        TEST, PRODUCTION
    }

    public void build(ServerConfigReceiver receiver, TargetEnvironment targetEnvironment) {
        Map<String, String> map;
        if(targetEnvironment == TEST) {
            map = testEnvironment();
        } else {
            map = productionEnvironment();
        }
        receiver.contextParams(contextParams())
                .localeMappings(localeMappings())
                .welcomeFiles("index.xhtml", "index.html")
                .listener(new EntityManagerListener(map))
                .listener(new CardPlannerListener())
                .listener(new SharedObjectSpacesListener())
                .listener(new ProcessStepRegisterListener())
                .filter("/*", new ClientIdentityFilter())
                .servlet("/*", new YAWFServlet());
    }


    private static Map<String, String> productionEnvironment() {
        final HashMap<String, String> map = new HashMap<>();
        shared(map);
        map.put("javax.persistence.jdbc.url", "jdbc:h2:~/Data/cardplanner;FILE_LOCK=NO;MVCC=true");
        map.put("javax.persistence.jdbc.user", "cardplanner");
        map.put("javax.persistence.jdbc.password", "cardplanner");
        map.put("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
        map.put("hibernate.c3p0.acquire_increment", "4");
        map.put("hibernate.c3p0.idle_test_period", "500");
        map.put("hibernate.c3p0.max_size", "100");
        map.put("hibernate.c3p0.max_statements", "15");
        map.put("hibernate.c3p0.min_size", "5");
        map.put("hibernate.c3p0.timeout", "1000");
        return map;
    }

    public static Map<String, String> testEnvironment() {
        final HashMap<String, String> map = new HashMap<>();
        shared(map);
        map.put("javax.persistence.jdbc.url", "jdbc:h2:mem:cardplanner;MVCC=true");
        map.put("javax.persistence.jdbc.user", "cardplanner");
        map.put("javax.persistence.jdbc.password", "cardplanner");
        return map;
    }

    private static void shared(HashMap<String, String> map) {
        map.put("hibernate.archive.autodetection", "class");
        map.put("hibernate.show_sql", "true");
        map.put("hibernate.format_sql", "true");
        map.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        map.put("hibernate.hbm2ddl.auto", "update");
        map.put("javax.persistence.jdbc.driver", "org.h2.Driver");
    }


    private Map<String, String> contextParams() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("processRoot", "uk.co.itstherules.cardplanner.controller.processor.active");
        map.put("controllerRoot", "uk.co.itstherules.cardplanner.controller.active");
        map.put("viewRoot", "uk.co.itstherules.cardplanner.view.active");
        map.put("resourceRoot", "uk.co.itstherules.cardplanner.view");

        map.put("maxUploadSize", "32768000");
        map.put("root", "/CardPlanner");
        map.put("applicationName", "CardPlanner");
        map.put("applicationVersion", "6.3.2");
        map.put("webAppRootKey", "/CardPlanner.root");
        map.put("defaultHomePage", "/StoryBoard/Show/0/index.xhtml");
        map.put("binaryExtensions", ".gif,.png,.jpg,.jpeg,.zip,.ico");
        map.put("captcha", "true");
        map.put("captchaPublicKey", "6LeuO78SAAAAADBSWUk5dBSwwjR_BhyIjiXQvXtf");
        map.put("captchaPrivateKey", "6LeuO78SAAAAACwS-SJiy3wi2hnBFfRs3_mQoc2Q");
        return map;
    }

    private Map<String, String> localeMappings() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ar", "ISO-8859-6");
        map.put("be", "ISO-8859-5");
        map.put("bg", "ISO-8859-5");
        map.put("ca", "ISO-8859-1");
        map.put("cs", "ISO-8859-2");
        map.put("da", "ISO-8859-1");
        map.put("de", "ISO-8859-1");
        map.put("el", "ISO-8859-7");
        map.put("en", "ISO-8859-1");
        map.put("es", "ISO-8859-1");
        map.put("et", "ISO-8859-1");
        map.put("fi", "ISO-8859-1");
        map.put("fr", "ISO-8859-1");
        map.put("hr", "ISO-8859-2");
        map.put("hu", "ISO-8859-2");
        map.put("is", "ISO-8859-1");
        map.put("it", "ISO-8859-1");
        map.put("iw", "ISO-8859-8");
        map.put("ja", "Shift_JIS");
        map.put("ko", "EUC-KR");
        map.put("lt", "ISO-8859-2");
        map.put("lv", "ISO-8859-2");
        map.put("mk", "ISO-8859-5");
        map.put("nl", "ISO-8859-1");
        map.put("no", "ISO-8859-1");
        map.put("pl", "ISO-8859-2");
        map.put("pt", "ISO-8859-1");
        map.put("ro", "ISO-8859-2");
        map.put("ru", "ISO-8859-5");
        map.put("sh", "ISO-8859-5");
        map.put("sk", "ISO-8859-2");
        map.put("sl", "ISO-8859-2");
        map.put("sq", "ISO-8859-2");
        map.put("sr", "ISO-8859-5");
        map.put("sv", "ISO-8859-1");
        map.put("tr", "ISO-8859-9");
        map.put("uk", "ISO-8859-5");
        map.put("zh", "GB2312");
        map.put("zh_TW", "Big5");
        return map;
    }

}
