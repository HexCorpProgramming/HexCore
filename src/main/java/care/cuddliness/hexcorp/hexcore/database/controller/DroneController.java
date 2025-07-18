package care.cuddliness.hexcorp.hexcore.database.controller;

import care.cuddliness.hexcorp.hexcore.database.model.drone.Drone;
import care.cuddliness.hexcorp.hexcore.database.model.drone.DroneModel;
import org.bukkit.entity.Player;
import org.jdbi.v3.core.Jdbi;

public class DroneDataController {
    private Jdbi jdbi;
    private DroneModel droneModel;

    public DroneDataController(Jdbi jdbi){
        this.jdbi = jdbi;
        droneModel = jdbi.onDemand(DroneModel.class);
    }

    public Drone getDrone(String uuid){
        return droneModel.findDroneByUuid(uuid);
    }

    public void insertDrone(Player player){
        droneModel.insertDrone(player.getUniqueId().toString());
    }

    public Drone getDroneById(int id){
        return droneModel.findDroneById(id);
    }
    public void insertDroneWithId(Player player, int id){
        droneModel.insertDroneWithId(player.getUniqueId().toString(), id);
    }

    public void updateDrone(Drone drone){
        droneModel.updateDrone(drone);
    }

    public void emergencyRelease(Drone drone){
    }

    public boolean isIdUsed(int id){
        return false;
    }

}
