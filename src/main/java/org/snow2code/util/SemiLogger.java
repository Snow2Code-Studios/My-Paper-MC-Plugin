package org.snow2code.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.snow2code.plugin.Main;
import java.util.logging.Logger;

public class SemiLogger {

    // changed from final to static because getLogger
    private static JavaPlugin plugin = Main.Plugin;


    // ANSI Colors
    public static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";


    // Functions
    public static void Log(String message) {
        plugin.getLogger().info(message);
    }

    public static void Info(String message) {
        plugin.getLogger().info(GREEN + "[INFO] " + RESET + message);
    }

    public static void Warn(String message) {
        plugin.getLogger().warning(YELLOW + "[WARN] " + RESET + message);
    }

    public static void Error(String message) {
        plugin.getLogger().severe(RED + "[ERROR] " + RESET + message);
    }

    public static void Debug(String message) {
        plugin.getLogger().info(CYAN + "[DEBUG] " + RESET + message);
    }

    public static void Custom(String prefix, String color, String message) {
        plugin.getLogger().info(color + "[" + prefix + "] " + RESET + message);
    }
    
    public static void Custom2(String color, String message) {
        // plugin.getLogger().info(color + message + RESET);
        Logger logger = Logger.getLogger("");
        logger.info(color + message + RESET);
    }
}
