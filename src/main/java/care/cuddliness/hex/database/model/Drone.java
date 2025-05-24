package care.cuddliness.hex.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Entity
@Table(name = "player_data")
public class Drone {

    @Id
    private String id;
    @Column(name = "id_drone", columnDefinition = "varchar(32)")
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

    public Drone() {
    }

    public Drone merge(Drone other) {
        setActive(other.isActive());
        setBatteryStatus(other.isBatteryStatus());
        setIdPrepend(other.isIdPrepend());
        setSpeechOptimalization(other.isSpeechOptimalization());
        setTextGlitching(other.isTextGlitching());
        setConsent(other.getConsent());
        return other;
    }

    public void linkToConsent(Consent consent) {
        consent.setDrone(this);
        this.consent.add(consent);
    }

    public Drone(String id, int droneId) {
        this.id = id;
        this.droneId = droneId;
    }
}
