package com.rsscripting.mobstand.gui;

import com.rsscripting.mobstand.RSMobStandPlugin;
import com.rsscripting.mobstand.data.MobFilterData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BabyFilterMenu {

    public static final String MENU_TITLE =
            ChatColor.BLUE + "Baby Filters";

    private static final int ITEMS_PER_PAGE = 45;

    public static void open(
            RSMobStandPlugin plugin,
            Player player,
            ArmorStand armorStand,
            int page
    ) {

        Map<String, MobFilterData> mobData =
                plugin.getConfigManager()
                        .getBabyMobData();

        List<String> mobs =
                new ArrayList<>(mobData.keySet());

        Collections.sort(mobs);

        List<String> disabledMobs =
                plugin.getStandManager()
                        .getDisabledBabyMobs(
                                armorStand
                        );

        int startIndex =
                page * ITEMS_PER_PAGE;

        int endIndex =
                Math.min(
                        startIndex + ITEMS_PER_PAGE,
                        mobs.size()
                );

        int visibleItemCount =
                endIndex - startIndex;

        int contentRows =
                (int) Math.ceil(
                        visibleItemCount / 9.0
                );

        contentRows =
                Math.max(contentRows, 1);

        int totalRows =
                contentRows + 1;

        int inventorySize =
                totalRows * 9;

        Inventory inventory =
                Bukkit.createInventory(
                        new RSMenuHolder(
                                "BABYFILTER"
                        ),
                        inventorySize,
                        MENU_TITLE
                                + " (Page "
                                + (page + 1)
                                + ")"
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

        List<ItemStack> filterItems =
                new ArrayList<>();

        for (int i = startIndex; i < endIndex; i++) {

            String mobName = mobs.get(i);

            MobFilterData data =
                    mobData.get(mobName);

            boolean forcedEnabled =
                    data.isKillAll();

            boolean enabled =
                    forcedEnabled
                            || !disabledMobs.contains(
                            mobName
                    );

            ItemStack item =
                    new ItemStack(
                            enabled
                                    ? data.getIcon()
                                    : Material.RED_STAINED_GLASS_PANE
                    );

            ItemMeta meta =
                    item.getItemMeta();

            if (meta != null) {

                meta.setDisplayName(
                        enabled
                                ? ChatColor.GREEN + mobName
                                : ChatColor.YELLOW
                                  + mobName
                                  + " (Disabled)"
                );

                java.util.List<String> lore =
                        new java.util.ArrayList<>();

                if (forcedEnabled) {

                    lore.add(
                            ChatColor.YELLOW
                                    + "Overridden by Server Admin"
                    );

                }

                meta.setLore(lore);

                item.setItemMeta(meta);

            }

            filterItems.add(item);

        }

        int currentRow = 0;
        int rowStart = 0;

        while (rowStart < filterItems.size()) {

            int remainingItems =
                    filterItems.size() - rowStart;

            int itemsThisRow =
                    Math.min(9, remainingItems);

            boolean isLastPartialRow =
                    itemsThisRow < 9;

            int rowBaseSlot =
                    currentRow * 9;

            List<Integer> rowSlots =
                    new ArrayList<>();

            if (isLastPartialRow) {

                if (itemsThisRow % 2 == 0) {

                    switch (itemsThisRow) {

                        case 2 -> rowSlots = List.of(3, 5);

                        case 4 -> rowSlots = List.of(2, 3, 5, 6);

                        case 6 -> rowSlots = List.of(1, 2, 3, 5, 6, 7);

                        case 8 -> rowSlots = List.of(0, 1, 2, 3, 5, 6, 7, 8);

                    }

                } else {

                    int startSlot =
                            (9 - itemsThisRow) / 2;

                    for (int i = 0; i < itemsThisRow; i++) {

                        rowSlots.add(startSlot + i);

                    }

                }

            } else {

                for (int i = 0; i < 9; i++) {

                    rowSlots.add(i);

                }

            }

            for (int i = 0; i < itemsThisRow; i++) {

                inventory.setItem(
                        rowBaseSlot + rowSlots.get(i),
                        filterItems.get(rowStart + i)
                );

            }

            rowStart += itemsThisRow;
            currentRow++;

        }

        int navigationRow =
                inventorySize - 9;

// Previous page
        if (page > 0) {

            ItemStack previous =
                    new ItemStack(Material.ARROW);

            ItemMeta previousMeta =
                    previous.getItemMeta();

            if (previousMeta != null) {

                previousMeta.setDisplayName(
                        ChatColor.YELLOW
                                + "Previous Page"
                );

                previous.setItemMeta(previousMeta);

            }

            inventory.setItem(
                    navigationRow,
                    previous
            );

        }


// Back button
        ItemStack back =
                new ItemStack(Material.OAK_DOOR);

        ItemMeta backMeta =
                back.getItemMeta();

        if (backMeta != null) {

            backMeta.setDisplayName(
                    ChatColor.YELLOW + "Back"
            );

            back.setItemMeta(backMeta);

        }

        inventory.setItem(
                navigationRow + 4,
                back
        );



// Next page
        if (endIndex < mobs.size()) {

            ItemStack next =
                    new ItemStack(Material.ARROW);

            ItemMeta nextMeta =
                    next.getItemMeta();

            if (nextMeta != null) {

                nextMeta.setDisplayName(
                        ChatColor.YELLOW
                                + "Next Page"
                );

                next.setItemMeta(nextMeta);

            }

            inventory.setItem(
                    navigationRow + 8,
                    next
            );

        }
        player.openInventory(inventory);

    }

}
