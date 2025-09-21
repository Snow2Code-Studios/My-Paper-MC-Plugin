package org.snow2code.util.interfaces;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

import org.snow2code.plugin.Main;

public interface CustomItem {
    JavaPlugin plugin = Main.Plugin;

    ItemStack createItem();
    Recipe createRecipe();
    NamespacedKey getKey();
}
