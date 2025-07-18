package care.cuddliness.hex.database.controller;

import care.cuddliness.hex.database.model.drone.Drone;
import care.cuddliness.hex.database.model.drone.DroneModel;
import org.bukkit.entity.Player;
import org.jdbi.v3.core.Jdbi;

public class ConsentDataController {
    private Jdbi jdbi;
    private DroneModel droneModel;

    public ConsentDataController(Jdbi jdbi){
        this.jdbi = jdbi;
        droneModel = jdbi.onDemand(DroneModel.class);
    }

    public boolean isConsented(Drone drone, String uuid){

        return false;

    }

    public void removeConsent(String dronePlayerId, String consentedId){

    }

    public void createNewConsent(Player consentedId, String dronePlayerId){

    }
}
