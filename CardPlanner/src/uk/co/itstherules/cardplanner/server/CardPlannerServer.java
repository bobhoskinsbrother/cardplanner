package uk.co.itstherules.cardplanner.server;

import uk.co.itstherules.yawf.server.StandaloneServerApplication;

import java.net.URI;

public final class CardPlannerServer {

    private final StandaloneServerApplication server;

    public CardPlannerServer(){
        server = new StandaloneServerApplication("/CardPlanner", 9999);
        new CardPlannerConfigBuilder().build(server);
    }

    public static void main(String[] args) throws Exception {
        final CardPlannerServer server = new CardPlannerServer();
        try {
            server.startServer();
        } finally {
            server.destroy();
        }
    }

    public CardPlannerServer port(int port){
        server.port(port);
        return this;
    }

    public void destroy() {
        server.destroy();
    }

    public URI startServer() throws Exception {
        return server.startServer();
    }

}
