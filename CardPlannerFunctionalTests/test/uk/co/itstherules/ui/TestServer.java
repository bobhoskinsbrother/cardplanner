package uk.co.itstherules.ui;

import uk.co.itstherules.cardplanner.server.CardPlannerServer;

import java.net.URI;

import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.TEST;

public class TestServer {

    public static void main(String[] args) throws Exception {
        CardPlannerServer server = new CardPlannerServer(TEST);
        URI uri = server.port(0).startServer();
        System.out.println(uri.toASCIIString());
    }
}
