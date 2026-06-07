package com.rsscripting.mobstand.gui;

import com.rsscripting.mobstand.RSMobStandPlugin;
import com.rsscripting.mobstand.managers.StandManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.Collections;
import java.util.List;

public class StandMenu {

    public static final String MENU_TITLE =
            ChatColor.BLUE + "RS MobStand";

    public static void open(
            RSMobStandPlugin plugin,
            Player player,
            ArmorStand armorStand
    ) {

        Inventory inventory =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "MAIN"
                        ),
                        18,
                        MENU_TITLE
                );

        StandManager standManager =
                plugin.getStandManager();

        double radius =
                standManager.getRadius(armorStand);

        int maxRadius =
                plugin.getConfig()
                        .getInt(
                                "max-player-scan-radius"
                        );

        radius =
                Math.min(
                        radius,
                        maxRadius
                );

        standManager.setRadius(
                armorStand,
                radius
        );

        boolean gravity =
                standManager.hasGravity(armorStand);

        boolean paused =
                standManager.isPaused(armorStand);

        ItemStack filler =
                new ItemStack(Material.WHITE_STAINED_GLASS_PANE);

        ItemMeta fillerMeta =
                filler.getItemMeta();

        if (fillerMeta != null) {

            fillerMeta.setDisplayName(" ");

            filler.setItemMeta(fillerMeta);

        }

        for (int i = 0; i < inventory.getSize(); i++) {

            inventory.setItem(i, filler);

        }

// Convert back item
        ItemStack revertItem =
                new ItemStack(Material.COMPOSTER);

        ItemMeta revertMeta =
                revertItem.getItemMeta();

        if (revertMeta != null) {

            revertMeta.setDisplayName(
                    ChatColor.RED
                            + "Convert Back to Armor Stand"
            );

            revertMeta.setLore(
                    Collections.singletonList(
                            ChatColor.GRAY
                                    + "Remove MobStand functionality."
                    )
            );

            revertItem.setItemMeta(revertMeta);

        }

        inventory.setItem(2, revertItem);

         /*
        |--------------------------------------------------------------------------
        | ADMIN MENU
        |--------------------------------------------------------------------------
        */

        if (player.isOp()) {

            ItemStack adminItem =
                    new ItemStack(
                            Material.COMMAND_BLOCK
                    );

            ItemMeta adminMeta =
                    adminItem.getItemMeta();

            if (adminMeta != null) {

                adminMeta.setDisplayName(
                        ChatColor.GOLD
                                + "Admin"
                );

                adminMeta.setLore(
                        java.util.Collections.singletonList(
                                "§7Administrative options."
                        )
                );

                adminItem.setItemMeta(
                        adminMeta
                );

            }

            inventory.setItem(
                    4,
                    adminItem
            );

        }

// Pause item
        ItemStack pauseItem =
                new ItemStack(
                        paused
                                ? Material.RED_DYE
                                : Material.GREEN_DYE
                );

        ItemMeta pauseMeta =
                pauseItem.getItemMeta();

        if (pauseMeta != null) {

            pauseMeta.setDisplayName(
                    paused
                            ? ChatColor.GREEN + "Enable Processing"
                            : ChatColor.RED + "Disable Processing"
            );

            pauseMeta.setLore(List.of(
                    paused
                            ? ChatColor.GRAY
                              + "Click to resume processing."
                            : ChatColor.GRAY
                              + "Click to pause processing."
            ));

            pauseMeta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES
            );

            pauseItem.setItemMeta(pauseMeta);

        }

        inventory.setItem(6, pauseItem);




// Adult filters item
        ItemStack adultFilters =
                new ItemStack(Material.BEEF);

        ItemMeta adultMeta =
                adultFilters.getItemMeta();

        if (adultMeta != null) {

            adultMeta.setDisplayName(
                    ChatColor.YELLOW
                            + "Adult Filters"
            );

            adultMeta.setLore(List.of(
                    ChatColor.YELLOW
                            + "Configure adult mob filters."
            ));

            adultFilters.setItemMeta(adultMeta);

        }

        inventory.setItem(15, adultFilters);

// Baby filters item
        ItemStack babyFilters =
                new ItemStack(Material.BEE_SPAWN_EGG);

        ItemMeta babyMeta =
                babyFilters.getItemMeta();

        if (babyMeta != null) {

            babyMeta.setDisplayName(
                    ChatColor.YELLOW
                            + "Baby Filters"
            );

            babyMeta.setLore(List.of(
                    ChatColor.YELLOW
                            + "Configure baby mob filters."
            ));

            babyFilters.setItemMeta(babyMeta);

        }

        inventory.setItem(17, babyFilters);


        // Leave Alive item
        int leaveAlive =
                standManager.getLeaveAlive(
                        armorStand
                );

        ItemStack leaveAliveItem =
                new ItemStack(
                        Material.TOTEM_OF_UNDYING
                );

        ItemMeta leaveAliveMeta =
                leaveAliveItem.getItemMeta();

        if (leaveAliveMeta != null) {

            leaveAliveMeta.setDisplayName(
                    ChatColor.YELLOW
                            + "Leave Alive:"
            );

            leaveAliveMeta.setLore(List.of(
                    ChatColor.GRAY
                            + "Current: "
                            + ChatColor.WHITE
                            + leaveAlive,
                    "",
                    ChatColor.YELLOW
                            + "Click to adjust."
            ));

            leaveAliveItem.setItemMeta(leaveAliveMeta);

        }

        inventory.setItem(13, leaveAliveItem);



// Radius item
        ItemStack radiusItem =
                new ItemStack(
                        Material.COMPASS
                );

        ItemMeta radiusMeta =
                radiusItem.getItemMeta();

        if (radiusMeta != null) {

            radiusMeta.setDisplayName(
                    ChatColor.YELLOW
                            + "Scan Radius"
            );

            radiusMeta.setLore(List.of(
                    ChatColor.GRAY
                            + "Current: "
                            + ChatColor.WHITE
                            + (int) radius,
                    "",
                    ChatColor.YELLOW
                            + "Click to adjust radius."
            ));

            radiusItem.setItemMeta(radiusMeta);

        }

        inventory.setItem(9, radiusItem);

        ItemStack gravityItem =
                new ItemStack(Material.FEATHER);

        ItemMeta gravityMeta =
                gravityItem.getItemMeta();

        if (gravityMeta != null) {

            gravityMeta.setDisplayName(
                    (gravity
                            ? ChatColor.GREEN
                            : ChatColor.RED)
                            + "Gravity"
            );

            gravityMeta.setLore(List.of(
                    ChatColor.GRAY + "Current: "
                            + (gravity
                            ? ChatColor.GREEN + "Enabled"
                            : ChatColor.RED + "Disabled"),
                    "",
                    ChatColor.YELLOW + "Left Click: Toggle"
            ));

// Add enchant glow if gravity enabled
            if (gravity) {

                gravityMeta.addEnchant(
                        Enchantment.UNBREAKING,
                        1,
                        true
                );

                gravityMeta.addItemFlags(
                        ItemFlag.HIDE_ENCHANTS
                );

            }

            gravityItem.setItemMeta(gravityMeta);

        }

        inventory.setItem(11, gravityItem);

        player.openInventory(inventory);

    }

}