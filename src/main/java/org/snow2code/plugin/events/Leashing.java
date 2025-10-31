package org.snow2code.plugin.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.snow2code.util.*;

public class Leashing implements Listener {
    private static HashMap<String, Set<UUID>> leashedFoxes = new HashMap<>();

    // Remove all foxes from leashedFoxes for the player
    @EventHandler
    public void playerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        leashedFoxes.remove(player.getName());
    }

    // When leashing a fox, add one to the player
    @EventHandler
    public void onLeashEntity(PlayerLeashEntityEvent event) {
        if ( event.getPlayer() != null ) {
            Entity entity = event.getEntity();
            Player player = event.getPlayer();

            // Is the entity a fox?
            if ( entity.getType() == EntityType.FOX ) {
                // Okay so a fox has been leashed

                // Is the player leashing the fox(s) to a fence?
                if ( event.getLeashHolder() instanceof LeashHitch ) {
                    // Okay so the player is leashing the fox(s) to a fence
                    SemiLogger.Debug("Clearing the dillywop.");

                    leashedFoxes.remove(player.getName());
                    return;
                }

                leashedFoxes.putIfAbsent(player.getName(), new HashSet<>());

                Set<UUID> foxSet = leashedFoxes.get(player.getName());
                foxSet.add(entity.getUniqueId());

                if ( foxSet.size() >= 2 ) {
                    // is in overworld
                    if ( player.getWorld().getName().equals("world") ) {
                        SemiLogger.Debug("Player has 2 or more foxes leashed in the overworld. " + player.getWorld().getTime());
                        
                        if ( SemiFunc.IsDay(player.getWorld().getTime()) ) {
                            SemiLogger.Debug("SemiFunc.IsDay returned " + SemiFunc.IsDay(player.getWorld().getTime()));
                        }
                    }

                }

                SemiLogger.Debug(
                    String.format("Some foxes were leashed by %s, total the player has now is %s", player.getName(), foxSet.size())
                );
            }
        }
    }

    // When unleashing a fox, remove one from the player in leashedFoxes
    @EventHandler
    public void onUnleashEntity(PlayerLeashEntityEvent event) {
        Entity entity = event.getEntity();

        if ( entity.getType() == EntityType.FOX ) {
            for ( Map.Entry<String, Set<UUID>> entry : leashedFoxes.entrySet() ) {
                if ( entry.getValue().remove(entity.getUniqueId()) ) {
                    SemiLogger.Debug(
                        String.format("Some foxes were unleashed by %s, total the player has now is %s", entry.getKey(), entry.getValue().size())
                    );
                }
                break;
            }
        }
    }
}
