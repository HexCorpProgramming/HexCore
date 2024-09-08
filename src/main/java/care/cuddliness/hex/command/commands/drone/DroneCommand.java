package care.cuddliness.hex.command.commands.drone;

import care.cuddliness.hex.HexCore;
import care.cuddliness.hex.database.model.Drone;
import care.cuddliness.hex.message.MessageUtil;
import care.cuddliness.hex.message.MessageValue;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

@CommandAlias("drone|d")
public class DroneCommand extends BaseCommand {
    private static HexCore core;

    public DroneCommand(HexCore core) {
        DroneCommand.core = core;
    }

    @Default
    @Subcommand("status")
    @Syntax("<+tag> <player>")
    @Description("Toggle your battery status")
    public static void onStatusCommand(Player player, String[] args) {
        if (args.length == 0) {
            if (core.getDroneDataController().getDrone(player.getUniqueId().toString()) != null) {
                Drone drone = core.getDroneDataController().getDrone(player.getUniqueId().toString());
                sendStatus(drone, player);
            }

        }
    }

    @Default
    @Subcommand("settings toggle battery")
    @Syntax("<+tag> <player>")
    @Description("Toggle your battery status")
    public static void onDroneBattery(Player player, String[] args) {
        //Check if it has an argument
        if (args.length == 0) {
            //Check if the sender is a drone
            if (core.getDroneDataController().getDrone(player.getUniqueId().toString()) != null) {
                Drone drone = core.getDroneDataController().getDrone(player.getUniqueId().toString());
                if(!drone.isActive()){
                    MessageUtil.sendMessage(MessageValue.DRONE_DEACTIVATED.getMessage(), player);
                    return;
                }
                if (!drone.isBatteryStatus()) {
                    drone.setBatteryStatus(true);
                    //Enable BatteryStatus
                    MessageUtil.sendMessage(MessageValue.BATTERY_ENABLED.getMessage(), player);
                    core.getDroneDataController().updateDrone(drone);
                } else {
                    drone.setBatteryStatus(false);
                    MessageUtil.sendMessage(MessageValue.BATTERY_DISABLED.getMessage(), player);
                    core.getDroneDataController().updateDrone(drone);
                    //Disables BatteryStatus
                }
            }
        } else if (args.length == 1) {
            Drone drone = null;
            //Check if target is an integer / id
            if (args[0].length() == 4 && isInteger(args[0]))
                drone = core.getDroneDataController().getDroneById(Integer.parseInt(args[0]));
            //Check if target is a user or not
            if (Bukkit.getPlayer(args[0]) != null)
                drone = core.getDroneDataController().getDrone(Bukkit.getPlayer(args[0]).getUniqueId().toString());

            //Check if drone is not null, else this target doesn't exist
            if (drone != null) {
                if(!drone.isActive()){
                    MessageUtil.sendMessage(MessageValue.DRONE_DEACTIVATED.getMessage(), player);
                    return;
                }
                //Check if sender is drone itself
                if (drone.getId().equalsIgnoreCase(player.getUniqueId().toString())) {
                    toggleBattery(drone, player);
                    return;
                }
                //Check if user is consented over drone
                if (!core.getConsentDataController().isConsented(Objects.requireNonNull(Bukkit.getPlayer(drone.getId())),
                        player.getUniqueId().toString()) || !isInOvveride(player)) {
                    MessageUtil.sendMessage(MessageValue.PLAYER_NOT_CONSENTED.getMessage(), player);
                } else {
                    toggleBattery(drone, player);
                }
            } else {
                MessageUtil.sendMessage(MessageValue.DRONE_NOT_REGISTERED_IN_HIVE.getMessage(), player);

            }
        }
    }

    @Default
    @Subcommand("settings toggle idprepend")
    @Syntax("<+tag> <player>")
    @Description("Toggle IDPrepend status")
    public static void onIdPrepend(Player player, String[] args) {
        //Check if it has an argument
        if (args.length == 0) {
            //Check if the sender is a drone
            if (core.getDroneDataController().getDrone(player.getUniqueId().toString()) != null) {
                Drone drone = core.getDroneDataController().getDrone(player.getUniqueId().toString());
                if (!drone.isIdPrepend()) {
                    drone.setIdPrepend(true);
                    //Enable BatteryStatus
                    MessageUtil.sendMessage(MessageValue.IDPREPEND_ENABLE.getMessage(), player);
                    core.getDroneDataController().updateDrone(drone);
                } else {
                    drone.setIdPrepend(false);
                    MessageUtil.sendMessage(MessageValue.IDPREPEND_DISABLE.getMessage(), player);
                    core.getDroneDataController().updateDrone(drone);
                    //Disables BatteryStatus
                }
            }
        } else if (args.length == 1) {
            Drone drone = null;
            //Check if target is an integer / id
            if (args[0].length() == 4 && isInteger(args[0]))
                drone = core.getDroneDataController().getDroneById(Integer.parseInt(args[0]));
            //Check if target is a user or not
            if (Bukkit.getPlayer(args[0]) != null)
                drone = core.getDroneDataController().getDrone(Bukkit.getPlayer(args[0]).getUniqueId().toString());
            //Check if drone is not null, else this target doesn't exist
            if (drone != null) {
                //Check if drone is deactivated
                if(!drone.isActive()){
                    MessageUtil.sendMessage(MessageValue.DRONE_DEACTIVATED.getMessage(), player);
                    return;
                }
                //Check if sender is drone itself
                if (drone.getId().equalsIgnoreCase(player.getUniqueId().toString())) {
                    toggleIdPrepend(drone, player);
                    return;
                }
                //Check if user is consented over drone
                if (!core.getConsentDataController().isConsented(Objects.requireNonNull(Bukkit.getPlayer(drone.getId())),
                        player.getUniqueId().toString()) || !isInOvveride(player)) {
                    MessageUtil.sendMessage(MessageValue.PLAYER_NOT_CONSENTED.getMessage(), player);
                } else {
                    toggleIdPrepend(drone, player);
                }
            } else {
                MessageUtil.sendMessage(MessageValue.DRONE_NOT_REGISTERED_IN_HIVE.getMessage(), player);
            }
        }
    }

    @Default
    @Subcommand("settings toggle corruption")
    @Syntax("<+tag> <player>")
    @Description("Toggle corruption status")
    public static void onGlitching(Player player, String[] args) {
        //Check if it has an argument for only sender
        if (args.length == 0) {
            //Check if the sender is a drone
            if (core.getDroneDataController().getDrone(player.getUniqueId().toString()) != null) {
                Drone drone = core.getDroneDataController().getDrone(player.getUniqueId().toString());
                if (!drone.isTextGlitching()) {
                    drone.setTextGlitching(true);
                    //Enable BatteryStatus
                    MessageUtil.sendMessage(MessageValue.CORRUPTION_ENABLE.getMessage(), player);
                    core.getDroneDataController().updateDrone(drone);
                } else {
                    drone.setTextGlitching(false);
                    MessageUtil.sendMessage(MessageValue.CORRUPTION_DISABLE.getMessage(), player);
                    core.getDroneDataController().updateDrone(drone);
                    //Disables BatteryStatus
                }
            }
            //Check if args target is a player or a drone
        } else if (args.length == 1) {
            Drone drone = null;
            //Check if target is an integer / id
            if (args[0].length() == 4 && isInteger(args[0]))
                drone = core.getDroneDataController().getDroneById(Integer.parseInt(args[0]));
            //Check if target is a user or not
            if (Bukkit.getPlayer(args[0]) != null)
                drone = core.getDroneDataController().getDrone(Bukkit.getPlayer(args[0]).getUniqueId().toString());
            //Check if drone is not null, else this target doesn't exist
            if (drone != null) {
                //Check if sender is drone itself
                //Check if drone is deactivated
                if(!drone.isActive()){
                    MessageUtil.sendMessage(MessageValue.DRONE_DEACTIVATED.getMessage(), player);
                    return;
                }
                if (drone.getId().equalsIgnoreCase(player.getUniqueId().toString())) {
                    toggleGlitching(drone, player);
                    return;
                }
                //Check if user is consented over drone
                if (!core.getConsentDataController().isConsented(Objects.requireNonNull(Bukkit.getPlayer(drone.getId())),
                        player.getUniqueId().toString()) || !isInOvveride(player)) {
                    MessageUtil.sendMessage(MessageValue.PLAYER_NOT_CONSENTED.getMessage(), player);
                } else {
                    toggleGlitching(drone, player);
                }
            } else {
                MessageUtil.sendMessage(MessageValue.DRONE_NOT_REGISTERED_IN_HIVE.getMessage(), player);

            }
        }
    }

    @Default
    @Subcommand("emergencyrelease")
    @Syntax("<+tag> [player]")
    @CommandCompletion("@players")
    @Description("Releases yourself from any drone related configuration")
    public static void onRelease(Player player, String[] args){
        if (core.getDroneDataController().getDrone(player.getUniqueId().toString()) == null) {
            MessageUtil.sendMessage(MessageValue.DRONE_NOT_REGISTERED_IN_HIVE.getMessage(), player);
            return;
        }

        if (args.length == 0) {
            //Check if the sender is a drone
            if (core.getDroneDataController().getDrone(player.getUniqueId().toString()) != null) {
                Drone drone = core.getDroneDataController().getDrone(player.getUniqueId().toString());
                if(drone.isActive()) {
                    core.getDroneDataController().emergencyRelease(drone);
                    MessageUtil.sendMessage(MessageValue.EMERGENCY_RELEASE_CONSENTED.getMessage().replace("%droneid%", drone.getId()), player);
                }else{
                    MessageUtil.sendMessage(MessageValue.DRONE_ALREADY_DEACTIVATED.getMessage(), player);
                }
            }
        }else if(args.length == 1){
            Drone drone = null;
            //Check if target is an integer / id
            if (args[0].length() == 4 && isInteger(args[0]))
                drone = core.getDroneDataController().getDroneById(Integer.parseInt(args[0]));
            //Check if target is a user or not
            if (Bukkit.getPlayer(args[0]) != null)
                drone = core.getDroneDataController().getDrone(Bukkit.getPlayer(args[0]).getUniqueId().toString());
            //Check if drone is not null, else this target doesn't exist
            if (drone != null) {
                //Check if drone is deactivated
                if(!drone.isActive()){
                    MessageUtil.sendMessage(MessageValue.DRONE_DEACTIVATED.getMessage(), player);
                    return;
                }
                //Check if sender is drone itself
                if (drone.getId().equalsIgnoreCase(player.getUniqueId().toString())) {
                    core.getDroneDataController().emergencyRelease(drone);
                    MessageUtil.sendMessage(MessageValue.EMERGENCY_RELEASE_DRONE.getMessage(), player);
                    return;
                }
                //Check if user is consented over drone
                if (!core.getConsentDataController().isConsented(Objects.requireNonNull(Bukkit.getPlayer(drone.getId())),
                        player.getUniqueId().toString()) || !isInOvveride(player)) {
                    MessageUtil.sendMessage(MessageValue.PLAYER_NOT_CONSENTED.getMessage(), player);
                } else {
                    if(drone.isActive()) {
                        core.getDroneDataController().emergencyRelease(drone);
                        MessageUtil.sendMessage(MessageValue.EMERGENCY_RELEASE_CONSENTED.getMessage().replace("%droneid%", drone.getId()), player);
                        Player p = Bukkit.getPlayer(drone.getId());
                        //Check if player is online
                        if(p == null){
                            MessageUtil.sendMessage(MessageValue.DRONE_NOT_ONLINE.getMessage(), player);
                        }else{
                            MessageUtil.sendMessage(MessageValue.EMERGENCY_RELEASE_DRONE.getMessage(), player);
                        }
                    }else{
                        MessageUtil.sendMessage(MessageValue.DRONE_ALREADY_DEACTIVATED.getMessage(), player);
                    }
                }
            } else {
                MessageUtil.sendMessage(MessageValue.DRONE_NOT_REGISTERED_IN_HIVE.getMessage(), player);

            }
        }
    }

    private static boolean isInOvveride(Player player){
        core.getMainConfigYml().getStringList("mod_override");
        return core.getMainConfigYml().getStringList("mod_override").contains(player.getUniqueId().toString());
    }

    private static void toggleGlitching(Drone drone, Player player) {
        if (!drone.isTextGlitching()) {
            drone.setTextGlitching(true);
            //Enable BatteryStatus
            MessageUtil.sendMessage(MessageValue.CORRUPTION_ENABLE.getMessage(), player);
            core.getDroneDataController().updateDrone(drone);
        } else {
            drone.setTextGlitching(false);
            MessageUtil.sendMessage(MessageValue.CORRUPTION_DISABLE.getMessage(), player);
            core.getDroneDataController().updateDrone(drone);
            //Disables BatteryStatus
        }
    }

    private static void toggleIdPrepend(Drone drone, Player player) {
        if (!drone.isIdPrepend()) {
            drone.setIdPrepend(true);
            //Enable BatteryStatus
            MessageUtil.sendMessage(MessageValue.IDPREPEND_ENABLE.getMessage(), player);
            core.getDroneDataController().updateDrone(drone);
        } else {
            drone.setIdPrepend(false);
            MessageUtil.sendMessage(MessageValue.IDPREPEND_DISABLE.getMessage(), player);
            core.getDroneDataController().updateDrone(drone);
            //Disables BatteryStatus
        }
    }

    private static void toggleBattery(Drone drone, Player player) {
        if (!drone.isBatteryStatus()) {
            drone.setBatteryStatus(true);
            //Enable BatteryStatus
            MessageUtil.sendMessage(MessageValue.BATTERY_ENABLED.getMessage(), player);
            core.getDroneDataController().updateDrone(drone);
        } else {
            drone.setBatteryStatus(false);
            MessageUtil.sendMessage(MessageValue.BATTERY_DISABLED.getMessage(), player);
            core.getDroneDataController().updateDrone(drone);
            //Disables BatteryStatus
        }
    }

    private static void sendStatus(Drone drone, Player player) {
        List<String> messagelines = core.getMainConfigYml().getStringList("drone_status");
        messagelines.forEach(s -> MessageUtil.sendMessage(placeholders(drone, s), player));
    }

    private static String placeholders(Drone drone, String input) {
        StringBuilder builder = new StringBuilder();
        drone.getConsent().forEach(consent -> {
            builder.append(Bukkit.getOfflinePlayer(consent.getConsented()).getPlayerProfile().getName()).append(" - ");
        });
        return input.replace("%droneid%", String.valueOf(drone.getDroneId()))
                .replace("%glitched%", coloredBoolean(drone.isTextGlitching()))
                .replace("%idprepend%", coloredBoolean(drone.isIdPrepend()))
                .replace("%batterypower%", coloredBoolean(drone.isBatteryStatus()))
                .replace("%batterycharge%", drone.getDroneSettings().getBatteryCapacity() + "%")
                .replace("%trustedusers%", builder.toString());
    }

    private static String coloredBoolean(boolean bool) {
        if (bool) {
            return "<bold><color:green> true</color></bold>";
        } else {
            return "<bold><color:red> false</color></bold>";
        }
    }

    //Check if an input is a valid int
    private static boolean isInteger(String input) {
        try {
            int num = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
