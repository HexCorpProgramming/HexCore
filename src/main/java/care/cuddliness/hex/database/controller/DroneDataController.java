package care.cuddliness.hex.database.controller;

import care.cuddliness.hex.database.model.Drone;
import care.cuddliness.hex.database.model.DroneSettings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.bukkit.entity.Player;



public class DroneDataController {

    private final EntityManager entityManager;

    public DroneDataController(EntityManagerFactory entityManagerFactory) {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void createNewDrone(Player player, int id) {
        entityManager.getTransaction().begin();
        Drone d = new Drone(player.getUniqueId().toString(), id);
        DroneSettings settings = new DroneSettings();
        settings.setId(player.getUniqueId().toString());
        d.setDroneSettings(settings);
        entityManager.persist(d);
        entityManager.getTransaction().commit();
    }

    public void updateDrone(Drone d) {
        if(d.getId() == null){
            entityManager.persist(d);
        }else{
            entityManager.merge(d);
        }
    }

    public boolean isIdUsed(int id){
        return getDroneById(id) != null;
    }

    public Drone getDroneById(int id){
        TypedQuery<Drone> q = entityManager.createQuery("SELECT d FROM Drone d WHERE d.droneId = :droneId", Drone.class);
        q.setParameter("droneId", id);
        return q.getSingleResult();
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
}
