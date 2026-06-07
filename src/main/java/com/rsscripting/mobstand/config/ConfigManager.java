package com.rsscripting.mobstand.config;

import com.rsscripting.mobstand.RSMobStandPlugin;
import com.rsscripting.mobstand.data.MobFilterData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import java.util.List;

public class ConfigManager {

    private final RSMobStandPlugin plugin;

    private final Map<String, MobFilterData> adultMobData =
            new HashMap<>();

    private final Map<String, MobFilterData> babyMobData =
            new HashMap<>();

    private FileConfiguration adultConfig;

    private FileConfiguration babyConfig;

    public ConfigManager(RSMobStandPlugin plugin) {

        this.plugin = plugin;

        loadConfigs();

    }


// Loads all plugin configs.

    public void loadConfigs() {

        File configFolder = new File(
                plugin.getDataFolder(),
                "configs"
        );

        if (!configFolder.exists()) {

            if (!configFolder.mkdirs()) {

                plugin.getLogger().warning(
                        "Failed to create configs folder."
                );

            }

        }

        loadAdultConfig();
        loadAdultMobData();

        loadBabyConfig();
        loadBabyMobData();

    }


// Loads adult mob config.

    private void loadAdultConfig() {

        File file = new File(
                plugin.getDataFolder(),
                "configs/adult-mobs.yml"
        );

        if (!file.exists()) {

            plugin.saveResource("configs/adult-mobs.yml", false);

        }

        adultConfig = YamlConfiguration.loadConfiguration(file);

    }


//Loads baby mob config.

    private void loadBabyConfig() {

        File file = new File(
                plugin.getDataFolder(),
                "configs/baby-mobs.yml"
        );

        if (!file.exists()) {

            plugin.saveResource("configs/baby-mobs.yml", false);

        }

        babyConfig = YamlConfiguration.loadConfiguration(file);

    }

// Gets allowed adult mobs.

    public void loadBabyMobData() {

        babyMobData.clear();

        ConfigurationSection section =
                babyConfig.getConfigurationSection(
                        "baby-mobs"
                );

        if (section == null) {
            return;
        }

        for (String mobName : section.getKeys(false)) {

            String path =
                    "baby-mobs." + mobName;

            String iconName =
                    babyConfig.getString(
                            path + ".icon"
                    );

            boolean enabled =
                    babyConfig.getBoolean(
                            path + ".enabled-by-default"
                    );

            boolean killAll =
                    babyConfig.getBoolean(
                            path + ".kill-all",
                            false
                    );

            if (iconName == null) {

                plugin.getLogger().warning(
                        "Missing icon for mob: "
                                + mobName
                );

                continue;

            }

            Material icon =
                    Material.matchMaterial(iconName);

            if (icon == null) {

                plugin.getLogger().warning(
                        "Invalid icon material for mob: "
                                + mobName
                );

                continue;

            }

            babyMobData.put(
                    mobName,
                    new MobFilterData(
                            icon,
                            enabled,
                            killAll
                    )
            );

        }

    }

// Gets allowed adult mobs.

    public void loadAdultMobData() {

        adultMobData.clear();

        ConfigurationSection section =
                adultConfig.getConfigurationSection(
                        "adult-mobs"
                );

        if (section == null) {
            return;
        }

        for (String mobName : section.getKeys(false)) {

            String path =
                    "adult-mobs." + mobName;

            String iconName =
                    adultConfig.getString(
                            path + ".icon"
                    );

            boolean enabled =
                    adultConfig.getBoolean(
                            path + ".enabled-by-default"
                    );

            boolean killAll =
                    adultConfig.getBoolean(
                            path + ".kill-all",
                            false
                    );

            if (iconName == null) {

                plugin.getLogger().warning(
                        "Missing icon for mob: "
                                + mobName
                );

                continue;

            }

            Material icon =
                    Material.matchMaterial(iconName);

            if (icon == null) {

                plugin.getLogger().warning(
                        "Invalid icon material for mob: "
                                + mobName
                );

                continue;

            }

            adultMobData.put(
                    mobName,
                    new MobFilterData(
                            icon,
                            enabled,
                            killAll
                    )
            );

        }

    }


// Gets allowed baby mobs.

    public List<String> getBabyMobs() {

        return babyConfig.getStringList("baby-mobs");

    }

    public Map<String, MobFilterData> getAdultMobData() {

        return adultMobData;

    }

    public Map<String, MobFilterData> getBabyMobData() {

        return babyMobData;

    }

}