package org.snow2code.plugin.events.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.snow2code.util.*;

public class Scram implements Listener {
    // For Foxes only
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (SemiConfig.HasFoxEffects(player)) {
            scramEffect(player);
        }
    }

    void scramEffect(Player player) {
        SemiFunc.RunLater(() -> {
            if ( player.getHealth() <= 8.0 ) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0, true, false));
            // } else {
            //    player.removePotionEffect(PotionEffectType.SPEED);
            }

            scramEffect(player);
        }, 3L);
    }
}
