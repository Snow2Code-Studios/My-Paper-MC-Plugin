package org.snow2code.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.snow2code.util.fox.*;
import org.snow2code.plugin.events.effects.*;
import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class SemiFox {
    
    private static final Map<UUID, Long> pounceCooldown = new HashMap<>();
    private static final Map<UUID, Boolean> canPounce = new HashMap<>();
    public static final MightyMouth mighty = new MightyMouth(plugin.getDataFolder());
    public static final SnoutPouch snout = new SnoutPouch(plugin.getDataFolder());

    // public static boolean canPounce = true;
    public static boolean idleSoundthingStarted = false;
    public static boolean idleSoundSkip = false;

    public static void RegisterFox() {
        SemiLogger.Debug("SemiFox");
        // HealFox is now into InteractEntity
        server.getPluginManager().registerEvents(new FoxPlayerAmbient(), plugin);
        server.getPluginManager().registerEvents(new Pounce(), plugin);

        server.getPluginManager().registerEvents(new BerryCraver(), plugin);
        // Acrobatics is now into NoFallDamage PlayerConfig (SemiConfig)
        server.getPluginManager().registerEvents(new Scram(), plugin);
    }

    public static void FoxSound(String type, Player player) {
        boolean isFox = false; // how dare you not be fox! foxes are cute af!
        Sound sound = Sound.UI_TOAST_IN;

        switch (type.toUpperCase()) {
            case "AMBIENT":
                isFox = true;
                sound = Sound.ENTITY_FOX_AMBIENT;
                break;
            case "DEATH":
                isFox = true;
                sound = Sound.ENTITY_FOX_DEATH;
                break;
            case "SLEEP":
                isFox = true;
                sound = Sound.ENTITY_FOX_SLEEP;
                break;
            case "SCREECH":
                isFox = true;
                sound = Sound.ENTITY_FOX_SCREECH;
                break;
            case "HURT":
                isFox = true;
                sound = Sound.ENTITY_FOX_HURT;
                break;
            case "EAT":
                isFox = true;
                sound = Sound.ENTITY_FOX_EAT;
                break;
            case "ANGRY", "ANGY":
                isFox = true;
                sound = Sound.ENTITY_FOX_AGGRO;
                break;
        }

        if ( isFox ) {
            player.playSound(player, sound, 1f, 1f);
        }
    }

    public static void SetCanPounce(Player player, Boolean start) {
        UUID uuid = player.getUniqueId();
        
        if ( start == true ) {

            if ( canPounce.containsKey(uuid) != true ) {
                canPounce.put(uuid, true);
            }
            
        } else {

            if ( canPounce.containsKey(uuid) ) {
                
                if ( canPounce.get(uuid) == true ) {
                    canPounce.put(uuid, true);
                } else {
                    canPounce.put(uuid, false);
                }

            }

        }
    }

    public static Boolean GetCanPounce(Player player) {
        // return canPounce;
        UUID uuid = player.getUniqueId();

        if ( canPounce.containsKey(uuid) ) {
            return canPounce.get(uuid);
        }

        return false;
    }

    public static void FoxPounce(Player player) {
        UUID uuid = player.getUniqueId();
        
        if ( canPounce.containsKey(uuid) ) {
            long now = System.currentTimeMillis();

            // Cooldown. 5 seconds
            if ( pounceCooldown.containsKey(uuid) && now - pounceCooldown.get(uuid) < 5000 ) {
                /// removed cuz it sends the message alot
                // SemiFunc.SendMessageToPlayer(player, "§cYou're too tired to pounce.");
                return;
            }
            
            // was < 1
            if ( player.getFoodLevel() < 2 ) {
                /// removed cuz it sends the message alot
                // SemiFunc.SendMessageToPlayer(player, "§cYou're too hungy to pounce.");
                return;
            }

            pounceCooldown.put(uuid, now);

            // Launch the foxo so she actually pounces
            Vector direction = player.getLocation().getDirection().multiply(1.5);
            direction.setY(0.8);
            player.setVelocity(direction);

            // The silly particle effect
            Location particleLocation = player.getLocation().add(0, 1, 0);
            player.getPlayer().spawnParticle(Particle.CLOUD, particleLocation, 20, 0.3, 0.3, 0.2, 0.05);

            // player.playSound(player, Sound.ENTITY_FOX_AGGRO, 1f, 1f);
            player.setFoodLevel(player.getFoodLevel() - 1);
        }
    }

    public static void LoadFoxInventories(Player player) {
        mighty.load(player);
        snout.load(player);
    }

    public static void SaveFoxInventories(Player player) {
        mighty.save(player);
        snout.save(player);
    }

    public static void OpenFoxInventory(Player player, PlayerInteractEvent event) {
        if ( event.hasItem() ) {
            ItemStack item = event.getItem();
            ItemMeta itemMeta = event.getItem().getItemMeta();

            // If is interactable
            if ( SemiCraft.IsInteractable(item) ) {
                return;
            }

            // Skip opening if block
            // if ( SemiCraft.IsInteractable(event.getClickedBlock()) ) {
            //     return;
            // }

            if ( itemMeta.hasDisplayName() ) {
                // TODO: Maybe use smth not deprecated?

                // Skip opening if yon fish is in hand
                if ( itemMeta.getDisplayName().equals("Yon's Fish") ) {
                    return;
                }

                // Skip opening if itemMeta hasfood thing
                if ( itemMeta.hasFood() ) {
                    return;
                }
            }

            // Now open depending if left or right clicked
            if ( event.getAction().isLeftClick() ) {
                player.openInventory(snout.get(player));
            } else if ( event.getAction().isRightClick() ) {
                player.openInventory(mighty.get(player));
            }
            SemiLogger.LogTemp("Open inventory for smth");
        }
    }
}
