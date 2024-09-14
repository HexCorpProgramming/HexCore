package care.cuddliness.hex.database.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class DroneSettingsDataController {

    private final EntityManager entityManager;

    public DroneSettingsDataController(EntityManagerFactory entityManagerFactory) {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        this.entityManager = entityManagerFactory.createEntityManager();
    }
}
