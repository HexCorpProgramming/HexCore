package care.cuddliness.hex.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "drone_settings")
public class DroneSettings {

    @Id
    @Setter
    @Getter
    private String id;
    @OneToOne(mappedBy = "droneSettings", cascade = CascadeType.ALL)
    private Drone drone;
    @Column(name = "battery_capacity")
    @Getter
    @Setter
    private int batteryCapacity = 100;
}
