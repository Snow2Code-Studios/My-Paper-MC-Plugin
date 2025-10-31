package org.snow2code.util.interfaces;

import org.bukkit.NamespacedKey;

public interface CustomRecipe {
    boolean enabled = false;

    void register();
    NamespacedKey getKey();
    boolean isEnabled();
}