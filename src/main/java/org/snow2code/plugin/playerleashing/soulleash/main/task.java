package org.snow2code.plugin.playerleashing.soulleash.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.snow2code.plugin.playerleashing.soulleash.LeashMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.snow2code.plugin.playerleashing.soulleash.LeashMain.*;
import static org.snow2code.plugin.playerleashing.soulleash.main.leash.startLeashTask;

// ncxiaoyi: Listeners class: implements the Bukkit event listener interface for handling events and ongoing logic.
public class task implements Listener {

    // ncxiaoyi: Start a timed task that continuously adds effects to the bound.
    public static void startLeashEffectTask(LeashMain plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                // ncxiaoyi: Traverse all owner UUIDs
                for (UUID masterUUID : leashMap.keySet()) {
                    // ncxiaoyi: Get the online main player object.
                    Player master = Bukkit.getPlayer(masterUUID);
                    if (master != null && master.isOnline()) {
                        // ncxiaoyi: Get all players bound to this owner.
                        List<UUID> followers = leashMap.get(masterUUID);
                        if (followers != null) {
                            // ncxiaoyi: Traverse each bound player.
                            for (UUID followerUUID : followers) {
                                Player follower = Bukkit.getPlayer(followerUUID);
                                // ncxiaoyi: Only effective for online binders.
                                if (follower != null && follower.isOnline()) {
                                    // ncxiaoyi: Creates a life recovery effect (2 seconds), no particles.
                                    PotionEffect effect = new PotionEffect(
                                            PotionEffectType.REGENERATION,
                                            50, // ncxiaoyi: Continuous for 50 ticks (2 seconds)
                                            0,  // ncxiaoyi: Level 1 (0 level represents the first tier)
                                            true,  // ncxiaoyi: Force refresh has been effective.
                                            false // ncxiaoyi: No particle effects displayed.
                                    );
                                    // ncxiaoyi: add this effect
                                    follower.addPotionEffect(effect);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(LeashMain.plugin, 0L, 40L); // ncxiaoyi: Start immediately, execute every 40 ticks (2 seconds)
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer(); // ncxiaoyi: Get player object
        UUID playerUUID = player.getUniqueId(); // ncxiaoyi: Get the player's unique ID

        // ncxiaoyi: Check if the player has already been bound to the fence.
        if (LeashMain.getFenceLeashManager().isPlayerOnFence(player)) {
            return; // ncxiaoyi: If so, return directly, avoiding the execution of other binding-related logic.
        }

        // ncxiaoyi: Execute after a delay of 1 second to ensure the player's position has fully loaded.
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            // ncxiaoyi: If the player is an S player (owner), restore the binding task.
            if (leashMap.containsKey(playerUUID)) {
                List<UUID> mUUIDs = leashMap.get(playerUUID); // ncxiaoyi: Get all M (servant) UUIDs bound to S.
                for (UUID mUUID : mUUIDs) {
                    Player m = Bukkit.getPlayer(mUUID); // ncxiaoyi: 获取每个 M 的玩家对象
                    if (LeashMain.getFenceLeashManager().isPlayerOnFence(m)) { // ncxiaoyi: Added check: if the servant is bound to the fence, skip teleportation and binding.
                        continue;
                    }
                    if (m != null && m.isOnline()) { // ncxiaoyi: If M is online
                        // ncxiaoyi: Restore the binding task, allowing M to follow S.
                        startLeashTask(player, m);
                        m.teleport(player.getLocation()); // ncxiaoyi: Transfer M to the position of S
                        Helper.attachLeash(m, player);
                    }
                }
            }

            // ncxiaoyi: 如果玩家是 M 玩家（仆从），尝试从 pendingTeleport 中恢复绑定
            if (leashDataConfig.contains("pendingTeleport." + playerUUID)) {
                String sUUIDString = leashDataConfig.getString("pendingTeleport." + playerUUID); // ncxiaoyi: 获取 S 的 UUID 字符串
                UUID sUUID = UUID.fromString(sUUIDString); // ncxiaoyi: 转换为 UUID
                Player s = Bukkit.getPlayer(sUUID); // ncxiaoyi: 获取 S 的玩家对象

                if (s != null && s.isOnline()) { // ncxiaoyi: 如果 S 在线
                    // ncxiaoyi: 恢复绑定任务，让 M 跟随 S
                    startLeashTask(s, player);
                    player.teleport(s.getLocation()); // ncxiaoyi: 将 M 传送到 S 的位置
                }

                // ncxiaoyi: 清除 pendingTeleport 中的记录
                leashDataConfig.set("pendingTeleport." + playerUUID, null);
            }
            instance.saveLeashData(); // ncxiaoyi: 确保数据被保存
        }, 20L); // ncxiaoyi: 延迟 1 秒执行
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        UUID playerUUID = e.getPlayer().getUniqueId();

        // ncxiaoyi: 检查玩家是否是主人或仆从，避免重复处理
        if (leashMap.containsKey(playerUUID)) {
            // ncxiaoyi: 主人退出，保存数据
            return; // ncxiaoyi: 退出不进行绑定解除
        }

        // ncxiaoyi: 如果是仆从退出，则在主人的绑定列表中移除
        for (Map.Entry<UUID, List<UUID>> entry : leashMap.entrySet()) {
            Helper.removeLeash(playerUUID);
            if (entry.getValue().remove(playerUUID)) {
                UUID sUUID = entry.getKey(); // ncxiaoyi: 获取主人的 UUID
                Player s = Bukkit.getPlayer(sUUID); // ncxiaoyi: 获取主人对象

                // ncxiaoyi: 如果主人在线，则继续跟随行为
                if (s != null && s.isOnline()) {
                    // ncxiaoyi: 可根据需要处理
                }

                // ncxiaoyi: 如果该主人没有绑定其他仆从，移除主人
                if (entry.getValue().isEmpty()) {
                    leashMap.remove(sUUID);
                }
                break;
            }
        }
    }

    // ncxiaoyi: 监听实体受到伤害的事件
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        // ncxiaoyi: 检查伤害是否发生在玩家身上
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity(); // ncxiaoyi: 获取玩家对象
            UUID playerUUID = player.getUniqueId(); // ncxiaoyi: 获取玩家的 UUID

            // ncxiaoyi: 检查是否有任何主人绑定了这个玩家
            if (leashMap.values().stream().anyMatch(list -> list.contains(playerUUID))) {
                // ncxiaoyi: 如果伤害来源是摔落（Fall Damage），则取消伤害
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    event.setCancelled(true); // ncxiaoyi: 取消摔落伤害
                }
            }
        }
    }
}
