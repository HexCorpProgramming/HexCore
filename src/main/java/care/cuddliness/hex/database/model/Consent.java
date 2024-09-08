package care.cuddliness.hex.database.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "consent")
public class Consent {

    @Id
    @Getter private UUID id;
    @Getter String consented;
    @JoinColumn(name = "droneId", referencedColumnName = "id")
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

    public Consent(UUID id, String consented) {
        this.id = id;
        this.consented = consented;
    }
}
