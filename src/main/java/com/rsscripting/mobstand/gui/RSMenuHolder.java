package com.rsscripting.mobstand.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class RSMenuHolder
        implements InventoryHolder {

    private final String menuId;

    public RSMenuHolder(
            String menuId
    ) {

        this.menuId = menuId;

    }

    public String getMenuId() {

        return menuId;

    }

    @Override
    public Inventory getInventory() {

        return null;

    }

}