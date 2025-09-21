package org.snow2code.util.interfaces;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.snow2code.plugin.Main;

public interface CustomRecipe {
    JavaPlugin plugin = Main.Plugin;
    boolean enabled = false;

    void register();
    NamespacedKey getKey();
    boolean isEnabled();
}
