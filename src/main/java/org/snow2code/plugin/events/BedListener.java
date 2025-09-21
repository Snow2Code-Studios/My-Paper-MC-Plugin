package org.snow2code.plugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.joml.Random;
import org.snow2code.util.SemiLogger;

public class BedListener implements Listener {
    private final JavaPlugin plugin;

    private static final Random random = new Random();

    public BedListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        plugin.getLogger().info("meow, bed leave");
    }
    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        // Only trigger if the bed enter is successful
        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) return;

        Player player = event.getPlayer();

        if (plugin.getServer().getOnlinePlayers().size() == 1) {
            if (player.getName().equals("snow2code")) {
                SemiLogger.Debug(player.getName() + " is cuddling her own tail in bed, giving comfort and warmth");

                // Example effect (regen 30s)
                // player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 30, 0));
            }
        } else {
            for (Player other : plugin.getServer().getOnlinePlayers()) {
                if (other.equals(player)) continue; // skip self
                if (!other.isOnline()) continue;

                // Check if other player is in a bed
                if (other.isSleeping()) {
                    double distance = player.getLocation().distance(other.getLocation());

                    int range = 10; // Example X block range
                    if (distance <= range) {
                        // Instead of potion effect, just debug for now
                        SemiLogger.Debug(player.getName() + " and " + other.getName() + " are cuddling in beds (distance " + distance + ")");

                        // Example effect (regen 30s)
                        // player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 30, 0));
                        // other.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 30, 0));
                    }
                }
            }
        }
    }
}
