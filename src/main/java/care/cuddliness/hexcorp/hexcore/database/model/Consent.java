package care.cuddliness.hexcorp.hexcore.database.model;

import lombok.Getter;
import lombok.Setter;


@Setter
public class Consent {
    @Getter
    private String id;
    private int droneId;

    public int getDrone() {
        return droneId;
    }

    public void setDrone(int drone) {
        this.droneId = drone;
    }

    public Consent() {
    }

    public Consent(String id, int droneId) {
        this.id = id;
        this.droneId = droneId;
    }
}
