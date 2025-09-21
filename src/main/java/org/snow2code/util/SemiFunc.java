package org.snow2code.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.joml.Random;

import org.snow2code.plugin.recipes.YonFish;
import org.snow2code.util.interfaces.CustomItem;
import org.snow2code.plugin.Main;
import org.snow2code.util.interfaces.CustomRecipe;
import org.snow2code.util.interfaces.LeashSystem;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;

public class SemiFunc {
    private static JavaPlugin plugin = Main.Plugin;
    private static final Random random = new Random();
    private static final List<String> leashSystems = new ArrayList<>();
    private static final List<String> items = new ArrayList<>();

    private static boolean leashSystemsDiscovered = false;
    private static LeashSystem activeLeashSystem;
    private static YonFish YonFishItem;
    private static List<String> startupLog = new ArrayList<>();

    // Functions
    public static List GetStartupLog()
    {
        return startupLog;
    }

    public static void SendMessageToSender(CommandSender sender, String message)
    {
        sender.sendMessage(message);
    }

    public static void SendMessageToPlayer(Player player, String message)
    {
        player.sendMessage(message);
    }

    public static void SendMessageToAllPlayers(String message)
    {
        // player.sendMessage(message);
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.sendMessage(message);
        }
    }

    public static void SendMessageToOps(String message)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if (player.hasPermission("op"))
            {
                player.sendMessage(message);
            }
        }
    }

    public static void DiscoverRecipe(Player player, CustomItem item)
    {
        if (!player.hasDiscoveredRecipe(item.getKey()))
        {
            player.discoverRecipe(item.getKey());
        }
    }


    private static void loadRecipeClass(JarEntry entry, String packageName)
    {
        String className = entry.getName()
                .replace('/', '.')
                .replace(".class", "");

        if (className.startsWith(packageName)) {
            loadRecipeClass(className);
        }
    }

    private static void loadRecipeClass(String className)
    {
        try {
            Class<?> clazz = Class.forName(className);
            if (CustomRecipe.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                CustomRecipe recipe = (CustomRecipe) clazz
                        .getDeclaredConstructor()
                        .newInstance();

                if (recipe.isEnabled()) {
                    recipe.register();
                    startupLog.add("Registered recipe: " + recipe.getKey());
                } else {
                    startupLog.add("Skipped disabled recipe: " + recipe.getKey());
                }
            }
        } catch (Exception e) {
            startupLog.add(ChatColor.YELLOW + "Failed to load recipe class: " + className);
            SemiLogger.Warn("Failed to load recipe class: " + className);
            e.printStackTrace();
        }
    }

    public static void RegisterRecipes()
    {
        String packageName = "org.snow2code.plugin.recipes";
        String path = packageName.replace('.', '/');

        try {
            Enumeration<URL> resources = plugin.getClass().getClassLoader().getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();

                if (resource.getProtocol().equals("jar")) {
                    JarURLConnection conn = (JarURLConnection) resource.openConnection();
                    try (var jarFile = conn.getJarFile()) {
                        jarFile.stream()
                                .filter(e -> e.getName().startsWith(path) && e.getName().endsWith(".class"))
                                .forEach(entry -> loadRecipeClass(entry, packageName));
                    }
                } else if (resource.getProtocol().equals("file")) {
                    File dir = new File(resource.getFile());
                    if (dir.exists() && dir.isDirectory()) {
                        for (File f : dir.listFiles()) {
                            if (f.getName().endsWith(".class")) {
                                String className = packageName + "." + f.getName().replace(".class", "");
                                loadRecipeClass(className);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void DiscoverLeashSystems()
    {
        if (leashSystemsDiscovered != true)
        {
            leashSystemsDiscovered = true;
            leashSystems.clear();

            String packageName = "org.snow2code.plugin.playerleashing";
            String path = packageName.replace('.', '/');

            try {
                Enumeration<URL> resources = plugin.getClass().getClassLoader().getResources(path);

                while (resources.hasMoreElements()) {
                    URL resource = resources.nextElement();

                    if (resource.getProtocol().equals("jar")) {
                        JarURLConnection conn = (JarURLConnection) resource.openConnection();
                        try (var jarFile = conn.getJarFile()) {
                            jarFile.stream()
                                    .map(JarEntry::getName)
                                    .filter(name -> name.startsWith(path + "/"))
                                    .map(name -> name.substring(path.length() + 1)) // strip base path
                                    .filter(name -> name.endsWith("/"))            // keep only dirs
                                    .map(name -> name.replace("/", ""))            // clean dir names
                                    .forEach(leashSystems::add);
                        }
//                    } else if (resource.getProtocol().equals("file")) {
//                        File dir = new File(resource.getFile());
//                        if (dir.exists() && dir.isDirectory()) {
//                            for (File f : dir.listFiles()) {
//                                if (f.isDirectory()) {
//                                    leashSystems.add(f.getName());
//                                }
//                            }
//                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check because of DUMB subdirs!
            for (String sys : leashSystems)
            {
                if (sys.contains("main"))
                {
                    leashSystems.remove(sys);
                }
            }

            SemiLogger.Debug("Discovered leash systems: " + leashSystems);
        }
    }

    private static String ValidateLeashSystemString(String system)
    {
        String systemLow = system.toLowerCase();

        for (String leashSys : leashSystems)
        {
            if (leashSys.toLowerCase().equals(systemLow))
            {
                return systemLow;
            }
        }

        SemiLogger.Warn("Invalid leash-system in config: " + system + ", falling back to SoulLeash's Leash System");

        return "soulleash";
    }

    public static void StartLeashSystem()
    {
        String leashSystem = ValidateLeashSystemString(plugin.getConfig().getString("leash-system"));

        try {
            // Build class name: org.snow2code.plugin.playerleashing.<chosen>.LeashMain
            String className = "org.snow2code.plugin.playerleashing." + leashSystem + ".LeashMain";
            Class<?> clazz = Class.forName(className);

            if (LeashSystem.class.isAssignableFrom(clazz))
            {
                LeashSystem system = (LeashSystem) clazz.getDeclaredConstructor().newInstance();
                system.start();
                activeLeashSystem = system;
                startupLog.add("Activated leash system: " + leashSystem);
            } else {
                SemiLogger.Warn(className + " does not implement LeashSystem!");
            }
        } catch (Exception e) {
            SemiLogger.Warn("Failed to initialize leash system: " + leashSystem);
            e.printStackTrace();
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
