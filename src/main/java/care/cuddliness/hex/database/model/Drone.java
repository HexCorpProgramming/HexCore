package care.cuddliness.hex.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "player_data")
public class Drone {

    @Id
    @Getter private String id;
    @Column(name = "id_drone")
    @Getter @Setter private int droneId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "droneSettings_id", referencedColumnName = "id")
    @Getter @Setter private DroneSettings droneSettings;
    @Getter @Setter @OneToMany(cascade = CascadeType.ALL, mappedBy = "droneId")
    private List<Consent> consent = new ArrayList<>();
    @Column(name = "active")
    @Getter @Setter private boolean active = false;
    @Column(name = "battery_status")
    @Getter @Setter private boolean batteryStatus = false;
    @Column(name = "id_prepend")
    @Getter @Setter private boolean idPrepend = false;
    @Column(name = "speech_optimization")
    @Getter @Setter private boolean speechOptimization = false;
    @Column(name = "text_glitch")
    @Getter @Setter private boolean textGlitching = false;
    @Column(name = "speech_optimalization")
    @Getter @Setter private boolean speechOptimalization = false;

    public Drone() {
    }

    public Drone merge(Drone other) {
        setActive(other.isActive());
        setBatteryStatus(other.isBatteryStatus());
        setIdPrepend(other.isIdPrepend());
        setSpeechOptimalization(other.isSpeechOptimalization());
        setTextGlitching(other.isTextGlitching());
        setDroneSettings(other.getDroneSettings());
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
