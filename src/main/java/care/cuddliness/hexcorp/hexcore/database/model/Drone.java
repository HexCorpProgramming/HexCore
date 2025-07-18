package care.cuddliness.hexcorp.hexcore.database.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class Drone {

    @Getter private String id;
    @Getter private int droneId;
    @Getter private List<Consent> consent;
    @Getter private boolean active = true;
    @Getter private boolean batteryStatus = false;
    @Getter private boolean idPrepend = false;
    @Getter private boolean speechOptimization = false;
    @Getter private boolean textGlitching = false;
    @Getter private int batteryCapacity = 100;

    public Drone(String id, int droneId, List<Consent> consent, boolean active, boolean batteryStatus, boolean idPrepend, boolean speechOptimization, boolean textGlitching, int batteryCapacity) {
        this.id = id;
        this.droneId = droneId;
        this.consent = consent;
        this.active = active;
        this.batteryStatus = batteryStatus;
        this.idPrepend = idPrepend;
        this.speechOptimization = speechOptimization;
        this.textGlitching = textGlitching;
        this.batteryCapacity = batteryCapacity;
    }


}
