package org.snow2code.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.snow2code.plugin.Recipes.*;
import org.snow2code.plugin.Events.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snow2code.REPO__SemiFunc;
import org.snow2code.REPO__SemiLogger;

public final class Main extends JavaPlugin {
    public static JavaPlugin Plugin;

    public static REPO__SemiFunc SemiFunc;
    public static REPO__SemiLogger SemiLogger;
    public static Map<String, List<String>> joinMessages = new HashMap<>();

    private final List<CustomItem> customItems = new ArrayList<>();
    public static Map<String, List<String>> getJoinMessages() {
        return joinMessages;
    }

    public void loadMessages() {
        joinMessages.clear();

        if (getConfig().isConfigurationSection("join-messages")) {
            for (String player : getConfig().getConfigurationSection("join-messages").getKeys(false)) {
                List<String> msgs = getConfig().getStringList("join-messages." + player);
                joinMessages.put(player.toLowerCase(), msgs);
            }
        }
    }

    @Override
    public void onLoad() {
        Plugin = this;
        SemiLogger = new REPO__SemiLogger(this);
    }

    @Override
    public void onEnable() {
        List<String> startupLog = new ArrayList<>();

        // Now actually do shit
        saveDefaultConfig();
        loadMessages();

        // Register events
        getServer().getPluginManager().registerEvents(new Consume(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);



        //******        Add items          ********
        //
        customItems.add(new YonFish());
        // customItems.add(new ChillElixir());
        // customItems.add(new FrostyBrew());
        // customItems.add(new SnowNectar());
        // customItems.add(new ());
        //
        //*****************************************

        // Register em all!
        for (CustomItem item : customItems) {
            Recipe recipe = item.createRecipe(this);
            Bukkit.addRecipe(recipe);
            startupLog.add("Registered new recipe: " + item.getKey(this).getKey());
//            SemiLogger.Info("Registered recipe: " + item.getKey(this).getKey());
        }


        // We're done here, log the startupLog
        SemiLogger.Log("******************************************");
        SemiLogger.Log("");
        SemiLogger.Log("Loading Snowy's Plugin (Main) version " + getPluginMeta().getVersion());
        if (getPluginMeta().getVersion().contains("-DEV")) {
            SemiLogger.Log("");
            SemiLogger.Log("    --Development Build");
        }
        SemiLogger.Log("");
        SemiLogger.Log("");
        for (String line : startupLog) {
            SemiLogger.Log(line);
        }
        SemiLogger.Log("");
        SemiLogger.Log("******************************************");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
//        getLogger().info("MyPlugin has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        SemiLogger.Info("cmdName: " + cmd.getName());
        if (cmd.getName().equalsIgnoreCase("snow2code")) {
            SemiLogger.Info(args[0]);
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                // if (!sender.hasPermission("snow2code.admin")) {
                //     sender.sendMessage(ChatColor.RED + "You don't have permission.");
                //     return true;
                // }

                // reloadConfig();
                // loadMessages();
                // sender.sendMessage(ChatColor.GREEN + "nyaa~ snowy config & messages reloaded ^w^");
                // return true;
//                if (sender.)
            }
            sender.sendMessage(ChatColor.YELLOW + "Usage: /snow2code reload");
            return true;
        }
        return false;
    }
}
