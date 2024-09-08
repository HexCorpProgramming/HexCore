package care.cuddliness.hex.task;

import care.cuddliness.hex.HexCore;
import care.cuddliness.hex.database.controller.DroneDataController;
import care.cuddliness.hex.database.model.Drone;
import care.cuddliness.hex.message.MessageUtil;
import care.cuddliness.hex.message.MessageValue;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DaylightCycle extends BukkitRunnable {

    private World world;
    private int drainage;
    private DroneDataController droneDataController;

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
            for(Player player : Bukkit.getOnlinePlayers()){
                Drone drone = droneDataController.getDrone(player.getUniqueId().toString());
                //Check if drone is on battery
                if(drone != null && drone.isBatteryStatus()){
                    //Checks if battery will drain below zero
                    if(Integer.signum(drone.getDroneSettings().getBatteryCapacity() - drainage) <= -1){
                        drone.getDroneSettings().setBatteryCapacity(0);
                        droneDataController.updateDrone(drone);
                    }else{
                        drone.getDroneSettings().setBatteryCapacity(drone.getDroneSettings().getBatteryCapacity() - drainage);
                        droneDataController.updateDrone(drone);
                    }
                    MessageUtil.sendMessage(MessageValue.BATTERY_DISCHARGE.getMessage().replace("%charge%", String.valueOf(drainage)),
                            player);

                }
            }
        }
    }
}
