package care.cuddliness.hex.listener;

import care.cuddliness.hex.HexCore;
import care.cuddliness.hex.database.controller.DroneDataController;
import care.cuddliness.hex.database.model.Drone;
import care.cuddliness.hex.message.ChatFormat;
import care.cuddliness.hex.message.MessageUtil;
import care.cuddliness.hex.message.MessageValue;
import care.cuddliness.hex.statuscode.StatusCode;
import care.cuddliness.hex.utils.ZalgoText;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {


    //I SWEAR THIS IS GONNA NEED A CLEANUP, ITS 12 AM AND DEHYDRATED
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (HexCore.getHexCore().getDroneDataController().getDrone(e.getPlayer().getUniqueId().toString()) != null) {
            Drone drone = HexCore.getHexCore().getDroneDataController().getDrone(e.getPlayer().getUniqueId().toString());
            //Check if only one word has been said
            String codeMessage = e.getMessage().replace("[a-zA-Z\\\\''//..,,s]", "").replace(" ", "");
            String[] args = codeMessage.split("::");
            String argument = "";

            if (args.length > 1) {
                if (isInteger(args[1])) {
                    //Return if said Id is not owned by the drone
                    if (drone.getDroneId() != Integer.parseInt(args[0].replace(" ", ""))) {
                        MessageUtil.sendMessage(MessageValue.COMMAND_HIVE_CODE_VALIDATION.getMessage(), e.getPlayer());
                        return;
                    }
                    //Check the argument is an integer to detect if its an StatusCode ID
                    StatusCode code = HexCore.getHexCore().getStatuscodeHandler()
                            .getStatusCode(args[1]);

                    //Is a status code with argument
                    if (args.length > 2) {
                        //Set argument
                        argument = args[2];
                    }

                    StringBuilder builder = new StringBuilder();
                    for (int i = 4; i < e.getMessage().split(" ").length; i++) {
                        builder.append(e.getMessage().split(" ")[i]).append(" ");
                    }
                    argument = HexCore.getHexCore().getThoughtDenial().denyThoughts(builder.toString());

                    String desc = code.getDescription();
                    if (drone.isTextGlitching()) {
                        desc = ZalgoText.goZalgo(desc, true, false, true, true, true);
                        argument = ZalgoText.goZalgo(HexCore.getHexCore().getThoughtDenial().denyThoughts(argument),
                                true, false, true, true, true);
                    }
                    e.setCancelled(true);
                    MessageUtil.broadcast(determineChatFormat(e.getPlayer(), true).replace("%droneid%",
                                    String.valueOf(HexCore.getHexCore().getDroneDataController()
                                            .getDrone(e.getPlayer().getUniqueId().toString()).getDroneId()))
                            .replace("%code%", code.getCode())
                            .replace("%description%", desc).replace("%argument%", argument), e.getPlayer().getWorld());
                } else {
                    e.setCancelled(true);
                    //Check if is on ID prepend and it want to send a message without status code
                    StringBuilder builder = new StringBuilder();
                    for (int i = 2; i < e.getMessage().split(" ").length; i++) {
                        builder.append(e.getMessage().split(" ")[i]).append(" ");
                    }
                    String result = builder.toString();
                    result = HexCore.getHexCore().getThoughtDenial().denyThoughts(result);
                    if (drone.isTextGlitching())
                        result = ZalgoText.goZalgo(result, true, false, true, true, true);
                    MessageUtil.broadcast(determineChatFormat(e.getPlayer(), false).replace("%droneid%",
                                    String.valueOf(drone.getDroneId())).replace("%argument%", result),
                            e.getPlayer().getWorld());
                }
            } else {
                if (drone.isIdPrepend()) {
                    e.setCancelled(true);
                    MessageUtil.sendMessage(MessageValue.IDPREPEND_REMINDER.getMessage(), e.getPlayer());
                }
            }
        }
    }

    private static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String determineChatFormat(Player player, boolean hasCode) {
        StringBuilder format = new StringBuilder();
        //Add default for every message
        format.append(ChatFormat.PREFIX.getMessage()).append(" ").append(ChatFormat.ID.getMessage()).append(" ");
        DroneDataController controller = HexCore.getHexCore().getDroneDataController();
        Drone drone = controller.getDrone(player.getUniqueId().toString());
        int level = drone.getDroneSettings().getBatteryCapacity();
        if (drone.isBatteryStatus()) {
            if (level <= 100 && (!(level < 83.4))) {
                format.append(care.cuddliness.hex.message.ChatFormat.BATTERY_1.getMessage()).append(" ");
            } else if (level <= 83.4 && (!(level < 66.8))) {
                format.append(care.cuddliness.hex.message.ChatFormat.BATTERY_2.getMessage()).append(" ");
            } else if (level <= 66.8 && (!(level < 50.2))) {
                format.append(care.cuddliness.hex.message.ChatFormat.BATTERY_3.getMessage()).append(" ");
            } else if (level <= 50.2 && (!(level < 33.6))) {
                format.append(care.cuddliness.hex.message.ChatFormat.BATTERY_4.getMessage()).append(" ");
            } else if (level <= 33.6 && (!(level < 17))) {
                format.append(care.cuddliness.hex.message.ChatFormat.BATTERY_5.getMessage()).append(" ");
            } else if (level <= 17 && (!(level < 1))) {
                format.append(care.cuddliness.hex.message.ChatFormat.BATTERY_6.getMessage()).append(" ");
            } else if (level == 0) {
                format.append(care.cuddliness.hex.message.ChatFormat.BATTERY_7.getMessage()).append(" ");
            }
        }
        if (hasCode) {
            format.append(ChatFormat.SPACER.getMessage()).append(" ").append(ChatFormat.CODE.getMessage()).append(" ").
                    append(ChatFormat.SPACER.getMessage()).append(" ").
                    append(ChatFormat.DESCRIPTION.getMessage()).append(" ").append(ChatFormat.SPACER.getMessage()).
                    append(" ").append(ChatFormat.ARGUMENT.getMessage());
        } else {
            format.append(ChatFormat.SPACER.getMessage()).append(" ").append(ChatFormat.ARGUMENT.getMessage());
        }
        return format.toString();
    }

}
