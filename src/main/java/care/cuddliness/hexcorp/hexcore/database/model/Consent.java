package care.cuddliness.hexcorp.hexcore.database.model.consent;

import care.cuddliness.hexcorp.hexcore.database.model.drone.Drone;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Entity
public class Consent {
    @Id
    @Getter
    @Column(columnDefinition = "CHAR(36)")
    private String id;
    @JoinColumn(name = "droneId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Drone droneId;

    public Drone getDrone() {
        return droneId;
    }

    public void setDrone(Drone drone) {
        this.droneId = drone;
    }

    public Consent() {
    }

    public Consent(String id, Drone droneId) {
        this.id = id;
        this.droneId = droneId;
    }
}
