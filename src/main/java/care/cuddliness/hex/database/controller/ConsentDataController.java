package care.cuddliness.hex.database.controller;

import care.cuddliness.hex.HexCore;
import care.cuddliness.hex.database.model.Consent;
import care.cuddliness.hex.database.model.Drone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConsentDataController {

    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public ConsentDataController(EntityManagerFactory entityManagerFactory) {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        this.entityManager = entityManagerFactory.createEntityManager();
        this.entityTransaction = entityManager.getTransaction();
    }

    public void createNewConsent(Player player, String consentedUuid) {
            DroneDataController droneDataController = HexCore.getHexCore().getDroneDataController();
            Consent consentData = new Consent(UUID.randomUUID(), consentedUuid);
            Drone updated = entityManager.find(Drone.class, player.getUniqueId().toString());
            consentData.setDrone(updated);
        updated.linkToConsent(consentData);
        updated.getConsent().add(consentData);
            droneDataController.updateDrone(updated);
        Drone existing = entityManager.find(Drone.class, player.getUniqueId().toString());
        existing.merge(updated);
    }
    public boolean isConsented(Player player, String consentedUuid){
        Drone drone = entityManager.find(Drone.class, player.getUniqueId().toString());
        System.out.println(drone.getConsent());
        return drone.getConsent().stream().filter(consent -> consent.getConsented().equalsIgnoreCase(consentedUuid)).toList().size() > 0;
    }

    public Consent getConsentByUuid(String uuid, String consentedUuid){
        Drone drone = entityManager.find(Drone.class, uuid);
        List<Consent> consented = drone.getConsent();
        return consented.stream().filter(consent -> consent.getConsented().equalsIgnoreCase(consentedUuid)).toList().get(0);
    }

    public void removeConsent(String uuid, String consentedUuid){
        Drone drone = entityManager.find(Drone.class, uuid);
        drone.getConsent().remove(getConsentByUuid(uuid, consentedUuid));
        updateConsent(getConsentByUuid(uuid, consentedUuid));
    }

    public void updateConsent(Consent consent){
        entityTransaction.begin();
        entityManager.merge(consent);
        entityTransaction.commit();
    }
}
