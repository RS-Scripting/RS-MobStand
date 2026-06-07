package com.rsscripting.mobstand.managers;

import com.rsscripting.mobstand.RSMobStandPlugin;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

public class StandManager {

    private final RSMobStandPlugin plugin;

    private final NamespacedKey mobStandKey;

    private final NamespacedKey ownerKey;

    private final NamespacedKey radiusKey;

    private final NamespacedKey gravityKey;

    private final NamespacedKey pausedKey;

    private final NamespacedKey disabledAdultMobsKey;

    private final NamespacedKey disabledBabyMobsKey;

    private final Map<UUID, UUID> selectedArmorStands = new HashMap<>();

    private final NamespacedKey leaveAliveKey;

    private final Set<UUID> activeStands = new HashSet<>();

    public StandManager(RSMobStandPlugin plugin) {

        this.plugin = plugin;

        this.mobStandKey = new NamespacedKey(plugin, "mobstand");

        this.ownerKey = new NamespacedKey(plugin, "owner");

        this.radiusKey = new NamespacedKey(plugin, "radius");

        this.leaveAliveKey = new NamespacedKey(plugin, "leaveAlive");

        this.gravityKey = new NamespacedKey(plugin, "gravity");

        this.pausedKey = new NamespacedKey(plugin, "paused");

        this.disabledAdultMobsKey =
                new NamespacedKey(
                        plugin,
                        "disabled_adult_mobs"
                );

        this.disabledBabyMobsKey =
                new NamespacedKey(
                        plugin,
                        "disabled_baby_mobs"
                );

    }

// counts stands on server.
    public int getPlayerStandCount(UUID ownerUUID) {

        int count = 0;

        for (UUID standUUID : activeStands) {

            Entity entity =
                    Bukkit.getEntity(standUUID);

            if (!(entity instanceof ArmorStand armorStand)) {
                continue;
            }

            UUID standOwner =
                    getOwnerUUID(armorStand);

            if (ownerUUID.equals(standOwner)) {
                count++;
            }

        }

        return count;

    }

// Converts an armor stand into a MobStand.

    public boolean convertToMobStand(ArmorStand armorStand, Player owner) {

        if (!isArmorStandEmpty(armorStand)) {

            owner.sendMessage(
                    ChatColor.RED
                            + "Remove all equipment first."
            );

            return false;

        }

        if (!owner.hasPermission(
                "rsmobstand.bypasslimits"
        )) {

// Server limit
            int maxTotalStands =
                    plugin.getConfig()
                            .getInt(
                                    "max-total-stands"
                            );

            if (maxTotalStands > 0 &&
                    activeStands.size()
                            >= maxTotalStands) {

                owner.sendMessage(
                        ChatColor.RED
                                + "Server mob stand limit reached."
                );

                return false;

            }

// Player limit
            int maxPlayerStands =
                    plugin.getConfig()
                            .getInt(
                                    "max-stands-per-player"
                            );

            int playerStandCount =
                    getPlayerStandCount(
                            owner.getUniqueId()
                    );

            if (maxPlayerStands > 0 &&
                    playerStandCount
                            >= maxPlayerStands) {

                owner.sendMessage(
                        ChatColor.RED
                                + "You have reached your mob stand limit."
                );

                return false;

            }

        }

        armorStand.getPersistentDataContainer().set(
                pausedKey,
                PersistentDataType.BYTE,
                (byte) 0
        );

        armorStand.getPersistentDataContainer().set(
                mobStandKey,
                PersistentDataType.BYTE,
                (byte) 1
        );

        armorStand.getPersistentDataContainer().set(
                ownerKey,
                PersistentDataType.STRING,
                owner.getUniqueId().toString()
        );

        double defaultRadius = plugin.getConfig().getDouble(
                "default-scan-radius"
        );

        armorStand.getPersistentDataContainer().set(
                radiusKey,
                PersistentDataType.DOUBLE,
                defaultRadius
        );

        boolean defaultGravity = plugin.getConfig().getBoolean(
                "default-gravity"
        );

        int defaultLeaveAlive =
                plugin.getConfig().getInt(
                        "default-leave-alive"
                );

        armorStand.getPersistentDataContainer().set(
                leaveAliveKey,
                PersistentDataType.INTEGER,
                defaultLeaveAlive
        );

        armorStand.getPersistentDataContainer().set(
                gravityKey,
                PersistentDataType.BYTE,
                (byte) (defaultGravity ? 1 : 0)
        );

        armorStand.setGravity(defaultGravity);

        armorStand.setCustomName("§cRS MobStand");
        armorStand.setCustomNameVisible(true);
        armorStand.setInvulnerable(true);
        activeStands.add(armorStand.getUniqueId());

        // Initialize adult disabled filters
        List<String> disabledAdultMobs =
                new ArrayList<>();

        plugin.getConfigManager()
                .getAdultMobData()
                .forEach((mobName, data) -> {

                    if (!data.isEnabledByDefault()) {

                        disabledAdultMobs.add(
                                mobName
                        );

                    }

                });

        setDisabledAdultMobs(
                armorStand,
                disabledAdultMobs
        );

// Initialize baby disabled filters
        List<String> disabledBabyMobs =
                new ArrayList<>();

        plugin.getConfigManager()
                .getBabyMobData()
                .forEach((mobName, data) -> {

                    if (!data.isEnabledByDefault()) {

                        disabledBabyMobs.add(
                                mobName
                        );

                    }

                });

        setDisabledBabyMobs(
                armorStand,
                disabledBabyMobs
        );

        return true;

    }


// Removes MobStand data.

    public void revertToNormalStand(ArmorStand armorStand) {

        armorStand.getPersistentDataContainer().remove(mobStandKey);

        armorStand.getPersistentDataContainer().remove(radiusKey);

        armorStand.getPersistentDataContainer().remove(gravityKey);

        armorStand.getPersistentDataContainer().remove(pausedKey);

        armorStand.getPersistentDataContainer().remove(leaveAliveKey);

        armorStand.getPersistentDataContainer().remove(disabledAdultMobsKey);

        armorStand.getPersistentDataContainer().remove(disabledBabyMobsKey);

        clearOwnership(armorStand);

        activeStands.remove(armorStand.getUniqueId());

        armorStand.setInvulnerable(false);

        armorStand.setCustomName(null);

        armorStand.setCustomNameVisible(false);

        armorStand.setGravity(true);

    }


// Checks if armor stand is a MobStand.

    public boolean isMobStand(ArmorStand armorStand) {

        Byte value = armorStand.getPersistentDataContainer().get(
                mobStandKey,
                PersistentDataType.BYTE
        );

        return value != null && value == (byte) 1;

    }


// Stores selected armor stand for player.

    public void setSelectedArmorStand(UUID playerUUID, UUID armorStandUUID) {

        selectedArmorStands.put(playerUUID, armorStandUUID);

    }


// Gets selected armor stand UUID.

    public UUID getSelectedArmorStand(UUID playerUUID) {

        return selectedArmorStands.get(playerUUID);

    }


// Removes selected armor stand tracking.

    public void clearSelectedArmorStand(UUID playerUUID) {

        selectedArmorStands.remove(playerUUID);

    }


// Checks if player owns this stand.

    public boolean isOwner(ArmorStand armorStand, Player player) {

        String ownerUUID = armorStand.getPersistentDataContainer().get(
                ownerKey,
                PersistentDataType.STRING
        );

        if (ownerUUID == null) {
            return false;
        }

        return ownerUUID.equals(player.getUniqueId().toString());

    }

    public UUID getOwnerUUID(
            ArmorStand armorStand
    ) {

        String ownerUUID =
                armorStand
                        .getPersistentDataContainer()
                        .get(
                                ownerKey,
                                PersistentDataType.STRING
                        );

        if (ownerUUID == null) {
            return null;
        }

        return UUID.fromString(ownerUUID);

    }


// Removes ownership data.

    public void clearOwnership(ArmorStand armorStand) {

        armorStand.getPersistentDataContainer().remove(ownerKey);

    }


// Gets all active Mob stands.

    public Set<UUID> getActiveStands() {

        return activeStands;

    }


// Reloads all MobStands from loaded worlds.

    public void loadExistingStands() {

        activeStands.clear();

        for (World world : Bukkit.getWorlds()) {

            for (Entity entity : world.getEntities()) {

// Must be armor stand
                if (!(entity instanceof ArmorStand armorStand)) {
                    continue;
                }

                Byte storedValue = armorStand.getPersistentDataContainer().get(
                        mobStandKey,
                        PersistentDataType.BYTE
                );

// Must be MobStand
                if (!isMobStand(armorStand)) {
                    continue;
                }

// Restore invulnerability
                armorStand.setInvulnerable(true);

// Re-register stand
                activeStands.add(armorStand.getUniqueId());

            }

        }

        plugin.getLogger().info(
                "Recovered "
                        + activeStands.size()
                        + " MobStands."
        );

    }

    public int getLeaveAlive(
            ArmorStand armorStand
    ) {

        Integer leaveAlive =
                armorStand
                        .getPersistentDataContainer()
                        .get(
                                leaveAliveKey,
                                PersistentDataType.INTEGER
                        );

        if (leaveAlive == null) {

            return plugin.getConfig().getInt(
                    "default-leave-alive"
            );

        }

        return leaveAlive;

    }

    public double getRadius(ArmorStand armorStand) {

        Double radius = armorStand.getPersistentDataContainer().get(
                radiusKey,
                PersistentDataType.DOUBLE
        );

        if (radius == null) {

            return plugin.getConfig().getDouble(
                    "default-scan-radius"
            );

        }

        return radius;

    }

    public void setRadius(
            ArmorStand armorStand,
            double radius
    ) {

        double maxRadius = plugin.getConfig().getDouble(
                "max-player-scan-radius"
        );

// Clamp radius
        if (radius < 0) {
            radius = 0;
        }

        if (radius > maxRadius) {
            radius = maxRadius;
        }

        armorStand.getPersistentDataContainer().set(
                radiusKey,
                PersistentDataType.DOUBLE,
                radius
        );

    }

    public void setLeaveAlive(
            ArmorStand armorStand,
            int leaveAlive
    ) {

        int minLeaveAlive =
                plugin.getConfig()
                        .getInt(
                                "min-leave-alive"
                        );

        int maxLeaveAlive =
                plugin.getConfig()
                        .getInt(
                                "max-leave-alive"
                        );

// Clamp leaveAlive
        if (leaveAlive < minLeaveAlive) {
            leaveAlive = minLeaveAlive;
        }

        if (leaveAlive > maxLeaveAlive) {
            leaveAlive = maxLeaveAlive;
        }

        armorStand.getPersistentDataContainer().set(
                leaveAliveKey,
                PersistentDataType.INTEGER,
                leaveAlive
        );

    }

    public boolean hasGravity(ArmorStand armorStand) {

        Byte value = armorStand.getPersistentDataContainer().get(
                gravityKey,
                PersistentDataType.BYTE
        );

        if (value == null) {

            return plugin.getConfig().getBoolean(
                    "default-gravity"
            );

        }

        return value == (byte) 1;

    }

    public void setGravity(
            ArmorStand armorStand,
            boolean gravity
    ) {

        armorStand.getPersistentDataContainer().set(
                gravityKey,
                PersistentDataType.BYTE,
                (byte) (gravity ? 1 : 0)
        );

        armorStand.setGravity(gravity);

    }

    public boolean isPaused(ArmorStand armorStand) {

        Byte value = armorStand.getPersistentDataContainer().get(
                pausedKey,
                PersistentDataType.BYTE
        );

        if (value == null) {
            return false;
        }

        return value == (byte) 1;

    }

    public void setPaused(
            ArmorStand armorStand,
            boolean paused
    ) {

        armorStand.getPersistentDataContainer().set(
                pausedKey,
                PersistentDataType.BYTE,
                (byte) (paused ? 1 : 0)
        );

    }

// Get disabled Adult Mobs
    public List<String> getDisabledAdultMobs(
            ArmorStand armorStand
    ) {

        String stored =
                armorStand
                        .getPersistentDataContainer()
                        .get(
                                disabledAdultMobsKey,
                                PersistentDataType.STRING
                        );

        if (stored == null || stored.isEmpty()) {

            return new ArrayList<>();

        }

        return new ArrayList<>(
                Arrays.asList(
                        stored.split(",")
                )
        );

    }

    public List<String> getDisabledBabyMobs(
            ArmorStand armorStand
    ) {

        String stored =
                armorStand
                        .getPersistentDataContainer()
                        .get(
                                disabledBabyMobsKey,
                                PersistentDataType.STRING
                        );

        if (stored == null || stored.isEmpty()) {

            return new ArrayList<>();

        }

        return new ArrayList<>(
                Arrays.asList(
                        stored.split(",")
                )
        );

    }

    public void setDisabledAdultMobs(
            ArmorStand armorStand,
            List<String> mobs
    ) {

        String stored =
                String.join(",", mobs);

        armorStand
                .getPersistentDataContainer()
                .set(
                        disabledAdultMobsKey,
                        PersistentDataType.STRING,
                        stored
                );

    }

    public void setDisabledBabyMobs(
            ArmorStand armorStand,
            List<String> mobs
    ) {

        String stored =
                String.join(",", mobs);

        armorStand
                .getPersistentDataContainer()
                .set(
                        disabledBabyMobsKey,
                        PersistentDataType.STRING,
                        stored
                );

    }

    public void toggleAdultMobFilter(
            ArmorStand armorStand,
            String mobName
    ) {

        List<String> disabled =
                getDisabledAdultMobs(
                        armorStand
                );

        if (disabled.contains(mobName)) {

            disabled.remove(mobName);

        } else {

            disabled.add(mobName);

        }

        setDisabledAdultMobs(
                armorStand,
                disabled
        );

    }

    private boolean isArmorStandEmpty(
            ArmorStand armorStand
    ) {

        return armorStand.getEquipment()
                .getHelmet()
                .getType()
                .isAir()

                && armorStand.getEquipment()
                .getChestplate()
                .getType()
                .isAir()

                && armorStand.getEquipment()
                .getLeggings()
                .getType()
                .isAir()

                && armorStand.getEquipment()
                .getBoots()
                .getType()
                .isAir()

                && armorStand.getEquipment()
                .getItemInMainHand()
                .getType()
                .isAir()

                && armorStand.getEquipment()
                .getItemInOffHand()
                .getType()
                .isAir();

    }

    public void toggleBabyMobFilter(
            ArmorStand armorStand,
            String mobName
    ) {

        List<String> disabled =
                getDisabledBabyMobs(
                        armorStand
                );

        if (disabled.contains(mobName)) {

            disabled.remove(mobName);

        } else {

            disabled.add(mobName);

        }

        setDisabledBabyMobs(
                armorStand,
                disabled
        );

    }

}