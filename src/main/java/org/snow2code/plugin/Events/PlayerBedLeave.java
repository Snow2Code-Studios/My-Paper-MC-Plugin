package org.snow2code.plugin.Events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.snow2code.REPO__SemiFunc;
import org.joml.Random;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class PlayerBedLeave implements Listener {
    private final JavaPlugin plugin;
    private final REPO__SemiFunc SemiFunc;

    private static final Random random = new Random();

    public PlayerBedLeave(JavaPlugin plugin) {
        this.plugin = plugin;
        this.SemiFunc = new REPO__SemiFunc(this.plugin);
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        plugin.getLogger().info("meow, bed leave");
    }
}
