package care.cuddliness.hex.database.controller;

import care.cuddliness.hex.HexCore;
import care.cuddliness.hex.database.model.Drone;
import care.cuddliness.hex.database.model.DroneSettings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;


public class DroneDataController {

    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public DroneDataController(EntityManagerFactory entityManagerFactory) {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        this.entityManager = entityManagerFactory.createEntityManager();
        this.entityTransaction = entityManager.getTransaction();
    }


    public Drone createNewDrone(Player player, int id) {
        entityManager.getTransaction().begin();
        Drone d = new Drone(player.getUniqueId().toString(), id);
        DroneSettings settings = new DroneSettings();
        settings.setId(player.getUniqueId().toString());
        d.setDroneSettings(settings);
        entityManager.persist(d);
        entityManager.getTransaction().commit();
        return d;
    }

    public void updateDrone(Drone drone){
       Drone existing = getDroneById(drone.getDroneId());
        existing.merge(drone);
        entityTransaction.begin();
        entityManager.merge(existing);
        entityTransaction.commit();
    }

    public boolean isIdUsed(int id){
        return getDroneById(id) != null;
    }

    public Drone getDroneById(int id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Drone> cq = cb.createQuery(Drone.class);
        Root<Drone> root = cq.from(Drone.class);
        Predicate condition = cb.equal(root.get("droneId"), id);
        cq.where(condition);
        List<Drone> result = entityManager.createQuery(cq).getResultList();
        return result.get(0);

    }

    public void emergencyRelease(Drone drone){
        if(drone.isActive()){
            drone.setActive(false);
            drone.setIdPrepend(false);
            drone.setTextGlitching(false);
            drone.setSpeechOptimalization(false);
            updateDrone(drone);
        }
    }

    public Drone getDrone(String uuid) {
        return entityManager.find(Drone.class, uuid);
    }

    public void deletePlayerData(Drone playerData) {
    }
}
