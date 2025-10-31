package org.snow2code.plugin.events.effects;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.snow2code.util.SemiConfig;

public class BerryCraver implements Listener {
    // For Foxes only

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        if ( SemiConfig.HasFoxEffects(player) ) {
            
            if ( event.getItem().getType() == Material.SWEET_BERRIES ) {
                int level = Math.min(20, player.getFoodLevel() + 1);
                player.setFoodLevel(level);
            }

        }
    }
}