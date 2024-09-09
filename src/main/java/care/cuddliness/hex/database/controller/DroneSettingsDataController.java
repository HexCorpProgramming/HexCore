package care.cuddliness.hex.database.controller;

import care.cuddliness.hex.database.model.Drone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.bukkit.entity.Player;

import java.util.Map;


public class DroneSettingsDataController {

    private final EntityManager entityManager;

    public DroneSettingsDataController(EntityManagerFactory entityManagerFactory) {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        this.entityManager = entityManagerFactory.createEntityManager();
    }
}
