package care.cuddliness.hex.listener;

import care.cuddliness.hex.HexCore;
import care.cuddliness.hex.database.controller.DroneDataController;
import care.cuddliness.hex.database.model.Drone;
import care.cuddliness.hex.message.ChatFormat;
import care.cuddliness.hex.message.MessageUtil;
import care.cuddliness.hex.utils.NameUtil;
import com.mojang.authlib.GameProfile;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;

public class PlayerJoinListener implements Listener {
    private final DroneDataController controller;

    public PlayerJoinListener(DroneDataController controller) {
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
