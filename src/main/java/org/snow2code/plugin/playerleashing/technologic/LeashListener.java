package org.snow2code.playerleash.system.technologic;

import io.papermc.paper.entity.Leashable;
import org.bukkit.*;
import org.bukkit.block.data.Directional;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.snow2code.util.SemiFunc;
import org.snow2code.util.SemiLogger;

import java.util.*;

public class LeashListener implements Listener {
    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event)
    {
        PlayerLeash.unleashFromAll(event.getPlayer());
    }

    private String getPronouns(Player player)
    {
        if (player.getName().equals("snow2code"))
        {
            return "she";
        }

        return "they";
    }

    /* --- Event handlers below are mostly direct ports --- */

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event)
    {
        if (!(event.getRightClicked() instanceof Player pet)) return;

        HashSet<Leashable> petLeashables = PlayerLeash.leashed.get(pet);
        if (event.getHand() == EquipmentSlot.HAND && petLeashables != null)
        {
            for (Leashable leashedEntity : petLeashables)
            {
                if (leashedEntity.isLeashed() && leashedEntity.getLeashHolder() == event.getPlayer())
                {
                    String petPrononus = getPronouns(pet);
                    event.getPlayer().sendMessage(ChatColor.RED + "You abandoned " + ChatColor.AQUA + pet.getName() + ChatColor.RED + ", now " + petPrononus + " can wander freely.");
                    PlayerLeash.unleashFrom(pet, leashedEntity, true);
                    return;
                }
            }
        }

        ItemStack item = event.getPlayer().getInventory().getItem(event.getHand());
        if (item == null || item.getType() != Material.LEAD) return;

        if (item.hasItemMeta())
        {
            String uuidStr = item.getItemMeta().getPersistentDataContainer().get(PlayerLeash.targetedLeadKey, PersistentDataType.STRING);
            if (uuidStr != null)
            {
                if (uuidStr.equals(pet.getUniqueId().toString())) return;

                var meta = item.getItemMeta();
                meta.setLore(PlayerLeash.getTargetedLeadDesc(pet));
                meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
                meta.getPersistentDataContainer().set(PlayerLeash.targetedLeadKey, PersistentDataType.STRING, pet.getUniqueId().toString());
                item.setItemMeta(meta);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0F, 1.0F);
                return;
            }
        }

        PlayerLeash.leash(event.getPlayer(), pet);
        SemiFunc.SendMessageToPlayer(event.getPlayer(),
                ChatColor.LIGHT_PURPLE + "You leashed " + ChatColor.AQUA + pet.getName() + ChatColor.LIGHT_PURPLE + ", now she's yours!"
        );
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
        {
            item.setAmount(item.getAmount() - 1);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onPlayerMove(PlayerMoveEvent event)
    {
        HashSet<Leashable> leashedEntities = PlayerLeash.leashed.get(event.getPlayer());
        if (leashedEntities == null) return;

        for (Leashable leashedEntity : leashedEntities)
        {
            leashedEntity.teleport(PlayerLeash.getLeashLocation(event.getPlayer()));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onHitchBreak(HangingBreakEvent event)
    {
        for (Map.Entry<Player, HashSet<Leashable>> entry : PlayerLeash.leashed.entrySet())
        {
            for (Leashable leashedEntity : entry.getValue())
            {
                if (leashedEntity.getLeashHolder() == event.getEntity())
                {
                    PlayerLeash.unleashFrom(entry.getKey(), leashedEntity, true);
                    return;
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onItemDispense(BlockDispenseEvent event)
    {
        SemiLogger.Debug("The pet was forced to stay by " + event.getBlock().getType());
    //    sendMessage("§dThe little pet is obediently staying here =v=")

        if (!event.getItem().hasItemMeta()) return;
        String uuidStr = event.getItem().getItemMeta().getPersistentDataContainer().get(PlayerLeash.targetedLeadKey, PersistentDataType.STRING);

        if (uuidStr == null) return;

        event.setCancelled(true);
        if (!uuidStr.equals("none"))
        {
            UUID uuid = UUID.fromString(uuidStr);
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || player.getWorld() != event.getBlock().getWorld()) return;
            if (player.getLocation().distance(event.getBlock().getLocation()) > 50.0) return;

            var blockInFront = event.getBlock().getRelative(((Directional) event.getBlock().getBlockData()).getFacing());
            if (!blockInFront.getType().name().endsWith("_FENCE")) return;

            for (Entity entity : blockInFront.getWorld().getNearbyEntities(blockInFront.getBoundingBox()))
            {
                if (entity instanceof LeashHitch)
                {
                    for (Map.Entry<Player, HashSet<Leashable>> entry : PlayerLeash.leashed.entrySet())
                    {
                        for (Leashable leashedEntity : entry.getValue())
                        {
                            if (leashedEntity.getLeashHolder() == entity)
                            {
                                entity.remove();
                                PlayerLeash.unleashFrom(entry.getKey(), leashedEntity, true);
                                return;
                            }
                        }
                    }
                }
            }

            LeashHitch hitch = blockInFront.getWorld().spawn(blockInFront.getLocation(), LeashHitch.class, h -> {
                h.getPersistentDataContainer().set(PlayerLeash.targetedLeadKey, PersistentDataType.BYTE, (byte) 1);
            });
            PlayerLeash.leash(hitch, player);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onLeash(PlayerLeashEntityEvent event)
    {
        if (PlayerLeash.leashed.containsKey(event.getPlayer()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockPlace(BlockPlaceEvent event)
    {
        if (PlayerLeash.leashed.containsKey(event.getPlayer()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockBreak(BlockBreakEvent event)
    {
        if (PlayerLeash.leashed.containsKey(event.getPlayer()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && (event.getClickedBlock().getType().name().endsWith("PRESSURE_PLATE") || event.getClickedBlock().getType() == Material.TRIPWIRE))
        {
            return;
        }

        if (PlayerLeash.leashed.containsKey(event.getPlayer()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onDamage(EntityDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player player)) return;

        if (PlayerLeash.leashed.containsKey(player))
        {
            event.setDamage(0.0);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (!(event.getDamager() instanceof Player damager)) return;

        if (PlayerLeash.leashed.containsKey(damager))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onHitchBreakByPlayer(HangingBreakByEntityEvent event)
    {
        if (!(event.getRemover() instanceof Player remover)) return;

        if (PlayerLeash.leashed.containsKey(remover))
        {
            event.setCancelled(true);
        }
    }

    /*
    @EventHandler
    public void onPlayerRightClickFence(PlayerInteractEvent event)
    {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null || !isFence(event.getClickedBlock().getType())) return;
        // if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;


        event.getPlayer().sendMessage("onPlayerRightClickFence");
    }
    */

    private boolean isFence(Material material) {
        return material.name().endsWith("_FENCE");
    }

}
