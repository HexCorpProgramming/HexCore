package care.cuddliness.hexcorp.hexcore.listener;

import care.cuddliness.hexcorp.hexcore.HexCore;
import care.cuddliness.hexcorp.hexcore.database.controller.DroneController;
import care.cuddliness.hexcorp.hexcore.database.model.Drone;
import care.cuddliness.hexcorp.hexcore.message.ChatFormat;
import care.cuddliness.hexcorp.hexcore.message.MessageUtil;
import care.cuddliness.hexcorp.hexcore.utils.NameUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerJoinListener implements Listener {
    private final DroneController controller;

    public PlayerJoinListener(DroneController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (controller.getDrone(e.getPlayer().getUniqueId().toString()) != null &&
                controller.getDrone(e.getPlayer().getUniqueId().toString()).isActive()) {
            Drone drone = controller.getDrone(e.getPlayer().getUniqueId().toString());
            e.getPlayer().setDisplayName("HexDrone" + drone.getDroneId());
            NameUtil.changePlayerName(e.getPlayer(), "HexDrone" + drone.getDroneId());
            if (HexCore.getHexCore().getMainConfigYml().getBoolean("replace_join_message_with_id")) {
                e.setJoinMessage("");
                MessageUtil.broadcast(ChatFormat.PREFIX.getMessage() + " " + ChatFormat.ID.getMessage().replace("%droneid%",
                        String.valueOf(controller.getDrone(e.getPlayer().getUniqueId().toString()).getDroneId())) +
                        " <color:#949292>Joined the hive</color>", e.getPlayer().getWorld());
            }
            // Retrieving the main scoreboard.
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            Team team = scoreboard.registerNewTeam(e.getPlayer().getName());
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', "&8&l⬡ &5" + controller.getDrone(e.getPlayer().getUniqueId().toString())
                    .getDroneId() + " &7| &r"));
            team.addEntry(e.getPlayer().getName());
        }
    }

}
