package com.rsscripting.mobstand.tasks;

import com.rsscripting.mobstand.RSMobStandPlugin;
import com.rsscripting.mobstand.managers.StandManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.Location;

import java.util.UUID;
import java.util.List;

public class SlaughterTask extends BukkitRunnable {

    private final RSMobStandPlugin plugin;

    private final StandManager standManager;

    public SlaughterTask(RSMobStandPlugin plugin) {

        this.plugin = plugin;

        this.standManager = plugin.getStandManager();

    }

    @Override
    public void run() {

        long start = System.nanoTime();

        boolean debugEnabled =
                plugin.getConfig().getBoolean("debug.enabled");

        boolean performanceLogging =
                plugin.getConfig().getBoolean(
                        "debug.logging.performance"
                );

        boolean entityKillLogging =
                plugin.getConfig().getBoolean(
                        "debug.logging.entity-kills"
                );

        Integer killedThisCycle =
                entityKillLogging ? 0 : null;

        Location lastStandLocation = null;

        var configManager = plugin.getConfigManager();

        var adultMobs =
                configManager
                        .getAdultMobData()
                        .keySet();

        var babyMobs =
                configManager
                        .getBabyMobData()
                        .keySet();

        for (UUID uuid : standManager.getActiveStands()) {

            Entity entity = Bukkit.getEntity(uuid);

// Stand no longer exists
            if (!(entity instanceof ArmorStand armorStand)) {
                continue;
            }

// Skip paused stands
            if (standManager.isPaused(armorStand)) {
                continue;
            }

// Scan nearby entities
            double radius = standManager.getRadius(armorStand);

            int leaveAlive =
                    standManager.getLeaveAlive(
                            armorStand
                    );

            lastStandLocation =
                    armorStand.getLocation();

            java.util.Map<String, List<LivingEntity>> adultTargets =
                    new java.util.HashMap<>();

            java.util.Map<String, List<LivingEntity>> babyTargets =
                    new java.util.HashMap<>();

            List<LivingEntity> killAllTargets =
                    new java.util.ArrayList<>();

            for (Entity nearby : armorStand.getNearbyEntities(
                    radius,
                    radius,
                    radius
            )) {

// Ignore players
                if (nearby instanceof Player) {
                    continue;
                }

// Ignore armor stands
                if (nearby instanceof ArmorStand) {
                    continue;
                }

// Must be living entity
                if (!(nearby instanceof LivingEntity livingEntity)) {
                    continue;
                }

                EntityType entityType = livingEntity.getType();

                String entityName = entityType.name();

                boolean killAll = false;

                boolean shouldKill;

// Ageable mobs
                if (livingEntity instanceof Ageable ageable) {

// Adult mob
                    if (ageable.isAdult()) {

                        List<String> disabledAdultMobs =
                                standManager.getDisabledAdultMobs(
                                        armorStand
                                );

                        shouldKill =
                                !disabledAdultMobs.contains(
                                        entityName
                                );

                        if (configManager
                                .getAdultMobData()
                                .containsKey(entityName)) {

                            killAll =
                                    configManager
                                            .getAdultMobData()
                                            .get(entityName)
                                            .isKillAll();

                        }

                    }

// Baby mob
                    else {

                        List<String> disabledBabyMobs =
                                standManager.getDisabledBabyMobs(
                                        armorStand
                                );

                        shouldKill =
                                !disabledBabyMobs.contains(
                                        entityName
                                );

                        if (configManager
                                .getBabyMobData()
                                .containsKey(entityName)) {

                            killAll =
                                    configManager
                                            .getBabyMobData()
                                            .get(entityName)
                                            .isKillAll();

                        }

                    }

                }

// Non-ageable mobs
                else {

                    List<String> disabledAdultMobs =
                            standManager.getDisabledAdultMobs(
                                    armorStand
                            );

                    shouldKill =
                            !disabledAdultMobs.contains(
                                    entityName
                            );

                    if (configManager
                            .getAdultMobData()
                            .containsKey(entityName)) {

                        killAll =
                                configManager
                                        .getAdultMobData()
                                        .get(entityName)
                                        .isKillAll();

                    }

                }

// Not allowed
                if (!shouldKill) {
                    continue;
                }

// Ignore named mobs
                if (livingEntity.customName() != null) {
                    continue;
                }

                if (killAll) {

                    killAllTargets.add(livingEntity);

                } else {

                    if (livingEntity instanceof Ageable ageable) {

                        if (ageable.isAdult()) {

                            adultTargets
                                    .computeIfAbsent(
                                            entityName,
                                            k -> new java.util.ArrayList<>()
                                    )
                                    .add(livingEntity);

                        } else {

                            babyTargets
                                    .computeIfAbsent(
                                            entityName,
                                            k -> new java.util.ArrayList<>()
                                    )
                                    .add(livingEntity);

                        }

                    } else {

                        adultTargets
                                .computeIfAbsent(
                                        entityName,
                                        k -> new java.util.ArrayList<>()
                                )
                                .add(livingEntity);

                    }

                }

// Damage amount
                double damage =
                        plugin.getConfig()
                                .getDouble(
                                        "damage-amount"
                                );

// Kill all targets first
                for (LivingEntity target : killAllTargets) {

                    target.damage(damage);

                    if (entityKillLogging) {
                        killedThisCycle++;
                    }

                }

// Leave alive logic
// Adult targets
                for (List<LivingEntity> mobList : adultTargets.values()) {

                    int allowedKills =
                            mobList.size() - leaveAlive;

                    if (allowedKills > 0) {

                        for (int i = 0; i < allowedKills; i++) {

                            LivingEntity target =
                                    mobList.get(i);

                            target.damage(damage);

                            if (entityKillLogging) {
                                killedThisCycle++;
                            }

                        }

                    }

                }

// Baby targets
                for (List<LivingEntity> mobList : babyTargets.values()) {

                    int allowedKills =
                            mobList.size() - leaveAlive;

                    if (allowedKills > 0) {

                        for (int i = 0; i < allowedKills; i++) {

                            LivingEntity target =
                                    mobList.get(i);

                            target.damage(damage);

                            if (entityKillLogging) {
                                killedThisCycle++;
                            }

                        }

                    }

                }

            }

        }

        long duration = System.nanoTime() - start;
        double ms = duration / 1_000_000.0;

        if (debugEnabled && performanceLogging) {

            if (ms >= 5.0) {

                Location location =
                        lastStandLocation;

                if (location == null) {
                    return;
                }

                plugin.getLogger().warning(
                        "Slow cycle: "
                                + String.format("%.2f", ms)
                                + "ms"
                                + (
                                entityKillLogging
                                        ? " | Killed: " + killedThisCycle
                                        : ""
                        )
                                + " | "
                                + location.getWorld().getName()
                                + " "
                                + location.getBlockX()
                                + " "
                                + location.getBlockY()
                                + " "
                                + location.getBlockZ()
                );

            }

        }

    }

}