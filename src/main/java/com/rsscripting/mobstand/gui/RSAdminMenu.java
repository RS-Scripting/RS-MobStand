package com.rsscripting.mobstand.gui;

import com.rsscripting.mobstand.RSMobStandPlugin;
import com.rsscripting.mobstand.utils.VersionChecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class RSAdminMenu {

    public static final String MENU_TITLE =
            ChatColor.GOLD + "Admin";

    public static void open(
            Player player
    ) {

        Inventory inventory =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "ADMIN"
                        ),
                        18,
                        MENU_TITLE
                );

        /*
        |--------------------------------------------------------------------------
        | FILLER
        |--------------------------------------------------------------------------
        */

        ItemStack filler =
                new ItemStack(
                        Material.WHITE_STAINED_GLASS_PANE
                );

        ItemMeta fillerMeta =
                filler.getItemMeta();

        if (fillerMeta != null) {

            fillerMeta.setDisplayName(" ");

            filler.setItemMeta(fillerMeta);

        }

        for (int i = 0; i < inventory.getSize(); i++) {

            inventory.setItem(i, filler);

        }

        /*
        |--------------------------------------------------------------------------
        | RELOAD CONFIG
        |--------------------------------------------------------------------------
        */

        ItemStack reloadItem =
                new ItemStack(
                        Material.ENCHANTED_BOOK
                );

        ItemMeta reloadMeta =
                reloadItem.getItemMeta();

        if (reloadMeta != null) {

            reloadMeta.setDisplayName(
                    ChatColor.GREEN
                            + "Reload Config"
            );

            reloadMeta.setLore(
                    Collections.singletonList(
                            ChatColor.GRAY
                                    + "Reload plugin configuration."
                    )
            );

            reloadMeta.addItemFlags(
                    ItemFlag.HIDE_ENCHANTS
            );

            reloadItem.setItemMeta(
                    reloadMeta
            );

        }

        inventory.setItem(
                12,
                reloadItem
        );

        /*
        |--------------------------------------------------------------------------
        | Back Button
        |--------------------------------------------------------------------------
        */

        ItemStack backItem =
                new ItemStack(
                        Material.OAK_DOOR
                );

        ItemMeta backMeta =
                backItem.getItemMeta();

        if (backMeta != null) {

            backMeta.setDisplayName(
                    ChatColor.YELLOW
                            + "Back"
            );

            backItem.setItemMeta(
                    backMeta
            );

        }

        inventory.setItem(
                4,
                backItem
        );

        /*
        |--------------------------------------------------------------------------
        | VERSION
        |--------------------------------------------------------------------------
        */

        ItemStack versionItem =
                new ItemStack(
                        Material.BOOK
                );

        ItemMeta versionMeta =
                versionItem.getItemMeta();

        if (versionMeta != null) {

            versionMeta.setDisplayName(
                    ChatColor.AQUA
                            + "Plugin Version"
            );

            String currentVersion =
                    Bukkit.getPluginManager()
                            .getPlugin("RS-MobStand")
                            .getDescription() .getVersion();

            String latestVersion =
                    VersionChecker.getLatestVersion();

            java.util.List<String> lore =
                    new java.util.ArrayList<>();

            lore.add(
                    ChatColor.GRAY
                            + "Current Version: "
                            + ChatColor.WHITE
                            + currentVersion
            );

            if (latestVersion == null) {

                lore.add(
                        ChatColor.GRAY
                                + "Latest Version: "
                                + ChatColor.WHITE
                                + "Unknown"
                );

                lore.add("");

                lore.add(
                        ChatColor.RED
                                + "Unable To Check"
                );

            }
            else if (!currentVersion.equalsIgnoreCase(
                    latestVersion
            )) {

                lore.add(
                        ChatColor.GRAY
                                + "Latest Version: "
                                + ChatColor.WHITE
                                + latestVersion
                );

                lore.add("");

                lore.add(
                        ChatColor.GREEN
                                + "Update Available!"
                );

            }

            else {

                lore.add(
                        ChatColor.YELLOW
                                + "Plugin Up To Date"
                );

            }

            versionMeta.setLore(lore);

            versionItem.setItemMeta(
                    versionMeta
            );

        }

        inventory.setItem(
                14,
                versionItem
        );

        player.openInventory(
                inventory
        );

    }

}