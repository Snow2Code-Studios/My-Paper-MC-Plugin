package org.snow2code.util;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.joml.Random;

import org.snow2code.plugin.CustomItem;
import org.snow2code.plugin.Main;

public class SemiFunc {
    private static JavaPlugin plugin = Main.Plugin;
    private static final Random random = new Random();

    // Functions

    public static void SendMessageToSender(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    public static void SendMessageToPlayer(Player player, String message) {
        if (!player.getName().equals("CONSOLE")) {
            player.sendMessage(message);
        } else {
            SemiLogger.Log(message);
        }
    }

    public static void SendMessageToAllPlayers(String message) {
        // player.sendMessage(message);
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if (!player.getName().equals("CONSOLE")) {
                player.sendMessage(message);
            } else {
                SemiLogger.Log(message);
            }
        }
    }

    public static void SendMessageToOps(String message) {
        // player.sendMessage(message);
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if (player.hasPermission("op"))
            {
                player.sendMessage(message);
            }
        }
    }

    public static void DiscoverRecipe(Player player, CustomItem item) {
        if (!player.hasDiscoveredRecipe(item.getKey()))
        {
            player.discoverRecipe(item.getKey());
        }
    }

    // Default: 1.0
    public static void SetPlayerScale(Player player, double scale) {
        player.getAttribute(Attribute.SCALE).setBaseValue(scale);
        SemiLogger.Log("Set " + player.getName() + "'s scale to " + scale);
    }

    // Default: 20.0
    public static void SetPlayerMaxHealth(Player player, double health) {
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
        if (player.getHealth() == 20 || player.getHealth() == 20.0) {
            player.heal(29);
        }
        SemiLogger.Log("Set " + player.getName() + "'s max health to " + health);
    }

    // Default: 0.2 (according to player.getWalkSpeed()
    public static void SetPlayerSpeed(Player player, float speed) {
        player.setWalkSpeed(speed);
        SemiLogger.Log("Set " + player.getName() + "'s speed to " + speed);
    }

    // Default: 1.0
    public static void SetPlayerAttackDamage(Player player, double amount) {
        player.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(amount);
        SemiLogger.Log("Set " + player.getName() + "'s attack damage to " + amount);
    }

    // Default: 0.0
    public static void SetPlayerAttackKnockback(Player player, double amount) {
        player.getAttribute(Attribute.ATTACK_KNOCKBACK).setBaseValue(amount);
        SemiLogger.Log("Set " + player.getName() + "'s attack knockback to " + amount);
    }

    // Default: 0.41999998688697815
    public static void SetPlayerJumpStrength(Player player, double amount) {
        player.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(amount);
        SemiLogger.Log("Set " + player.getName() + "'s jump strength to " + amount);
    }

    // Default: 1.0
    public static void SetPlayerBurnTime(Player player, double amount) {
        player.getAttribute(Attribute.BURNING_TIME).setBaseValue(amount);
        SemiLogger.Log("Set " + player.getName() + "'s burn time to " + amount);
    }

    // Default:
    public static void SetPlayerWaterMovementEfficiency(Player player, double amount) {
        player.getAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY).setBaseValue(amount);
        SemiLogger.Log("Set " + player.getName() + "'s water movement efficiency to " + amount);
    }

    // Default:
    public static void SetPlayerSneakSpeed(Player player, double amount) {
        player.getAttribute(Attribute.SNEAKING_SPEED).setBaseValue(amount);
        SemiLogger.Log("Set " + player.getName() + "'s sneak speed to " + amount);
    }



    public static double GetPlayerAttackDamage(Player player) {
        return player.getAttribute(Attribute.ATTACK_DAMAGE).getValue();
    }

    private static float getFloat(FileConfiguration cfg, String path) {
        return (float) cfg.getDouble(path);
    }
}
