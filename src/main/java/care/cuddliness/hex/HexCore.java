package care.cuddliness.hex;

import care.cuddliness.hex.command.commands.drone.DroneCommand;
import care.cuddliness.hex.command.commands.hive.HiveCommand;
import care.cuddliness.hex.database.DronePersistenceUnit;
import care.cuddliness.hex.database.controller.ConsentDataController;
import care.cuddliness.hex.database.controller.DroneDataController;
import care.cuddliness.hex.database.controller.DroneSettingsDataController;
import care.cuddliness.hex.listener.PlayerChatListener;
import care.cuddliness.hex.listener.PlayerJoinListener;
import care.cuddliness.hex.listener.PlayerLeaveBedListener;
import care.cuddliness.hex.listener.PlayerLeaveListener;
import care.cuddliness.hex.statuscode.StatuscodeHandler;
import care.cuddliness.hex.task.DaylightCycle;
import care.cuddliness.hex.utils.ThoughtDenial;
import co.aikar.commands.PaperCommandManager;
import dev.dejvokep.boostedyaml.YamlDocument;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

public class HexCore extends JavaPlugin {
    private @Getter YamlDocument messageYaml;
    private @Getter YamlDocument mainConfigYml;
    private @Getter EntityManagerFactory entityManager;
    private @Getter DroneDataController droneDataController;
    private @Getter DroneSettingsDataController droneSettingsDataController;
    private @Getter ConsentDataController consentDataController;
    private @Getter StatuscodeHandler statuscodeHandler;
    private @Getter PaperCommandManager commandManager;
    private @Getter ThoughtDenial thoughtDenial;
    private BukkitAudiences miniMessage;
    @Getter private static HexCore hexCore;

    @Override
    public void onEnable() {
        hexCore = this;
        System.setProperty("log4j.configuration", "resources/log4j.properties");
        //Config stuff
        try {
            this.messageYaml = YamlDocument.create(new File(getDataFolder(), "messages.yml"), getResource("messages.yml"));
            this.mainConfigYml = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Registering database controllers
        this.entityManager = new HibernatePersistenceProvider().createContainerEntityManagerFactory(new DronePersistenceUnit(), new HashMap());
        this.droneDataController = new DroneDataController(entityManager);
        this.droneSettingsDataController = new DroneSettingsDataController(entityManager);
        this.consentDataController = new ConsentDataController(entityManager);

        //Register Command stuff
        this.commandManager = new PaperCommandManager(this);
        this.commandManager.registerCommand(new HiveCommand(this));
        this.commandManager.registerCommand(new DroneCommand(this));

        //Adding instance for minimessages aka fancy colors
        this.miniMessage = BukkitAudiences.create(this);

        //Registering Thought Denial
        this.thoughtDenial = new ThoughtDenial(this);
        //Register event stuff
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this.droneDataController), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveBedListener(), this);

        this.statuscodeHandler = new StatuscodeHandler();
        new DaylightCycle(Bukkit.getWorld("world")).runTaskTimerAsynchronously(this, 0, 100);

        Bukkit.getLogger().log(Level.INFO, "Hexcore Enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO, "Hexcore Disabled");
        if(this.miniMessage != null) {
            this.miniMessage.close();
            this.miniMessage = null;
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for(Team t : scoreboard.getTeams()){
            t.unregister();
        }
    }

    public @NonNull BukkitAudiences miniMessage() {
        if(this.miniMessage == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.miniMessage;
    }

    //2703 was here *beep*
}
