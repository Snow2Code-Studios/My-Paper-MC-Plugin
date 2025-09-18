package org.snow2code.plugin;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

public interface CustomItem {
    ItemStack createItem(JavaPlugin plugin);
    Recipe createRecipe(JavaPlugin plugin);
    NamespacedKey getKey(JavaPlugin plugin);
}
