package uk.co.itstherules.yawf.model;

import java.util.UUID;

public final class IdentityGenerator {

    private IdentityGenerator(){}

    public static void generateIdentity(IdentityDeleteable<?> entity) {
        if(entity.getIdentity() == null || entity.getIdentity().length() < 2) {
            entity.setIdentity(UUID.randomUUID().toString());
        }
    }
}
