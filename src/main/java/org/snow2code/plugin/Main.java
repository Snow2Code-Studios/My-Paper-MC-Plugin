package org.snow2code.plugin;

import io.papermc.paper.command.brigadier.BasicCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snow2code.local.SemiSnow;
import org.snow2code.plugin.commands.*;
import org.snow2code.plugin.events.*;
import org.snow2code.plugin.recipes.*;
import org.snow2code.plugin.recipes.drinks.*;
import org.snow2code.util.interfaces.CustomItem;
import org.snow2code.util.*;


public final class Main extends JavaPlugin {
    public static JavaPlugin Plugin;

    public static List<CustomItem> customItems = new ArrayList<>();
    public static Map<String, List<String>> joinMessages = new HashMap<>();

    public static String snowyMint = "§x§0§0§F§F§9§0";
    private static String prefix_Snowy = "§lSnowy";
    public static String snowyPurple = "§x§C§D§8§C§D§8";
    public static String snowyOranage = "§x§D§6§9§0§6§4";
    private static String prefix_Final = "§r";
    /*
            Color codes:
         mint    -   00FF90
         purple  -   CD8CD8
         oranage -   D69064
    */

    public static String snowyPrefix = snowyPurple + "[§r" + snowyMint + prefix_Snowy + snowyOranage + "]" + prefix_Final;
    public static Map<String, List<String>> getJoinMessages()
    {
        return joinMessages;
    }
//    public static List<String> startupLog = new ArrayList<>();

    public static void ReloadConfig()
    {
        Plugin.reloadConfig();
    }

    public static void loadMessages()
    {
        joinMessages.clear();
        if (Plugin.getConfig().isConfigurationSection("join-messages")) {
            for (String player : Plugin.getConfig().getConfigurationSection("join-messages").getKeys(false)) {
                List<String> msgs = Plugin.getConfig().getStringList("join-messages." + player);
                joinMessages.put(player.toLowerCase(), msgs);
            }
        }
    }

    @Override
    public void onLoad()
    {
        Plugin = this;
        SemiFunc.DiscoverLeashSystems();
    }

    @Override
    public void onEnable()
    {
        List<String> startupLog = SemiFunc.GetStartupLog();

        saveDefaultConfig();
        loadMessages();

        /* Register event s*/
        getServer().getPluginManager().registerEvents(new ConsumeListener(), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new BedListener(this), this);

        /* Register Commands */
        BasicCommand shareCords = new ShareCords();

        registerCommand("snowy", new SnowyCommand());
        registerCommand("c", shareCords);
        registerCommand("sharecords", shareCords);
        registerCommand("cords", shareCords);

        /*******        Add items          *********/

        SemiFunc.RegisterRecipes();
//        customItems.add(new YonFish());
//        customItems.add(new YonFrostedFish());
//
//        customItems.add(new ChillElixir());
//        customItems.add(new FrostfireLager());
//        customItems.add(new FrostyBrew());
//        customItems.add(new FrostyNight());
//        customItems.add(new FrozenDelight());
//        customItems.add(new GlacialWhiskey());
//        customItems.add(new IcebergShiver());
//        customItems.add(new SnowboundSpirit());
//        customItems.add(new SnowcapCider());
//        customItems.add(new SnowdriftAle());
//        customItems.add(new SnowfallShot());
//        customItems.add(new SnowNectar());

        /* Register all of em items!  */
        // for (CustomItem item : customItems) {
        //     Recipe recipe = item.createRecipe();
        //     Bukkit.addRecipe(recipe);
        //     startupLog.add("Registered new recipe: " + item.getKey().getKey());
        // }

        /*******************************************/

        /* LeashSystem */
        String system = getConfig().getString("leash-system");

        SemiLogger.Debug("LeashSystem: " + system);
        SemiFunc.StartLeashSystem();


        /* We're done here. */
        SemiLogger.Log("******************************************");
        SemiLogger.Log("");
        SemiLogger.Log("Loading Snowy's Plugin (Main) version " + getPluginMeta().getVersion());
        if (getPluginMeta().getVersion().contains("-DEV"))
        {
            SemiLogger.Log("");
            SemiLogger.Log("        --Development Build");
            SemiLogger.Log("");
            SemiLogger.Log("");
        }

        for (String line : startupLog)
        {
            SemiLogger.Log(line);
        }

        SemiLogger.Log("");
        SemiLogger.Log("******************************************");
    }

    @Override
    public void onDisable()
    {
        for(Player player : getServer().getOnlinePlayers())
        {
            if (SemiSnow.HasSpecials(player))
            {
                SemiSnow.ApplySpecialEffects(player, "join");
            }
        }

        SemiLogger.Log("******************************************");
        SemiLogger.Log("");
        SemiLogger.Log("     Disabling Snowy's Plugin (Main)");
        SemiLogger.Log("");
        SemiLogger.Log("******************************************");
    }

    @EventHandler
    public void onReload()
    {
        saveDefaultConfig();
        loadMessages();
    }
}
