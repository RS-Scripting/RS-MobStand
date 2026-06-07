package com.rsscripting.mobstand.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ConversionMenu {

    public static final String MENU_TITLE =
            ChatColor.BLUE
                    + "Convert to RS-MobStand";

    public static void open(Player player) {

        Inventory inventory =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "CONVERT"
                        ),
                        9,
                        MENU_TITLE
                );

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

        ItemStack convertItem = new ItemStack(Material.CRAFTING_TABLE);

        ItemMeta meta = convertItem.getItemMeta();

        if (meta != null) {

            meta.setDisplayName(ChatColor.GREEN + "Convert to RS MobStand");

            meta.setLore(Collections.singletonList(
                    ChatColor.YELLOW + "Convert this armor stand into an RS MobStand."
            ));

            meta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES
            );

            convertItem.setItemMeta(meta);

        }

        inventory.setItem(4, convertItem);

        player.openInventory(inventory);

    }

}