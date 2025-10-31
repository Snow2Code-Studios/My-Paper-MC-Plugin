package org.snow2code.util;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.joml.Random;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;

import org.snow2code.util.interfaces.*;
import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class SemiFunc {

    public static final Random random = new Random();
    private static final List<String> leashSystems = new ArrayList<>();
    private static final List<String> customItems = new ArrayList<>();

    private static boolean leashSystemsDiscovered = false;
    private static LeashSystem activeLeashSystem;

    public static ItemStack hiimyonkagorandilikefish;

    private static List<String> startLog = new ArrayList<>();
    private static Map<String, List<String>> joinMessages = new HashMap<>();


    public static void LoadMessages() {
        joinMessages.clear();

        if ( plugin.getConfig().isConfigurationSection("join-messages") ) {
            for ( String player : plugin.getConfig().getConfigurationSection("join-messages").getKeys(false) ) {
                List<String> messages = plugin.getConfig().getStringList("join-messages." + player);
                joinMessages.put(player.toLowerCase(), messages);
            }
        }

        SemiLogger.Debug("meow meow, join messages " + joinMessages);
    }

    public static Map<String, List<String>> GetJoinMessages() {
        return joinMessages;
    }

    public static List<String> GetStartupLog() {
        return startLog;
    }

    public static void SendMessageTEMP(Player player, String message) {
        player.sendMessage(message);
    }

    public static void SendMessageToSender(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    public static void SendMessageToPlayer(Player player, String message) {
        player.sendMessage(message);
    }

    public static void SendMessageToAllPlayers(String message) {
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            player.sendMessage(message);
        }
    }

    public static void SendMessageToOps(String message) {
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            if ( player.hasPermission("op") ) {
                player.sendMessage(message);
            }
        }
    }

    public static void RunLater(Runnable function, long delay) {
        Bukkit.getScheduler().runTaskLater(
            plugin,
            function,
            delay
        );
    }

    /*
          Minecraft times
        Day: 0 - 12300 ticks
        Night 12300 - 24000 ticks
    */
    public static boolean IsDay(long time) {
        return time > 0 && time < 12300;
    }

    public static boolean IsNight(long time) {
        return time > 12300 && time < 24000;
    }

    public static int GetRandomNumber(int min, int max) {
        int rand = random.nextInt(max - min + 1) + min;

        return rand;
    }

    public static void DiscoverRecipe(Player player, String item) {
        NamespacedKey itemNamespace = NamespacedKey.fromString(item);

        if ( !player.hasDiscoveredRecipe(itemNamespace) ) {
            player.discoverRecipe(itemNamespace);
        }
    }

    public static Player GetPlayer(String name) {
        Player player = server.getPlayer(name);

        return player;
    }

    public static Boolean DoesHasPermission(CommandSender sender, String permission) {
        if ( sender.hasPermission(permission) ) {
            return true;
        }
        SendMessageToSender(sender, ChatColor.RED + "You don't have access to that command.");
        return false;
    }

    public static List<String> GetCustomItems() {
        return customItems;
    }

    public static void LeashMessage(String what, String type, Player master, Player pet, long left) {
        boolean sendToMaster = false;
        boolean sendToPet = false;
        boolean specialMessage = false;

        String masterMessage = "";
        String petMessage = "";

        String petPronouns_HimHer = SemiPronouns.HimHer(pet);

        switch (what) {
            case "abandonment":
                sendToMaster = true;
                sendToPet = true;

                masterMessage = String.format(
                        "%s You've let %s%s free, now %s can freely wander.",
                        ChatColor.RED,
                        ChatColor.AQUA,
                        pet.getName(),
                        petPronouns_HimHer
                );
                petMessage = String.format(
                        "%s%s%s has let you free! now you can wander freely!",
                        ChatColor.AQUA,
                        master.getName(),
                        ChatColor.GREEN
                );

                break;
            case "leashed":
                int rand = GetRandomNumber(1, 0);

                sendToMaster = true;
                sendToPet = true;


                if ( petPronouns_HimHer.equals("she") ) {
                    masterMessage = String.format(
                            "%sYou leashed %s%s%s, now %s are yours!",
                            ChatColor.GREEN,
                            ChatColor.AQUA,
                            pet.getName(),
                            ChatColor.GREEN,
                            petPronouns_HimHer
                    );
                } else {
                    masterMessage = String.format(
                            "%sYou leashed %s%s%s, now %s is yours!",
                            ChatColor.GREEN,
                            ChatColor.AQUA,
                            pet.getName(),
                            ChatColor.GREEN,
                            petPronouns_HimHer
                    );
                }

                if ( rand == 1 ) {
                    petMessage = String.format(
                            "%sYou've been leashed by %s%s%s! Try to resist if you dare...",
                            ChatColor.GREEN,
                            ChatColor.AQUA,
                            master.getName(),
                            ChatColor.GREEN
                    );
                } else if ( rand == 2 ) {
                    petMessage = String.format(
                            "%sYou've been leashed by %s%s%s! Better follow or face the consequences!",
                            ChatColor.GREEN,
                            ChatColor.AQUA,
                            master.getName(),
                            ChatColor.GREEN
                    );
                } else if ( rand == 3 ) {
                    petMessage = String.format(
                            "%sOh dear... %s%s%s has put you on a leash!",
                            ChatColor.GREEN,
                            ChatColor.AQUA,
                            master.getName(),
                            ChatColor.GREEN
                    );
                } else if ( rand == 4 ) {
                    petMessage = String.format(
                            "%sLeashed up by %s%s%s! You can try to escape... if you can!",
                            ChatColor.GREEN,
                            ChatColor.AQUA,
                            master.getName(),
                            ChatColor.GREEN
                    );
                } else {
                    petMessage = String.format(
                            "%sYou've been leashed by %s%s%s!",
                            ChatColor.GREEN,
                            ChatColor.AQUA,
                            master.getName(),
                            ChatColor.GREEN
                    );
                }

                break;
            case "stuck":
                sendToMaster = true;

                masterMessage = String.format(
                        "§eYour little pet §d %s §e seems to be stuck, meow!",
                        pet.getName()
                );

                break;
            case "gagged_start":
                specialMessage = true;

                SendMessageToAllPlayers(
                        String.format(
                                "<%s> Wuwu... I can’t speak qwq",
                                pet.getName()
                        )
                );

                break;
            case "gagged_end":
                sendToMaster = true;
                sendToPet = true;

                masterMessage = String.format(
                        "§aYou decide to let your little pet %s obediently speak again",
                        pet.getName()
                );
                petMessage = "§aYou can speak obediently again owo";

                break;
            case "cooldown":
                sendToMaster = true;

                masterMessage = String.format(
                        "Cooling, don't always think about teleportation, pay more attention to your little pet (still needs %i seconds!)",
                        left
                );

                break;
            case "obediently stay":
                sendToMaster = true;

                masterMessage = "§dYour little pet will stay here obediently =v=";

                break;
            case "summon pet":
                sendToMaster = true;

                masterMessage = String.format(
                        "%sYou summoned your little pet %s%s%s over here! =v=",
                        ChatColor.GREEN,
                        ChatColor.AQUA,
                        pet.getName(),
                        ChatColor.GREEN
                );

                break;
            default:
                break;
        }

        if ( specialMessage == false ) {
            if ( sendToMaster ) {
                if (type.toLowerCase().equals("action_bar")) {
                    master.sendActionBar(masterMessage);
                } else {
                    SendMessageToPlayer(master, masterMessage);
                }
            }

            if ( sendToPet ) {
                if ( type.toLowerCase().equals("action_bar") ) {
                    pet.sendActionBar(petMessage);
                } else {
                    SendMessageToPlayer(pet, petMessage);
                }
            }
        }
    }


    private static void loadRecipeClass(JarEntry entry, String packageName) {
        String className = entry.getName()
            .replace("/", ".")
            .replace(".class", "");
        
            if ( className.startsWith(packageName) ) {
                loadRecipeClass(className);
            }
    }

    private static void loadRecipeClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);

            if ( CustomRecipe.class.isAssignableFrom(clazz) && !clazz.isInterface() ) {
                CustomRecipe recipe = (CustomRecipe) clazz
                    .getDeclaredConstructor()
                    .newInstance();
                
                if ( recipe.isEnabled() ) {
                    recipe.register();
                    customItems.add(recipe.getKey().toString());
                    startLog.add("Registered recipe: " + recipe.getKey());
                } else {
                    startLog.add("Skipped disabled recipe: " + recipe.getKey());
                }
            }
        } catch (Exception e) {
            startLog.add(ChatColor.YELLOW + "Failed to load recipe class: " + className);
            SemiLogger.Warn("Failed to load recipe class: " + className);
            e.printStackTrace();
        }
    }

    public static void RegisterRecipes() {
        String packageName = "org.snow2code.plugin.recipes";
        String path = packageName.replace(".", "/");

        try {
            Enumeration<URL> resources = plugin.getClass().getClassLoader().getResources(path);

            while ( resources.hasMoreElements() ) {
                URL resource = resources.nextElement();

                if ( resource.getProtocol().equals("jar") ) {
                    JarURLConnection conn = (JarURLConnection) resource.openConnection();
                    try (var jarFile = conn.getJarFile() ) {
                        jarFile.stream()
                            .filter(e -> e.getName().startsWith(path) && e.getName().endsWith(".class"))
                            .forEach(entry -> loadRecipeClass(entry, packageName));
                    }
                } else if ( resource.getProtocol().equals("file") ) {
                    File dir = new File(resource.getFile());

                    if ( dir.exists() && dir.isDirectory() ) {
                        for ( File f : dir.listFiles() ) {
                            if ( f.getName().endsWith(".class") ) {
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

    public static void RegisterCommands() {
        SemiLogger.Warn("Bullshit. So I gotta register them maunally.");
    }

    public static void DiscoverLeashSystems() {
        if ( leashSystemsDiscovered != true ) {
            leashSystemsDiscovered = true;
            leashSystems.clear();

            String packageName = "org.snow2code.plugin.playerleashing";
            String path = packageName.replace(".", "/");

            try {
                Enumeration<URL> resources = plugin.getClass().getClassLoader().getResources(path);

                while ( resources.hasMoreElements() ) {
                    URL resource = resources.nextElement();

                    if ( resource.getProtocol().equals("jar") ) {
                        JarURLConnection conn = (JarURLConnection) resource.openConnection();

                        try ( var jarFIle = conn.getJarFile() ) {
                            jarFIle.stream()
                                .map(JarEntry::getName)
                                .filter(name -> name.startsWith(path + "/"))
                                .map(name -> name.substring(path.length() + 1))
                                .filter(name -> name.endsWith("/"))
                                .map(name -> name.replace("/", ""))
                                .forEach(leashSystems::add);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String ValidateLeashSystemString(String system) {
        String systemLow = system.toLowerCase();


        for ( String leashSys : leashSystems ) {
            if ( leashSys.toLowerCase().equals(systemLow) ) {
                return systemLow;
            }
        }

        SemiLogger.Warn("Invalid leash-system in config (" + system + "), falling falling back to SoulLeash Player Leash System");

        return "soulleash";
    }

    public static void StartLeashSystem() {
        String leashSystem = ValidateLeashSystemString(plugin.getConfig().getString("leash-system"));

        try {
            // org.snow2code.plugin.playerleashing.<chosen>.LeashMain
            String className = "org.snow2code.plugin.playerleashing." + leashSystem + ".LeashMain";
            Class<?> clazz = Class.forName(className);

            if ( LeashSystem.class.isAssignableFrom(clazz) ) {
                LeashSystem system = (LeashSystem) clazz.getDeclaredConstructor().newInstance();
                system.start();
                activeLeashSystem = system;
                startLog.add("Activated leash system: " + leashSystem);
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
