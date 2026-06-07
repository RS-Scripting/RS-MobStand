package com.rsscripting.mobstand;

import com.rsscripting.mobstand.commands.RSMSCommand;
import com.rsscripting.mobstand.commands.RSMSTabCompleter;
import com.rsscripting.mobstand.tasks.SlaughterTask;
import com.rsscripting.mobstand.config.ConfigManager;
import com.rsscripting.mobstand.listeners.ChunkLoadListener;
import com.rsscripting.mobstand.listeners.ArmorStandBreakListener;
import com.rsscripting.mobstand.listeners.ArmorStandListener;
import com.rsscripting.mobstand.listeners.MenuListener;
import com.rsscripting.mobstand.managers.StandManager;
import com.rsscripting.mobstand.utils.VersionChecker;

import org.bukkit.plugin.java.JavaPlugin;

public class RSMobStandPlugin extends JavaPlugin {

    private StandManager standManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {

            saveDefaultConfig();

            this.standManager = new StandManager(this);

            this.configManager = new ConfigManager(this);

            getCommand("rsms").setExecutor(
                    new RSMSCommand(this)
            );

        getCommand("rsms").setTabCompleter(
                new RSMSTabCompleter()
        );

        getServer().getPluginManager().registerEvents(
                new ArmorStandListener(
                        this,
                        standManager
                ),
                this
        );

        getServer().getPluginManager().registerEvents(
                new ArmorStandBreakListener(this),
                this
        );

        getServer().getPluginManager().registerEvents(
                new MenuListener(this),
                this
        );

        long scanInterval = getConfig().getLong("scan-interval");

        new SlaughterTask(this).runTaskTimer(
                this,
                20L,
                scanInterval
        );

        getServer().getPluginManager().registerEvents(
                new ChunkLoadListener(standManager),
                this
        );

        new VersionChecker(this).checkVersion();

        getLogger().info("RS Mob Stand enabled.");

    }

    @Override
    public void onDisable() {

        getLogger().info("RS Mob Stand disabled.");

    }

    public StandManager getStandManager() {

        return standManager;

    }

    public ConfigManager getConfigManager() {

        return configManager;

    }

}