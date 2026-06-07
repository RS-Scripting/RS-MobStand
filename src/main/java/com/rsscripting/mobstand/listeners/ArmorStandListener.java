package com.rsscripting.mobstand.listeners;

import com.rsscripting.mobstand.RSMobStandPlugin;
import com.rsscripting.mobstand.gui.ConversionMenu;
import com.rsscripting.mobstand.gui.StandMenu;
import com.rsscripting.mobstand.managers.StandManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorStandListener implements Listener {

    private final StandManager standManager;

    private final RSMobStandPlugin plugin;

    public ArmorStandListener(
            RSMobStandPlugin plugin,
            StandManager standManager
    ) {

        this.plugin = plugin;

        this.standManager = standManager;

    }

    @EventHandler
    public void onArmorStandInteract(PlayerInteractAtEntityEvent event) {

// Only armor stands
        if (!(event.getRightClicked() instanceof ArmorStand armorStand)) {
            return;
        }

        Player player = event.getPlayer();

// Must be sneaking
        if (!player.isSneaking()) {
            return;
        }

        ItemStack item =
                player.getInventory()
                        .getItemInMainHand();

        if (item == null ||
                item.getType() != Material.STICK) {

            return;
        }

// Existing MobStand
        if (standManager.isMobStand(armorStand)) {

// Track selected stand
            standManager.setSelectedArmorStand(
                    player.getUniqueId(),
                    armorStand.getUniqueId()
            );

// Owner check
            if (!standManager.isOwner(
                    armorStand,
                    player
            )
                    && !player.hasPermission(
                    "rsmobstand.admin"
            )) {

                player.sendMessage(
                        ChatColor.RED
                                + "You do not own this MobStand."
                );

                return;

            }

// Cancel normal interaction
            event.setCancelled(true);

            StandMenu.open(
                    plugin,
                    player,
                    armorStand
            );

            return;

        }

// Track selected stand
        standManager.setSelectedArmorStand(
                player.getUniqueId(),
                armorStand.getUniqueId()
        );

        // Cancel normal interaction
        event.setCancelled(true);

// Open conversion menu
        ConversionMenu.open(player);

    }

}