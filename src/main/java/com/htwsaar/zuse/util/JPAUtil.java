package com.htwsaar.zuse.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "ZusePU";
    private static EntityManagerFactory factory;

    /**
     * Creates EntityManager instance using singleton pattern
     * @return entityManager
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        try {
            if (factory == null) {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            }
            return factory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Shuts down EntityManagerFactory
     */
    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
