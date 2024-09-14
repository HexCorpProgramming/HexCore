package care.cuddliness.hex.database.controller;

import care.cuddliness.hex.HexCore;
import care.cuddliness.hex.database.model.Consent;
import care.cuddliness.hex.database.model.Drone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class ConsentDataController {

    private final EntityManager entityManager;

    public ConsentDataController(EntityManagerFactory entityManagerFactory) {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        this.entityManager = entityManagerFactory.createEntityManager();
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

    public boolean isConsented(Drone drone, String consentedUuid) {
        return drone.getConsent().stream().filter(consent -> consent.getConsented().
                equalsIgnoreCase(consentedUuid)).toList().size() > 0;
    }

    public Consent getConsentByUuid(String uuid, String consentedUuid) {
        Drone drone = entityManager.find(Drone.class, uuid);
        List<Consent> consented = drone.getConsent();
        return consented.stream().filter(consent -> consent.getConsented()
                .equalsIgnoreCase(consentedUuid)).toList().get(0);
    }

    public void removeConsent(String uuid, String consentedUuid) {
        Drone drone = entityManager.find(Drone.class, uuid);
        drone.getConsent().remove(getConsentByUuid(uuid, consentedUuid));
        updateConsent(getConsentByUuid(uuid, consentedUuid));
    }

    public void updateConsent(Consent consent) {
        if (consent.getId() == null) {
            entityManager.persist(consent);
        } else {
            entityManager.merge(consent);
        }
    }
}
