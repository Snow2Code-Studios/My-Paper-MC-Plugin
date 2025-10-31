package org.snow2code.plugin;

import io.papermc.paper.command.brigadier.BasicCommand;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

//SNOW
import org.snow2code.local.SemiSnow;
import org.snow2code.plugin.commands.*;
import org.snow2code.plugin.events.*;
import org.snow2code.util.*;
// import org.snow2code.util.interfaces.*;


public class Snow2Code_Plugin extends JavaPlugin {
    public static JavaPlugin Plugin;
    public static JavaPlugin plugin;
    public static Server server;
    public static PluginManager manager;

    public static void ReloadConfig() {
        plugin.reloadConfig();
    }

    void footer(String forWhat) {
         List<String> startLog = SemiFunc.GetStartupLog();

        InputStream stream = plugin.getResource("version.yml");
        YamlConfiguration versionYML = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
        String version = getPluginMeta().getVersion();
        boolean isDev = versionYML.getBoolean("is_dev");
        String stage = versionYML.getString("stage");

        SemiLogger.Log("******************************************");
        SemiLogger.Log("");

        // if ( forWhat.equals(""))
        switch (forWhat.toLowerCase()) {
            case "start":
                SemiLogger.Log("Loading Snowy's Plugin version " + version);

                if ( isDev ) {
                    SemiLogger.Log("");
                    SemiLogger.Log("         --Development Build | " + stage);
                    SemiLogger.Log("");
                    SemiLogger.Log("");
                }

                for ( String entry : startLog) {
                    SemiLogger.Log(entry);
                }

                break;
            case "disable":
                SemiLogger.Log("         Disabling Snowy's Plugin");
                break;
        }

        SemiLogger.Log("");
        SemiLogger.Log("******************************************");
    }

    void registerEvents() {
        /// TEMP! REMOVE LATER
        // manager.registerEvents(new EventTest(), plugin);
        // SemiFox.RegisterFox();

        // manager.registerEvents(new PlayerItemConsume(), plugin);
        manager.registerEvents(new PlayerJoinQuit(), plugin);
        /*
        manager.registerEvents(new CraftItem(), plugin);
        manager.registerEvents(new PlayerBed(), plugin);
        manager.registerEvents(new Inventory(), plugin);
        manager.registerEvents(new Leashing(), plugin);
        */
    }

    void registerCommands() {
        registerCommand("snowy", new Snowy());
        /*
        BasicCommand cords = new CordsCommand();
        BasicCommand yonkagor = new YonKaGorCommand();
        BasicCommand foxinv = new FoxInventoryCommand();

        registerCommand("snowy", new SnowyCommand());
        registerCommand("cords", cords);
        registerCommand("yonkagor", yonkagor);
        registerCommand("yon", yonkagor);
        registerCommand("mouth", foxinv);
        registerCommand("pounce", new FoxPounceCommand());
        registerCommand("screech", new FoxScreechCommand());
        registerCommand("fox", new FoxCommand());
        */
    }


    @Override
    public void onLoad() {
        Plugin = this;
        plugin = this;

        server = getServer();
        manager = getServer().getPluginManager();

        SemiConfig.OnPluginLoad();
        SemiFunc.DiscoverLeashSystems();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        SemiFunc.LoadMessages();

        registerEvents();
        registerCommands();


        /*******        Add Items          ********/
        // SemiFunc.RegisterRecipes();

        SemiFunc.StartLeashSystem();
        Advancements.registerAdvancements();

        footer("start");
    }

    @Override
    public void onDisable() {
        footer("disable");
    }

    /*
     @Override
     public void onReload() {
         saveDefaultConfig();
         SemiFunc.LoadMessages();
     }
    */
}
