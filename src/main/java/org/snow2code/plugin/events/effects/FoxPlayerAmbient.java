package org.snow2code.plugin.events.effects;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.bukkit.metadata.FixedMetadataValue;
import org.snow2code.local.SemiSnow;
import org.snow2code.util.*;

import java.util.UUID;

import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class FoxPlayerAmbient implements Listener {

    private void what_does_the_fox_say(Player player) {
        boolean hostileNear = player.getWorld().getNearbyEntities(player.getLocation(), 10, 5, 10, entity -> {
            if ( !(entity instanceof Monster) ) {
                return false;
            } else {
                Monster hostile = (Monster) entity;

                return hostile.isAggressive();
            }
        }).size() > 0;

        if ( hostileNear ) {
            SemiFox.FoxSound("SCREECH", player);
        } else {
            SemiFox.FoxSound("AMBIENT", player);
        }
    }

    private void ambient(Player player) {
        long delay = 20 * (10 + (int)(Math.random() * 20));

        SemiFunc.RunLater(
            () -> {
                if ( SemiFox.idleSoundSkip ) {
                    SemiFunc.RunLater(
                        () -> {
                            what_does_the_fox_say(player);
                        }, 10L // 50L
                    );
                } else {
                    if ( player.isOnline() && !player.isSleeping() && !player.isDead() ) {
                        what_does_the_fox_say(player);

                        ambient(player);
                    }
                }
            }, delay
        );
    }

    // If the player is Snowy, play Fox Ambient sound and load her fox inventories
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (SemiSnow.IsSnowy(player) ) {
            SemiFox.LoadFoxInventories(player);

            ambient(player);
        }
    }

    // If the player is Snowy, save her fox inventories
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if ( SemiSnow.IsSnowy(player) ) {
            SemiFox.SaveFoxInventories(player);
        }
    }

    // If the player is Snowy, start sleep sound
    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();

        if ( SemiSnow.IsSnowy(player) ) {
            int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                if ( !player.isSleeping() ) {
                    Bukkit.getScheduler().cancelTask(player.getMetadata("foxSleepTask").get(0).asInt());
                    player.removeMetadata("foxSleepTask", plugin);
                    return;
                }

            }, 0L, 20L);

            player.setMetadata("foxSleepTask", new FixedMetadataValue(plugin, taskId));
        }
    }

    // If the player is Snowy, stop sleep sound
    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();

        if ( player.hasMetadata("foxSleepTask") ) {
            Bukkit.getScheduler().cancelTask(player.getMetadata("foxSleepTask").get(0).asInt());
            player.removeMetadata("foxSleepTask", plugin);
        }
    }

    // If player is snowy and attacked with empty hand, play fox bite sound
    @EventHandler
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent event) {
        if ( event.getDamager() instanceof Player ) {
            Player player = (Player) event.getDamager();

            if ( SemiSnow.IsSnowy(player) ) {
                if ( player.getInventory().getItemInMainHand().getType().isAir() ) {
                    SemiFox.FoxSound("BITE", player);
                }
            }
        }
    }

    // If a fox spawns, get the fox to trust Snowy
    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if ( event.getEntity().getType() == EntityType.FOX ) {
            Fox fox  = (Fox) event.getEntity();
            Player snowy = SemiFunc.GetPlayer("snow2code");

            if ( snowy != null ) {
                SpawnReason reason = event.getSpawnReason();
                UUID snowyUUID = snowy.getUniqueId();

                String spawnDbgMessage = String.format(
                        "a foxo spawned!\nUnique ID: %s\nEntity Id: %s\n Pos: %s %s %s",
                        fox.getUniqueId(),
                        fox.getEntityId(),
                        fox.getX(),
                        fox.getY(),
                        fox.getZ()
                );

                // Only if natural or breeding
                if ( reason == SpawnReason.NATURAL || reason == SpawnReason.BREEDING || reason == SpawnReason.SPAWNER_EGG ) {
                    /*
                    Only if snow/arctic fox.. not really optional imo
                    if ( fox.getFoxType() != Fox.Type.SNOW) return;

                    */
                    boolean isFoxCute = true;

                    SemiLogger.Debug(spawnDbgMessage);

                    if ( fox.getFirstTrustedPlayer() != null ) {
                        if ( fox.getFirstTrustedPlayer().getName().equals("snow2code") ) {
                            isFoxCute = false;
                            // HOW DARE YOU SNOWY SAY A FOX ISN'T CUTE!
                        }
                    }

                    if ( fox.getSecondTrustedPlayer() != null ) {
                        if ( fox.getSecondTrustedPlayer().getName().equals("snow2code") ) {
                            isFoxCute = false;
                            // HOW DARE YOU SNOWY SAY A FOX ISN'T CUTE!
                        }
                    }

                    if ( isFoxCute ) {
                        if ( !fox.isAdult() ) {
                            SemiLogger.Debug("It's a kit! awww <3");
                        }

                        server.dispatchCommand(
                                Bukkit.getConsoleSender(),
                                String.format(
                                        "data modify entity %s Trusted append from entity %s UUID",
                                        fox.getUniqueId(),
                                        "snow2code"
                                )
                        );
                    }
                }
            }
        }
    }

    // Fox Hurt/Death sounds
    @EventHandler
    public void playerDamaged(EntityDamageEvent event) {
        if ( event.getEntity() instanceof Player ) {
            Player player = (Player) event.getEntity();

            if ( SemiSnow.IsSnowy(player) ) {
                boolean foxHurtSound = false;
                boolean foxDeathSound = false;
                boolean foxSpecialSound = false;
                DamageCause cause = event.getCause();

                switch (cause.toString()) {
                    case "KILL":
                        // Caused by /kill command
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "WORLD_BORDER":
                        // Caused by the world border
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "CONTACT":
                        // Contact a block that deals damage, like a cactus
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "ENTITY_ATTACK":
                        // caused by a entity attacking
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    // case "ENTITY_SWEEP_ATTACK":
                    // Damage caused when an entity attacks another entity in a sweep attack
                    //          should be true if sweep attack, but player cant be attacked with sweep i think
                    //                     |
                    //     foxHurtSound = false;
                    //     foxDeathSound = false;
                    //     break;
                    case "PROJECTILE":
                        //  Caused by a projectile
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "SUFFOCATION":
                        // Caused by being suffocated in a block
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "FALL":
                        // Caused by fall damage
                        // foxHurtSound = true;
                        // foxDeathSound = false;

                        foxSpecialSound = true;
                        break;
                    case "FIRE":
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "FIRE_TICK":
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    // case "MELTING":
                    //// For snowman
                    //     foxHurtSound = false;
                    //     foxDeathSound = false;
                    case "LAVA":
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "DROWNING":
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "BLOCK_EXPLOSION":
                        SemiLogger.Debug("BLOCK_EXPLOSION Damage Cause");
                        // I do not know when you happen, so you'll be disabled until I find out
                        // foxHurtSound = false;
                        // foxDeathSound = false;
                        break;
                    case "ENTITY_EXPLOSION":
                        // Caused by being the the area of a entity explosion (Creeper)
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "VOID":
                        // Caused by falling into the void
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "LIGHTNING":
                        // Caused by being struck by lightning
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "SUICIDE":
                        // NOT SUICIDE!! qwq

                        // Caused by committing suicide
                        /*
                            EntityDamageEvent$DamageCause Note:

                         * This is currently only used by plugins, default commands
                         * like /minecraft:kill use (@link #KILL) to damage players.
                        */

                        foxHurtSound = false;
                        foxDeathSound = true;
                        break;
                    case "STARVATION":
                        // Damage caused by having a empty hunger bar
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "POISON":
                        // Caused by being poisoned
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "MAGIC":
                        // Caused by a damage potion or spell
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "WITHER":
                        // caused by Wither potion effect
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "FALLING_BLOCK":
                        // Caused by a falling block
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "THORNS":
                        // damage caused by thorns
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;

                    // MARKED AS DEPRECATED, REMOVED
                    // case "DRAGON_BREATH":
                    //     // caused by dragon breathing fire
                    //     foxHurtSound = false;
                    //     foxDeathSound = false;
                    //     break;
                    case "FLY_INTO_WALL":
                        // Caused when running in to a wall
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "HOT_FLOOR":
                        // Caused by magma block
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "CAMPFIRE":
                        // Caused by campfire
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    case "CRAMMING":
                        // Caused by too many entitys colliding together
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
                    // case "DRYOUT":
                    //     foxHurtSound = false;
                    //     foxDeathSound = false;
                    //     break;
                    case "FREEZE":
                        foxHurtSound = true;
                        foxDeathSound = false;
                        break;
//                    case "CUSTOM":
//                        foxHurtSound = false;
//                        foxDeathSound = false;
//                        break;
                }

                // Now actually the sounds

                if ( foxHurtSound ) {
                    SemiFox.FoxSound("DEATH", player);
                }

                if ( foxDeathSound ) {
                    SemiFox.FoxSound("DEATH", player);
                }

                // if ( foxSpecialSound ) {
                //     player.playSound(player, HURT, 0.1f, 0.7f);
                // }
            }
        }
    }
}
