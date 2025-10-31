package org.snow2code.plugin.playerleashing.soulleash.main;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import org.snow2code.util.*;
import static org.snow2code.plugin.playerleashing.soulleash.LeashMain.*;

public class SummonAll implements Listener {

    private final Map<UUID, Long> cooldownMap = new HashMap<>();
    private static final long CD = 10 * 60 * 1000L; // 10分钟

    @EventHandler
    public void onUseStar(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) return;

        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType() != Material.NETHER_STAR) return;

        UUID id = p.getUniqueId();
        long now = System.currentTimeMillis();

        if (cooldownMap.containsKey(id) && now - cooldownMap.get(id) < CD) {
            long left = (CD - (now - cooldownMap.get(id))) / 1000;
            SemiFunc.LeashMessage("cooldown", "chat", p, p, left);
            return;
        }

        List<UUID> list = leashMap.get(id);
        if (list == null || list.isEmpty()) {
            return;
        }

        Location loc = p.getLocation();

        for (UUID uid : list) {
            Player f = Bukkit.getPlayer(uid);
            if (f != null && f.isOnline()) {
                f.teleport(loc);
//                Helper.attachLeash(f, p);
                f.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                SemiFunc.LeashMessage("summon pet", "chat", p, f, now);
            }
        }

        cooldownMap.put(id, now);
    }
}
