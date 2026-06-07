package com.rsscripting.mobstand.listeners;

import com.rsscripting.mobstand.RSMobStandPlugin;
import com.rsscripting.mobstand.managers.StandManager;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ArmorStandBreakListener implements Listener {

    private final StandManager standManager;

    public ArmorStandBreakListener(RSMobStandPlugin plugin) {

        this.standManager = plugin.getStandManager();

    }

    @EventHandler
    public void onArmorStandDamage(EntityDamageEvent event) {

// Must be armor stand
        if (!(event.getEntity() instanceof ArmorStand armorStand)) {
            return;
        }

// Only manage MobStands
        if (!standManager.isMobStand(armorStand)) {
            return;
        }

// Completely block ALL vanilla damage
        event.setCancelled(true);

    }

}