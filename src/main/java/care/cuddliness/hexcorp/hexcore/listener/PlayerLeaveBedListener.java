package care.cuddliness.hexcorp.hexcore.listener;

import care.cuddliness.hexcorp.hexcore.HexCore;
import care.cuddliness.hexcorp.hexcore.database.model.Drone;
import care.cuddliness.hexcorp.hexcore.message.MessageUtil;
import care.cuddliness.hexcorp.hexcore.message.MessageValue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class PlayerLeaveBedListener implements Listener {

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        Drone drone = HexCore.getHexCore().getDroneDataController().getDrone(event.getPlayer().getUniqueId().toString());
        if (drone != null && drone.isBatteryStatus() && event.getPlayer().getWorld().getTime() >= 0) {
            MessageUtil.sendMessage(MessageValue.BATTERY_CHARGED.getMessage(), event.getPlayer());
            drone.setBatteryCapacity(100);
            HexCore.getHexCore().getDroneDataController().updateDrone(drone);
        }
    }
}
