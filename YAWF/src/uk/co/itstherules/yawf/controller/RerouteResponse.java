package uk.co.itstherules.yawf.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.view.helper.UrlBuilder;

public class RerouteResponse {

    public static void to(HttpServletResponse response, String root, String controller, String action) {
        to(response, root, controller, action, "0");
    }

    public static void to(HttpServletResponse response, String root, String controller, String action, String identity) {
        to(response, root, controller, action, identity, "index.xhtml");
    }

    public static void to(HttpServletResponse response, String root, String controller, String action, String identity, String title) {
        String url = new UrlBuilder(root).url(controller, action, identity, title);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
