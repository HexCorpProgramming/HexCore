package care.cuddliness.hex.message;

import care.cuddliness.hex.HexCore;
import org.jetbrains.annotations.NotNull;

public enum MessageValue {
    PREFIX("PREFIX"), COMMAND_HIVE_ALREADY_REGISTERED("COMMAND_HIVE_ALREADY_REGISTERED"),
    COMMAND_HIVE_CODE_VALIDATION("COMMAND_HIVE_CODE_VALIDATION"),
    COMMAND_HIVE_WELCOME("COMMAND_HIVE_WELCOME"),
    COMMAND_HIVE_WELCOME_BACK("COMMAND_HIVE_WELCOME_BACK"),
    COMMAND_HIVE_ALREADY_DRONE("COMMAND_HIVE_ALREADY_DRONE"),
    COMMAND_HIVE_PLAYER_NOT_FOUND("COMMAND_HIVE_PLAYER_NOT_FOUND"),
    COMMAND_CONSENT_INVITE_TARGET("COMMAND_CONSENT_INVITE_TARGET"),
    COMMAND_CONSENT_INVITE("COMMAND_CONSENT_INVITE"),
    COMMAND_CONSENT_INVITE_ALREADY_SENT("COMMAND_CONSENT_INVITE_ALREADY_SENT"),
    COMMAND_CONSENT_ACCEPTED("COMMAND_CONSENT_ACCEPTED"),
    COMMAND_CONSENT_ACCEPTED_TARGET("COMMAND_CONSENT_ACCEPTED_TARGET"),
    COMMAND_CONSENT_INVITE_NOT_FOUND("COMMAND_CONSENT_INVITE_NOT_FOUND"),
    COMMAND_CONSENT_ALREADY_TRUSTED("COMMAND_CONSENT_ALREADY_TRUSTED"),
    COMMAND_CONSENT_SELF_USER("COMMAND_CONSENT_SELF_USER"),
    COMMAND_CONSENT_NOT_IN_LIST("COMMAND_CONSENT_NOT_IN_LIST"),
    COMMAND_CONSENT_REMOVED("COMMAND_CONSENT_REMOVED"),
    BATTERY_CHARGED("BATTERY_CHARGED"),
    BATTERY_DISCHARGE("BATTERY_DISCHARGE"),
    BATTERY_ENABLED("BATTERY_ENABLED"),
    BATTERY_DISABLED("BATTERY_DISABLED"),
    IDPREPEND_ENABLE("IDPREPENND_ENABLE"),
    IDPREPEND_DISABLE("IDPREPENND_DISABLE"),
    IDPREPEND_REMINDER("IDPREPENND_REMINDER"),
    CORRUPTION_ENABLE("CORRUPTION_ENABLE"),
    CORRUPTION_DISABLE("CORRUPTION_DISABLE"),
    PLAYER_NOT_CONSENTED("PLAYER_NOT_CONSENTED"),
    DRONE_NOT_REGISTERED_IN_HIVE("DRONE_NOT_REGISTERED_IN_HIVE"),
    EMERGENCY_RELEASE_DRONE("EMERGENCY_RELEASE_DRONE"),
    EMERGENCY_RELEASE_CONSENTED("EMERGENCY_RELEASE_CONSENTED"),
    DRONE_NOT_ONLINE("DRONE_NOT_ONLINE"),
    DRONE_ALREADY_DEACTIVATED("DRONE_ALREADY_DEACTIVATED"),
    DRONE_DEACTIVATED("DRONE_DEACTIVATED");


    private String name;

    MessageValue(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @NotNull
    public String getMessage() {
        return HexCore.getHexCore().getMessageYaml().getString("PREFIX") +
                HexCore.getHexCore().getMessageYaml().getString(getName());
    }
}
