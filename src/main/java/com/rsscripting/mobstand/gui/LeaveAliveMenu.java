package com.rsscripting.mobstand.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LeaveAliveMenu {

    public static final String MENU_TITLE =
            ChatColor.BLUE
                    + "Leave Alive: ";

    public void open(
            Player player,
            int leaveAlive
    ) {

        Inventory inventory =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "LEAVEALIVE"
                        ),
                        18,
                        MENU_TITLE
                                + leaveAlive
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

        inventory.setItem(
                4,
                createItem(
                        Material.OAK_DOOR,
                        "§eBack",
                        List.of(
                                "§7Return to previous menu."
                        ),
                        1
                )
        );

        inventory.setItem(
                10,
                createItem(
                        Material.RED_WOOL,
                        "§cRemove 10",
                        List.of(
                                "§7Decrease by 10."
                        ),
                        10
                )
        );

        inventory.setItem(
                11,
                createItem(
                        Material.RED_WOOL,
                        "§cRemove 5",
                        List.of(
                                "§7Decrease by 5."
                        ),
                        5
                )
        );

        inventory.setItem(
                12,
                createItem(
                        Material.RED_WOOL,
                        "§cRemove 1",
                        List.of(
                                "§7Decrease by 1."
                        ),
                        1
                )
        );

        inventory.setItem(
                13,
                createItem(
                        Material.GRAY_STAINED_GLASS_PANE,
                        " ",
                        List.of(),
                        1
                )
        );

        inventory.setItem(
                14,
                createItem(
                        Material.GREEN_WOOL,
                        "§aAdd 1",
                        List.of(
                                "§7Increase by 1."
                        ),
                        1
                )
        );

        inventory.setItem(
                15,
                createItem(
                        Material.GREEN_WOOL,
                        "§aAdd 5",
                        List.of(
                                "§7Increase by 5."
                        ),
                        5
                )
        );

        inventory.setItem(
                16,
                createItem(
                        Material.GREEN_WOOL,
                        "§aAdd 10",
                        List.of(
                                "§7Increase by 10."
                        ),
                        10
                )
        );

        player.openInventory(inventory);

    }

    private ItemStack createItem(
            Material material,
            String name,
            List<String> lore,
            int amount
    ) {

        ItemStack item =
                new ItemStack(
                        material,
                        amount
                );

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.setDisplayName(name);

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;

    }
}