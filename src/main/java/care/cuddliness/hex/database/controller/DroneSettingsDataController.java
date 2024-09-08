package care.cuddliness.hex.database.controller;

import care.cuddliness.hex.database.model.Drone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.bukkit.entity.Player;

import java.util.Map;


public class DroneSettingsDataController {

    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public DroneSettingsDataController(EntityManagerFactory entityManagerFactory) {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        this.entityManager = entityManagerFactory.createEntityManager();
        this.entityTransaction = entityManager.getTransaction();
    }
}
