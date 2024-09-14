package care.cuddliness.hex.listener;

import care.cuddliness.hex.HexCore;
import care.cuddliness.hex.database.controller.DroneDataController;
import care.cuddliness.hex.database.model.Drone;
import care.cuddliness.hex.message.ChatFormat;
import care.cuddliness.hex.message.MessageUtil;
import care.cuddliness.hex.utils.NameUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class PlayerLeaveListener implements Listener {

    private final DroneDataController controller;

    public PlayerLeaveListener(DroneDataController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if (controller.getDrone(e.getPlayer().getUniqueId().toString()) != null &&
                controller.getDrone(e.getPlayer().getUniqueId().toString()).isActive()) {
            if (HexCore.getHexCore().getMainConfigYml().getBoolean("replace_join_message_with_id")) {
                e.setQuitMessage("");
                MessageUtil.broadcast(ChatFormat.PREFIX.getMessage() + " " + ChatFormat.ID.getMessage().replace("%droneid%",
                        String.valueOf(controller.getDrone(e.getPlayer().getUniqueId().toString()).getDroneId())) +
                        " <color:#949292>Left the hive</color>", e.getPlayer().getWorld());
            }
        }
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (scoreboard.getTeam(e.getPlayer().getName()) != null) {
            Objects.requireNonNull(scoreboard.getTeam(e.getPlayer().getName())).unregister();
        }
    }
}
