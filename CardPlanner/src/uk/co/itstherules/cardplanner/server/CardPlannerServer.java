package uk.co.itstherules.cardplanner.server;

import uk.co.itstherules.yawf.server.StandaloneServerApplication;

import java.net.URI;

import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment;
import static uk.co.itstherules.cardplanner.server.CardPlannerConfigBuilder.TargetEnvironment.PRODUCTION;

public final class CardPlannerServer {

    private final StandaloneServerApplication server;

    public static void main(String[] args) throws Exception {
        CardPlannerServer server = new CardPlannerServer(PRODUCTION);
        server.startServer();
    }

    public CardPlannerServer(TargetEnvironment targetEnvironment) {
        server = new StandaloneServerApplication("/CardPlanner", 9999);
        buildFor(targetEnvironment);
    }

    private void buildFor(TargetEnvironment targetEnvironment) {
        new CardPlannerConfigBuilder().build(server, targetEnvironment);
    }

    public CardPlannerServer port(int port) {
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
