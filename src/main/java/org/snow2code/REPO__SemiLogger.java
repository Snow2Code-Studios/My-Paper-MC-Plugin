package org.snow2code;

import org.bukkit.plugin.java.JavaPlugin;

public class REPO__SemiLogger {
    private final JavaPlugin plugin;

    // ANSI Colors
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    public REPO__SemiLogger(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Functions
    public void Log(String message) {
        plugin.getLogger().info(message);
    }

    public void Info(String message) {
        plugin.getLogger().info(GREEN + "[INFO] " + RESET + message);
    }

    public void Warn(String message) {
        plugin.getLogger().warning(YELLOW + "[WARN] " + RESET + message);
    }

    public void Error(String message) {
        plugin.getLogger().severe(RED + "[ERROR] " + RESET + message);
    }

    public void Debug(String message) {
        plugin.getLogger().info(CYAN + "[DEBUG] " + RESET + message);
    }

    public void Custom(String prefix, String color, String message) {
        plugin.getLogger().info(color + "[" + prefix + "] " + RESET + message);
    }
}
