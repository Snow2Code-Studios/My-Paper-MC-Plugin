package org.snow2code.plugin.playerleashing.soulleash;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.snow2code.util.SemiLogger;
import org.snow2code.util.interfaces.LeashSystem;
import org.snow2code.plugin.playerleashing.soulleash.main.*;

public final class LeashMain implements LeashSystem
{

    /*
     * 
     * NOTE:
     * All comments from ncxiaoyi (the SoulLeash plugin author)
     * is translated from their language to english.
     * 
    */

    public static LeashMain instance;
    public static final Map<UUID, List<UUID>> leashMap = new HashMap<>();
    public static final Map<UUID, BukkitRunnable> leashTasks = new HashMap<>();
    private static File leashDataFile;
    public static FileConfiguration leashDataConfig;
    private static Fence fence;
    public static final Map<UUID, List<UUID>> fenceLeashMap = new HashMap<>();
    private static File fenceLeashFile;
    public static FileConfiguration fenceLeashDataConfig;
    public static Map<UUID, List<String>> fenceData = new HashMap<>();


    @Override
    public void start()
    {
        // ncxiaoyi: Initialize all necessary data
        initializeLeashData();  // ncxiaoyi: Initialize configuration file
        initializeFenceLeashData();
        initializeManagers();


        // ncxiaoyi: Load data
        loadLeashData();  // ncxiaoyi: Loading data now

        // ncxiaoyi: Register Event
        registerEvents();

        // ncxiaoyi: Command registration
//        Executors executor = new Executors(this);
//        Objects.requireNonNull(plugin.getCommand("leashplayers")).setExecutor(executor);
//        Objects.requireNonNull(plugin.getCommand("leashplayers")).setTabCompleter(executor);


        // ncxiaoyi: Start task
        task.startLeashEffectTask(this);
        Helper leashHelper = new Helper();


        plugin.getServer().getPluginManager().registerEvents(new FoodShare(), LeashMain.plugin);
        plugin.getServer().getPluginManager().registerEvents(new BoneControl(), LeashMain.plugin);
        plugin.getServer().getPluginManager().registerEvents(new SummonAll(), LeashMain.plugin);
    }

    @Override
    public void stop()
    {
        if (fence != null) {
            fence.saveFenceLeashData();
        }
        saveLeashData();
        SemiLogger.Log("LeashPlayers 插件已关闭！");
    }


    
    // ncxiaoyi: Initialize leash data file.
    public static void initializeLeashData() {
        leashDataFile = new File(plugin.getDataFolder(), "leash_data.yml");
        if (!leashDataFile.exists()) {
            try {
                leashDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        leashDataConfig = YamlConfiguration.loadConfiguration(leashDataFile);
    }

    // ncxiaoyi: Initialize fence data file
    private void initializeFenceLeashData() {
        fenceLeashFile = new File(plugin.getDataFolder(), "fence_leash_data.yml");
        if (!fenceLeashFile.exists()) {
            try {
                fenceLeashFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fenceLeashDataConfig = YamlConfiguration.loadConfiguration(fenceLeashFile);
    }

    // ncxiaoyi: Initialize manager
    private void initializeManagers() {
        if (fence == null) {
            fence = new Fence();  // ncxiaoyi: Pass the plugin instance
            fence.loadFenceLeashData();
        }
    }

    // ncxiaoyi: 注册事件
    private void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(new Lookat(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new leash(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new dimension(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new kill(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new task(), plugin);
    }

    // ncxiaoyi: Load binding data
    public void loadLeashData() {
        if (leashDataConfig == null) {
            plugin.getLogger().warning("leashDataConfig is not initialized!");
            return;
        }
        leashMap.clear();
        leashDataConfig.getKeys(false).forEach(key -> {
            try {
                UUID sUUID = UUID.fromString(key);
                List<UUID> boundUUIDs = leashDataConfig.getStringList(key).stream()
                        .map(UUID::fromString)
                        .collect(Collectors.toList());
                leashMap.put(sUUID, boundUUIDs);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid UUID format in leash data: " + key);  // ncxiaoyi: Print invalid UUID key
            }
        });
    }

    public static void saveLeashData() {
        if (leashDataConfig == null) {
            plugin.getLogger().warning("leashDataConfig is not initialized!");
            return;
        }
        leashMap.forEach((uuid, mUUIDs) -> {
            leashDataConfig.set(uuid.toString(), mUUIDs.stream().map(UUID::toString).collect(Collectors.toList()));
        });
        try {
            leashDataConfig.save(leashDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ncxiaoyi: Get Fence instance
    public static Fence getFenceLeashManager() {
        return fence;
    }

}