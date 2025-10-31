package org.snow2code.plugin.events.effects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.snow2code.util.SemiConfig;

public class NoFallDamage implements Listener {

    @EventHandler
    public void entityDamaged(EntityDamageEvent event) {
        if ( event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if ( event.getCause() == EntityDamageEvent.DamageCause.FALL) {

                if ( SemiConfig.PlayerFallDamage(player) ) {
                    event.setCancelled(SemiConfig.PlayerFallDamage(player));
                }

            }
        }
    }

}
