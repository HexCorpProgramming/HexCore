package care.cuddliness.hex.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if(scoreboard.getTeam(e.getPlayer().getName()) != null){
            Objects.requireNonNull(scoreboard.getTeam(e.getPlayer().getName())).unregister();
        }
    }
}
