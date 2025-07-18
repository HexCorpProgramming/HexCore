package care.cuddliness.hexcorp.hexcore.database.model.drone;

import care.cuddliness.hexcorp.hexcore.database.model.consent.Consent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
@Getter @Setter
@Entity
public class Drone {

    @Id
    private String id;
    @Setter
    private int droneId;
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "droneId")
    private Set<Consent> consent;
    @Column(name = "active")
    @Setter
    private boolean active = true;
    @Column(name = "battery_status")
    @Setter
    private boolean batteryStatus = false;
    @Column(name = "id_prepend")
    @Setter
    private boolean idPrepend = false;
    @Column(name = "speech_optimization")
    @Setter
    private boolean speechOptimization = false;
    @Column(name = "text_glitch")
    @Setter
    private boolean textGlitching = false;
    @Column(name = "speech_optimalization")
    @Setter
    private boolean speechOptimalization = false;
    @Column(name = "battery_capacity")
    @Getter
    @Setter
    private int batteryCapacity = 100;


}
