package com.rsscripting.mobstand.listeners;

import com.rsscripting.mobstand.managers.StandManager;

import org.bukkit.Chunk;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoadListener implements Listener {

    private final StandManager standManager;

    public ChunkLoadListener(StandManager standManager) {

        this.standManager = standManager;

    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {

        Chunk chunk = event.getChunk();

        for (Entity entity : chunk.getEntities()) {

            if (!(entity instanceof ArmorStand armorStand)) {
                continue;
            }

            if (!standManager.isMobStand(armorStand)) {
                continue;
            }

            armorStand.setInvulnerable(true);

            standManager.getActiveStands().add(
                    armorStand.getUniqueId()
            );

        }

    }

}