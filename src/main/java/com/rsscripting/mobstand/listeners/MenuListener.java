package com.rsscripting.mobstand.listeners;

import com.rsscripting.mobstand.RSMobStandPlugin;
import com.rsscripting.mobstand.data.MobFilterData;
import com.rsscripting.mobstand.gui.*;
import com.rsscripting.mobstand.managers.StandManager;
import com.rsscripting.mobstand.gui.RSAdminMenu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;
import java.util.UUID;

public class MenuListener implements Listener {

    private final StandManager standManager;

    private final RSMobStandPlugin plugin;

    public MenuListener(RSMobStandPlugin plugin) {

        this.plugin = plugin;

        this.standManager = plugin.getStandManager();

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        String title = event.getView().getTitle();

// Only listen to inventories
// using RSMenuHolder.
        if (!(event.getInventory()
                .getHolder()
                instanceof RSMenuHolder)) {

            return;

        }

        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "LEAVEALIVE"
                )) {

            event.setCancelled(true);

            UUID standUUID =
                    standManager.getSelectedArmorStand(
                            player.getUniqueId()
                    );

            if (standUUID == null) {

                player.closeInventory();

                return;
            }

            ArmorStand armorStand =
                    (ArmorStand) Bukkit.getEntity(
                            standUUID
                    );

            if (armorStand == null) {

                player.closeInventory();

                return;
            }

            int leaveAlive =
                    standManager.getLeaveAlive(
                            armorStand
                    );

            int slot =
                    event.getRawSlot();

            switch (slot) {

                case 10 -> leaveAlive -= 10;

                case 11 -> leaveAlive -= 5;

                case 12 -> leaveAlive -= 1;

                case 14 -> leaveAlive += 1;

                case 15 -> leaveAlive += 5;

                case 16 -> leaveAlive += 10;

                case 4 -> {

                    StandMenu.open(
                            plugin,
                            player,
                            armorStand
                    );

                    return;
                }

                default -> {
                    return;
                }
            }

            int minLeaveAlive =
                    plugin.getConfig()
                            .getInt(
                                    "min-leave-alive"
                            );

            int maxleaveAlive =
                    plugin.getConfig()
                            .getInt(
                                    "max-leave-alive"
                            );

            leaveAlive =
                    Math.max(
                            minLeaveAlive,
                            Math.min(
                                    maxleaveAlive,
                                    leaveAlive
                            )
                    );

            standManager.setLeaveAlive(
                    armorStand,
                    (int) leaveAlive
            );

            LeaveAliveMenu leaveAliveMenu =
                    new LeaveAliveMenu();

            leaveAliveMenu.open(
                    player,
                    (int) leaveAlive
            );

            return;
        }

        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "RADIUS"
                )) {

            event.setCancelled(true);


            UUID standUUID =
                    standManager.getSelectedArmorStand(
                            player.getUniqueId()
                    );

            if (standUUID == null) {

                player.closeInventory();

                return;
            }

            ArmorStand armorStand =
                    (ArmorStand) Bukkit.getEntity(
                            standUUID
                    );

            if (armorStand == null) {

                player.closeInventory();

                return;
            }

            double radius =
                    standManager.getRadius(
                            armorStand
                    );

            int slot =
                    event.getRawSlot();

            switch (slot) {

                case 10 -> radius -= 10;

                case 11 -> radius -= 5;

                case 12 -> radius -= 1;

                case 14 -> radius += 1;

                case 15 -> radius += 5;

                case 16 -> radius += 10;

                case 4 -> {

                    StandMenu.open(
                            plugin,
                            player,
                            armorStand
                    );

                    return;
                }

                default -> {
                    return;
                }
            }

            int maxRadius =
                    plugin.getConfig()
                            .getInt(
                                    "max-player-scan-radius"
                            );

            radius =
                    Math.max(
                            1.0,
                            Math.min(
                                    maxRadius,
                                    radius
                            )
                    );

            standManager.setRadius(
                    armorStand,
                    (int) radius
            );

            RadiusMenu radiusMenu =
                    new RadiusMenu();

            radiusMenu.open(
                    player,
                    (int) radius
            );

            return;
        }

// Adult Filter Menu
        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "ADULTFILTER"
                )) {

            event.setCancelled(true);

            String[] split =
                    title.split("Page ");

            int currentPage = 0;

            if (split.length > 1) {

                try {

                    currentPage =
                            Integer.parseInt(
                                    split[1]
                            ) - 1;

                } catch (NumberFormatException ignored) {
                }

            }

            UUID standUUID =
                    standManager.getSelectedArmorStand(
                            player.getUniqueId()
                    );

            if (standUUID == null) {

                player.closeInventory();

                return;

            }

            ArmorStand armorStand =
                    (ArmorStand) Bukkit.getEntity(
                            standUUID
                    );

            if (armorStand == null) {

                player.closeInventory();

                return;

            }

            int navigationRow =
                    event.getInventory().getSize() - 9;

            int previousSlot =
                    navigationRow;

            int backSlot =
                    navigationRow + 4;

            int nextSlot =
                    navigationRow + 8;

// Previous page
            if (event.getSlot() == previousSlot) {

                if (currentPage > 0) {

                    AdultFilterMenu.open(
                            plugin,
                            player,
                            armorStand,
                            currentPage - 1
                    );

                }

                return;

            }

// Next page
            if (event.getSlot() == nextSlot) {

                Map<String, MobFilterData> mobData =
                        plugin.getConfigManager()
                                .getAdultMobData();

                int maxPage =
                        (int) Math.ceil(
                                mobData.size() / 45.0
                        ) - 1;

                if (currentPage < maxPage) {

                    AdultFilterMenu.open(
                            plugin,
                            player,
                            armorStand,
                            currentPage + 1
                    );

                }

                return;

            }

// Back button
            if (event.getSlot() == backSlot) {

                StandMenu.open(
                        plugin,
                        player,
                        armorStand
                );

                return;

            }

// Ignore navigation row
            if (event.getSlot() >= navigationRow) {
                return;
            }

            if (event.getCurrentItem() == null ||
                    !event.getCurrentItem().hasItemMeta() ||
                    event.getCurrentItem().getItemMeta() == null ||
                    !event.getCurrentItem().getItemMeta().hasDisplayName()) {

                return;
            }

            String mobName =
                    ChatColor.stripColor(
                            event.getCurrentItem()
                                    .getItemMeta()
                                    .getDisplayName()
                    );

            mobName =
                    mobName.replace(" (Disabled)", "");

            MobFilterData data =
                    plugin.getConfigManager()
                            .getAdultMobData()
                            .get(mobName);

            if (data != null && data.isKillAll()) {

                return;

            }

            standManager.toggleAdultMobFilter(
                    armorStand,
                    mobName
            );

            AdultFilterMenu.open(
                    plugin,
                    player,
                    armorStand,
                    currentPage
            );

            return;

        }

// Baby Filter Menu
        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "BABYFILTER"
                )) {

            event.setCancelled(true);

            String[] split =
                    title.split("Page ");

            int currentPage = 0;

            if (split.length > 1) {

                try {

                    currentPage =
                            Integer.parseInt(
                                    split[1]
                            ) - 1;

                } catch (NumberFormatException ignored) {
                }

            }

            UUID standUUID =
                    standManager.getSelectedArmorStand(
                            player.getUniqueId()
                    );

            if (standUUID == null) {

                player.closeInventory();

                return;

            }

            ArmorStand armorStand =
                    (ArmorStand) Bukkit.getEntity(
                            standUUID
                    );

            if (armorStand == null) {

                player.closeInventory();

                return;

            }

            int navigationRow =
                    event.getInventory().getSize() - 9;

            int previousSlot =
                    navigationRow;

            int backSlot =
                    navigationRow + 4;

            int nextSlot =
                    navigationRow + 8;

// Previous page
            if (event.getSlot() == previousSlot) {

                if (currentPage > 0) {

                    BabyFilterMenu.open(
                            plugin,
                            player,
                            armorStand,
                            currentPage - 1
                    );

                }

                return;

            }

// Next page
            if (event.getSlot() == nextSlot) {

                Map<String, MobFilterData> mobData =
                        plugin.getConfigManager()
                                .getBabyMobData();

                int maxPage =
                        (int) Math.ceil(
                                mobData.size() / 45.0
                        ) - 1;

                if (currentPage < maxPage) {

                    BabyFilterMenu.open(
                            plugin,
                            player,
                            armorStand,
                            currentPage + 1
                    );

                }

                return;

            }

// Back button
            if (event.getSlot() == backSlot) {

                StandMenu.open(
                        plugin,
                        player,
                        armorStand
                );

                return;

            }

// Ignore navigation row
            if (event.getSlot() >= navigationRow) {
                return;
            }

            if (event.getCurrentItem() == null ||
                    !event.getCurrentItem().hasItemMeta() ||
                    event.getCurrentItem().getItemMeta() == null ||
                    !event.getCurrentItem().getItemMeta().hasDisplayName()) {

                return;
            }

            String mobName =
                    ChatColor.stripColor(
                            event.getCurrentItem()
                                    .getItemMeta()
                                    .getDisplayName()
                    );

            mobName =
                    mobName.replace(" (Disabled)", "");

            MobFilterData data =
                    plugin.getConfigManager()
                            .getBabyMobData()
                            .get(mobName);

            if (data != null &&
                    data.isKillAll()) {

                return;

            }

            standManager.toggleBabyMobFilter(
                    armorStand,
                    mobName
            );

            BabyFilterMenu.open(
                    plugin,
                    player,
                    armorStand,
                    currentPage
            );

            return;

        }

// Conversion Menu
        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "CONVERT"
                )) {

            event.setCancelled(true);

            if (event.getCurrentItem() == null) {
                return;
            }

            UUID standUUID = standManager.getSelectedArmorStand(
                    player.getUniqueId()
            );

            if (standUUID == null) {

                player.closeInventory();

                return;
            }

            ArmorStand armorStand = (ArmorStand) Bukkit.getEntity(standUUID);

            if (armorStand == null) {

                player.closeInventory();

                return;
            }

            boolean converted =
                    standManager.convertToMobStand(
                            armorStand,
                            player
                    );

            standManager.setSelectedArmorStand(
                    player.getUniqueId(),
                    armorStand.getUniqueId()
            );

            UUID selectedUUID =
                    standManager.getSelectedArmorStand(
                            player.getUniqueId()
                    );

            standManager.setSelectedArmorStand(
                    player.getUniqueId(),
                    armorStand.getUniqueId()
            );

            if (converted) {

                player.sendMessage(
                        ChatColor.GREEN
                                + "Converted to RS MobStand. Reopen the stand to adjust settings."
                );

            }

            player.closeInventory();

            standManager.clearSelectedArmorStand(
                    player.getUniqueId()
            );

            return;

        }

// Stand Menu
        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "MAIN"
                )) {

            event.setCancelled(true);

            UUID standUUID =
                    standManager.getSelectedArmorStand(
                            player.getUniqueId()
                    );

            if (standUUID == null) {

                player.closeInventory();

                return;

            }

            ArmorStand armorStand =
                    (ArmorStand) Bukkit.getEntity(
                            standUUID
                    );

            if (armorStand == null) {

                player.closeInventory();

                return;

            }

// Adult Filters
            if (event.getSlot() == 15) {

                AdultFilterMenu.open(
                        plugin,
                        player,
                        armorStand,
                        0
                );

                return;

            }

// Baby Filters
            if (event.getSlot() == 17) {

                BabyFilterMenu.open(
                        plugin,
                        player,
                        armorStand,
                        0
                );

                return;

            }

            if (event.getCurrentItem() == null) {
                return;
            }

// Convert back button
            if (event.getSlot() == 2) {

                standManager.revertToNormalStand(armorStand);

                standManager.clearSelectedArmorStand(
                        player.getUniqueId()
                );

                player.sendMessage(
                        ChatColor.GREEN
                                + "Converted back to normal Armor Stand."
                );

                player.closeInventory();

                return;

            }

             /*
            |--------------------------------------------------------------------------
            | ADMIN MENU
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 4) {

                if (!player.isOp()) {
                    return;
                }

                RSAdminMenu.open(player);

                return;

            }

// Pause toggle
            if (event.getSlot() == 6) {

                boolean paused =
                        standManager.isPaused(armorStand);

                paused = !paused;

                standManager.setPaused(
                        armorStand,
                        paused
                );

                player.sendMessage(
                        paused
                                ? ChatColor.GREEN
                                  + "MobStand paused."
                                : ChatColor.GREEN
                                  + "MobStand resumed."
                );

                StandMenu.open(
                        plugin,
                        player,
                        armorStand
                );

                return;

            }
// Open leave Alive Menu
            if (event.getSlot() == 13) {

                LeaveAliveMenu leaveAliveMenu =
                        new LeaveAliveMenu();

                leaveAliveMenu.open(
                        player,
                        standManager.getLeaveAlive(armorStand)
                );

                return;
            }

// Open Radius Menu
            if (event.getSlot() == 9) {

                RadiusMenu radiusMenu =
                        new RadiusMenu();

                radiusMenu.open(
                        player,
                        (int) standManager.getRadius(armorStand)
                );

                return;
            }

// Gravity toggle
            if (event.getSlot() == 11) {

                boolean gravity =
                        standManager.hasGravity(armorStand);

                gravity = !gravity;

                standManager.setGravity(
                        armorStand,
                        gravity
                );

                player.sendMessage(
                        ChatColor.GREEN
                                + "Gravity "
                                + (gravity
                                ? ChatColor.GREEN + "Enabled"
                                : ChatColor.GREEN + "Disabled")
                );

                StandMenu.open(
                        plugin,
                        player,
                        armorStand
                );

                return;

            }
        }

                    /*
        |--------------------------------------------------------------------------
        | ADMIN MENU
        |--------------------------------------------------------------------------
        */

        if (event.getInventory()
                .getHolder()
                instanceof RSMenuHolder holder
                && holder.getMenuId()
                .equals(
                        "ADMIN"
                )) {

            event.setCancelled(true);

            if (event.getCurrentItem() == null) {
                return;
            }

            /*
            |--------------------------------------------------------------------------
            | BACK BUTTON
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 4) {

                UUID standUUID =
                        standManager.getSelectedArmorStand(
                                player.getUniqueId()
                        );

                if (standUUID == null) {

                    player.closeInventory();

                    return;

                }

                ArmorStand armorStand =
                        (ArmorStand) Bukkit.getEntity(
                                standUUID
                        );

                if (armorStand == null) {

                    player.closeInventory();

                    return;

                }

                StandMenu.open(
                        plugin,
                        player,
                        armorStand
                );

                return;

            }


            /*
            |--------------------------------------------------------------------------
            | RELOAD CONFIG
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 12) {

                plugin.reloadConfig();

                plugin.getConfigManager()
                        .loadConfigs();

                player.sendMessage(
                        ChatColor.GREEN
                                + "RS MobStand Configuration Reloaded."
                );

                RSAdminMenu.open(player);

                return;

            }

            /*
            |--------------------------------------------------------------------------
            | VERSION DISPLAY
            |--------------------------------------------------------------------------
            */

            if (event.getRawSlot() == 14) {

                player.sendMessage(
                        ChatColor.GREEN
                                + "§bRS MobStand Version: §f"
                                + Bukkit.getPluginManager()
                                .getPlugin("RS-MobStand")
                                .getDescription()
                                .getVersion()

                );

            }

        }
    }

}