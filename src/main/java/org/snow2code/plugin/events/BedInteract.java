package org.snow2code.plugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.snow2code.local.SemiSnow;
import org.snow2code.util.SemiLogger;

import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class BedInteract implements Listener {

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        // Just in case
        return;
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        // Is the BedEnterEvent successful?
        if ( event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK ) {
            Player player = event.getPlayer();

            if ( server.getOnlinePlayers().size() == 1 ) {
                if ( SemiSnow.IsSnowy(player) ) {
                    SemiLogger.Debug(player.getName() + " is sleeping along"); // is cuddling her own tail in bed, giving comfort and warmth")

                    // regen give.?
                }
            } else {
                for ( Player other : server.getOnlinePlayers() ) {
                    if ( other.equals(player) ) continue; // Ignore self
                    if ( !other.isOnline() ) continue; // Ignore if the player isn't online

                    // Are they sleeping?
                    if ( other.isSleeping() ) {
                        double distance = player.getLocation().distance(other.getLocation());
                        int range = 10; // Block range, 10 by default

                        // Is the distance is in range?
                        if ( distance <= range ) {
                            SemiLogger.Debug(player.getName() + " and " + other.getName() + " are cuddling in beds (distance " + distance + ")");

                            // player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 30, 0));
                            // other.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 30, 0));
                        }
                    }
                }
            }
        }
    }
}
