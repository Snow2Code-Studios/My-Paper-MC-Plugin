package org.snow2code.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.snow2code.REPO__SemiLogger;
import org.snow2code.plugin.Recipes.*;
import org.snow2code.plugin.Events.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class Main extends JavaPlugin {
    public static JavaPlugin Plugin;
    public static REPO__SemiLogger SemiLogger;
    public static Map<String, List<String>> joinMessages = new HashMap<>();

    // Master list of custom items
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
        saveDefaultConfig();
        loadMessages();

        // Register events
        getServer().getPluginManager().registerEvents(new Consume(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);

        //******Add items here (only once!)********
        //
        customItems.add(new YonFish());
        // customItems.add(new ChillElixir());
        // customItems.add(new FrostyBrew());
        // customItems.add(new SnowNectar());
        // customItems.add(new ());
        // customItems.add(new ());
        // customItems.add(new ());
        // customItems.add(new ());
        // customItems.add(new ());
        // customItems.add(new ());
        // customItems.add(new ());
        // customItems.add(new ());
        //
        //*****************************************

        // Register recipes in loop
        for (CustomItem item : customItems) {
            Recipe recipe = item.createRecipe(this);
            Bukkit.addRecipe(recipe);
            SemiLogger.Info("Registered recipe: " + item.getKey(this).getKey());
        }

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
            SemiLogger.Info(args);
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
