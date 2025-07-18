package care.cuddliness.hexcorp.hexcore.listener;

import care.cuddliness.hexcorp.hexcore.HexCore;
import care.cuddliness.hexcorp.hexcore.database.controller.DroneController;
import care.cuddliness.hexcorp.hexcore.message.ChatFormat;
import care.cuddliness.hexcorp.hexcore.message.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

public class PlayerLeaveListener implements Listener {

    private final DroneController controller;

    public PlayerLeaveListener(DroneController controller) {
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
