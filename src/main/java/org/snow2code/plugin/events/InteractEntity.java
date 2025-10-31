package org.snow2code.plugin.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import org.snow2code.util.*;

public class InteractEntity implements Listener {
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if ( event.getRightClicked() != null ) {
            Entity entity = event.getRightClicked();
            Player player = event.getPlayer();

            HealFox(entity, player);
        }
    }


    // Actual stuff
    void HealFox(Entity entity, Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if ( entity.getType() == EntityType.FOX ) {
            Fox fox = (Fox) entity;

            SemiFunc.RunLater(() -> {
                // should heal 3 hearts? idk
                SemiLogger.Debug(
                        String.format(
                                "\nLoveTick: %s\nisLoveMode: %s\nCan Breed: %s",
                                fox.getLoveModeTicks(),
                                fox.isLoveMode(),
                                fox.canBreed()
                        )
                );
            }, 1L);
        }
    }
}
