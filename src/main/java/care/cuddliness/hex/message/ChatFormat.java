package care.cuddliness.hex.message;

import care.cuddliness.hex.HexCore;
import org.jetbrains.annotations.NotNull;

public enum ChatFormat {
    PREFIX("PREFIX"), ARGUMENT("ARGUMENT"), DESCRIPTION("DESCRIPTION"), CODE("CODE"), ID("ID"), SPACER("SPACER"),
    BATTERY_1("BATTERY-1"), BATTERY_2("BATTERY-2"), BATTERY_3("BATTERY-3"), BATTERY_4("BATTERY-4"),
    BATTERY_5("BATTERY-5"), BATTERY_6("BATTERY-6"), BATTERY_7("BATTERY-7");;
    private String name;

    ChatFormat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @NotNull
    public String getMessage() {
        return HexCore.getHexCore().getMainConfigYml().getString("chatformat." + getName().toUpperCase());
    }
}
