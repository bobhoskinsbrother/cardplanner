package uk.co.itstherules.cardplanner.server;

import uk.co.itstherules.yawf.server.StandaloneServerApplication;

public final class CardPlannerServer {

    public static void main(String[] args) {
        StandaloneServerApplication server = new StandaloneServerApplication("/CardPlanner", 9999);
        new CardPlannerConfigBuilder().build(server);
        Thread thread = new Thread(server);
        thread.start();
    }

}
