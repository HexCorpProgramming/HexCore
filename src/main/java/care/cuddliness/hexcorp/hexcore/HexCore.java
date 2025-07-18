package care.cuddliness.hexcorp.hexcore;

import care.cuddliness.hexcorp.hexcore.command.commands.drone.DroneCommand;
import care.cuddliness.hexcorp.hexcore.command.commands.hive.HiveCommand;
import care.cuddliness.hexcorp.hexcore.database.Database;
import care.cuddliness.hexcorp.hexcore.database.controller.ConsentDataController;
import care.cuddliness.hexcorp.hexcore.database.controller.DroneController;
import care.cuddliness.hexcorp.hexcore.listener.PlayerChatListener;
import care.cuddliness.hexcorp.hexcore.listener.PlayerJoinListener;
import care.cuddliness.hexcorp.hexcore.listener.PlayerLeaveBedListener;
import care.cuddliness.hexcorp.hexcore.listener.PlayerLeaveListener;
import care.cuddliness.hexcorp.hexcore.statuscode.StatuscodeHandler;
import care.cuddliness.hexcorp.hexcore.task.DaylightCycle;
import care.cuddliness.hexcorp.hexcore.utils.ThoughtDenial;
import co.aikar.commands.PaperCommandManager;
import dev.dejvokep.boostedyaml.YamlDocument;
import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class HexCore extends JavaPlugin {
    private @Getter YamlDocument messageYaml;
    private @Getter YamlDocument mainConfigYml;
    private @Getter Database database;
    private @Getter DroneController droneDataController;
    private @Getter ConsentDataController consentDataController;
    private @Getter StatuscodeHandler statuscodeHandler;
    private @Getter PaperCommandManager commandManager;
    private @Getter ThoughtDenial thoughtDenial;
    private BukkitAudiences miniMessage;
    @Getter
    private static HexCore hexCore;

    @Override
    public void onEnable() {
        hexCore = this;
        //Config stuff
        try {
            this.messageYaml = YamlDocument.create(new File(getDataFolder(), "messages.yml"), getResource("messages.yml"));
            this.mainConfigYml = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Registering database controllers
        this.database = new Database(mainConfigYml);

        //Register Command stuff
        this.commandManager = new PaperCommandManager(this);
        this.commandManager.registerCommand(new HiveCommand(this));
        this.commandManager.registerCommand(new DroneCommand(this));

        //Registering database controllers
        this.droneDataController = new DroneController(database);
        this.consentDataController = new ConsentDataController(database);

        //Registering listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(droneDataController), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(droneDataController), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveBedListener(), this);

        //Adding instance for minimessages aka fancy colors
        this.miniMessage = BukkitAudiences.create(this);

        //Registering Thought Denial
        this.thoughtDenial = new ThoughtDenial(this);


        this.statuscodeHandler = new StatuscodeHandler();
        new DaylightCycle(Bukkit.getWorld("world")).runTaskTimerAsynchronously(this, 0, 100);

        Bukkit.getLogger().log(Level.INFO, "Hexcore Enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO, "Hexcore Disabled");
        if (this.miniMessage != null) {
            this.miniMessage.close();
            this.miniMessage = null;
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (Team t : scoreboard.getTeams()) {
            t.unregister();
        }
    }

    public @NonNull BukkitAudiences miniMessage() {
        if (this.miniMessage == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.miniMessage;
    }

    //2703 was here *beep*
}
