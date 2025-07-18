package care.cuddliness.hexcorp.hexcore.task;

import care.cuddliness.hexcorp.hexcore.HexCore;
import care.cuddliness.hexcorp.hexcore.database.controller.DroneController;
import care.cuddliness.hexcorp.hexcore.database.model.Drone;
import care.cuddliness.hexcorp.hexcore.message.MessageUtil;
import care.cuddliness.hexcorp.hexcore.message.MessageValue;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DaylightCycle extends BukkitRunnable {

    private final World world;
    private final int drainage;
    private final DroneController droneDataController;

    public DaylightCycle(World world) {
        this.world = world;
        this.drainage = HexCore.getHexCore().getMainConfigYml().getInt("battery.drain_on_daycycle");
        this.droneDataController = HexCore.getHexCore().getDroneDataController();
    }

    @Override
    public void run() {
        long Worldtime = world.getTime();
        //Checks if within 5 seconds the day end
        if (Worldtime > 12000 && Worldtime < 12100) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Drone drone = droneDataController.getDrone(player.getUniqueId().toString());
                //Check if drone is on battery
                if (drone != null && drone.isBatteryStatus() && drone.isActive()) {
                    //Checks if battery will drain below zero
                    if (Integer.signum(drone.getBatteryCapacity() - drainage) <= -1) {
                        drone.setBatteryCapacity(0);
                        droneDataController.updateDrone(drone);
                    } else {
                        drone.setBatteryCapacity(drone.getBatteryCapacity() - drainage);
                        droneDataController.updateDrone(drone);
                    }
                    MessageUtil.sendMessage(MessageValue.BATTERY_DISCHARGE.getMessage().replace("%charge%", String.valueOf(drainage)),
                            player);

                }
            }
        }
    }
}
