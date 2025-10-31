package org.snow2code.plugin.events.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import org.snow2code.util.*;

public class Pounce implements Listener {
    // For Foxes only
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if ( SemiConfig.HasFoxEffects(player) ) {
            SemiFox.FoxPounce(player);
        }
    }
}
