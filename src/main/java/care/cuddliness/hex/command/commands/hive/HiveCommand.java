package care.cuddliness.hex.command.commands.hive;

import care.cuddliness.hex.HexCore;
import care.cuddliness.hex.database.model.Drone;
import care.cuddliness.hex.message.MessageUtil;
import care.cuddliness.hex.message.MessageValue;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@CommandAlias("hive|h")
public class HiveCommand extends BaseCommand {
    private static HexCore core;

    private static HashMap<UUID, UUID> consentInvite = new HashMap<>();

    public HiveCommand(HexCore core) {
        HiveCommand.core = core;
    }

    @Default
    @Subcommand("help")
    @Description("Default help command")
    public static void onHelp(Player player, String[] args) {
        MessageUtil.sendMessage("<color:#967CAF><strikethrough><bold>                                                             </bold></strikethrough>", player);
        MessageUtil.sendMessage("                                <reset><color:#967CAF>Hive help menu", player);
        MessageUtil.sendMessage("<reset>                                                                                      ", player);
        MessageUtil.sendMessage("<color:#967CAF>/hive help - <color:#ff66ff>This command!", player);
        MessageUtil.sendMessage("<color:#967CAF>/hive register <droneID> <color:gray>-</color> <color:#ff66ff>Register yourself to the hive with your id", player);
        MessageUtil.sendMessage("<color:#967CAF>/drone settings toggle idprepend [droneID/Player] <color:gray>-</color> <color:#ff66ff>Toggle IDPrepend for yourself or targeted drone", player);
        MessageUtil.sendMessage("<color:#967CAF>/drone settings toggle corruption [droneID/Player] <color:gray>-</color> <color:#ff66ff>Toggle Corrupted text for yourself or targeted drone", player);
        MessageUtil.sendMessage("<color:#967CAF>/drone settings toggle battery [droneID/Player] <color:gray>-</color> <color:#ff66ff>Toggle battery status for yourself or targeted drone", player);
        MessageUtil.sendMessage("<color:#967CAF>/hive consent invite [droneID/Player] <color:gray>-</color> <color:#ff66ff>Sends a pending consent request to targeted user / done", player);
        MessageUtil.sendMessage("<color:#967CAF>/hive consent accept [droneID/Player] <color:gray>-</color> <color:#ff66ff>Accepts a pending consent request of targeted user / done", player);
        MessageUtil.sendMessage("<color:#967CAF>/hive consent remove [droneID/Player] <color:gray>-</color> <color:#ff66ff>Removes a registered consented user", player);
        MessageUtil.sendMessage("<color:#967CAF>/hive consent list <color:gray>-</color> <color:#ff66ff>Shows list of all consented users", player);
        MessageUtil.sendMessage("<reset>                                                                                      ", player);
    }

    @Default
    @Subcommand("register")
    @Syntax("<+tag> [droneID]")
    @Description("Register yourself into the hive with your unique id")
    public static void onRegister(Player player, String[] args) {
        if (args.length != 1) {
            MessageUtil.sendMessage(MessageValue.COMMAND_HIVE_CODE_VALIDATION.getMessage(), player);
        }
        if (args.length == 1) {
            if (!isInteger(args[0])) {
                MessageUtil.sendMessage(MessageValue.COMMAND_HIVE_CODE_VALIDATION.getMessage(), player);
                return;
            }
            Drone drone = core.getDroneDataController().getDrone(player.getUniqueId().toString());
            if(isInteger(args[0])) {
                if (!drone.isActive()) {
                    drone.setActive(true);
                    core.getDroneDataController().updateDrone(drone);
                    MessageUtil.sendMessage(MessageValue.COMMAND_HIVE_WELCOME_BACK.getMessage().replace("%droneId%",
                            drone.getId()), player);
                    return;
                } else {
                    MessageUtil.sendMessage(MessageValue.COMMAND_HIVE_ALREADY_DRONE.getMessage(), player);
                    return;
                }
            }
            if (core.getDroneDataController().getDrone((player).getUniqueId().toString()) != null && core.getDroneDataController().isIdUsed(Integer.parseInt(args[0]))) {
                MessageUtil.sendMessage(MessageValue.COMMAND_HIVE_ALREADY_REGISTERED.getMessage().replace("%droneId%", args[0]), player);
                return;
            }
            MessageUtil.sendMessage(MessageValue.COMMAND_HIVE_WELCOME.getMessage().replace("%droneId%", args[0]), player);
            HexCore.getHexCore().getDroneDataController().createNewDrone(player, Integer.parseInt(args[0]));
        }
    }

    @Default
    @Subcommand("consent invite")
    @Syntax("<+tag> <player>")
    @CommandCompletion("@players")
    @Description("Register yourself into the hive with your unique id")
    public static void onHiveConsentInvite(Player player, String[] args){
        if(args[0].equalsIgnoreCase(player.getName())){
            MessageUtil.sendMessage(MessageValue.COMMAND_CONSENT_SELF_USER.getMessage()
                    .replace("%player%", args[0]), player);
            return;
        }
        if(Bukkit.getPlayer(args[0]) == null) {
            MessageUtil.sendMessage(MessageValue.COMMAND_HIVE_PLAYER_NOT_FOUND.getMessage()
                    .replace("%player%", args[0]), player);
            return;
        }

        //Check if targeted person is already trusted
        if(HexCore.getHexCore().getConsentDataController().isConsented(player, Bukkit.getPlayer(args[0]).getUniqueId().toString())){
            MessageUtil.sendMessage(MessageValue.COMMAND_CONSENT_ALREADY_TRUSTED.getMessage()
                    .replace("%player%", args[0]), player);
            return;
        }

        if(consentInvite.get(player.getUniqueId()) != null) {
            if(consentInvite.get(player.getUniqueId()).equals(Bukkit.getPlayer(args[0]).getUniqueId())){
                MessageUtil.sendMessage(MessageValue.COMMAND_CONSENT_INVITE_ALREADY_SENT.getMessage(), player);
            }
        }else{
            consentInvite.put(player.getUniqueId(), Bukkit.getPlayer(args[0]).getUniqueId());
            MessageUtil.sendMessage(MessageValue.COMMAND_CONSENT_INVITE_TARGET.getMessage()
                    .replace("%player%", player.getName()), Bukkit.getPlayer(args[0]));
            MessageUtil.sendMessage(MessageValue.COMMAND_CONSENT_INVITE.getMessage()
                    .replace("%player%", args[0]), player);
        }
    }

    @Default
    @Subcommand("consent remove")
    @Syntax("<+tag> <player>")
    @Description("Register yourself into the hive with your unique id")
    public static void onHiveConsentRemove(Player player, String[] args){
        if(Bukkit.getPlayer(args[0]) == null){
            MessageUtil.sendMessage(MessageValue.COMMAND_HIVE_PLAYER_NOT_FOUND.getMessage()
                    .replace("%player%", args[0]), player);
        }else{
            if(!core.getConsentDataController().isConsented(player, Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString())){
                MessageUtil.sendMessage(MessageValue.COMMAND_CONSENT_NOT_IN_LIST.getMessage()
                        .replace("%player%", args[0]), player);
            }else{
                MessageUtil.sendMessage(MessageValue.COMMAND_CONSENT_REMOVED.getMessage()
                        .replace("%player%", args[0]), player);
                core.getConsentDataController().removeConsent(player.getUniqueId().toString(),
                        Bukkit.getPlayer(args[0]).getUniqueId().toString());
            }
        }
    }

    @Default
    @Subcommand("consent accept")
    @Syntax("<+tag> <player>")
    @Description("Register yourself into the hive with your unique id")
    public static void onHiveConsentAccept(Player player, String[] args){
        if(Bukkit.getPlayer(args[0]) == null){
            MessageUtil.sendMessage(MessageValue.COMMAND_HIVE_PLAYER_NOT_FOUND.getMessage().replace("%player%", args[0]), player);
            return;
        }

        //Check there are existing pending invites running
        if(consentInvite.get(Bukkit.getPlayer(args[0]).getUniqueId()) != null) {
            if(consentInvite.get(Bukkit.getPlayer(args[0]).getUniqueId()).equals(player.getUniqueId())) {
                HexCore.getHexCore().getConsentDataController().createNewConsent(Bukkit.getPlayer(args[0]),
                        player.getUniqueId().toString());
                MessageUtil.sendMessage(MessageValue.COMMAND_CONSENT_ACCEPTED_TARGET.getMessage().replace("%player%", player.getName()), Bukkit.getPlayer(args[0]));
                MessageUtil.sendMessage(MessageValue.COMMAND_CONSENT_ACCEPTED.getMessage().replace("%player%", player.getName()), player);
                consentInvite.remove(Bukkit.getPlayer(args[0]).getUniqueId());

            }
            }else{
            MessageUtil.sendMessage(MessageValue.COMMAND_CONSENT_INVITE_NOT_FOUND.getMessage().replace("%player%", player.getName()), player);
        }
    }

    @Default
    @Subcommand("consent list")
    @Syntax("<+tag> <player>")
    @Description("Register yourself into the hive with your unique id")
    public static void onHiveConsentList(Player player, String[] args){

    }

    //Check if an input is a valid int
    private static boolean isInteger(String input){
        try{
            int num = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
