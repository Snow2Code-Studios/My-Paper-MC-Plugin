package org.snow2code.util.interfaces;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import static org.snow2code.plugin.Snow2Code_Plugin.*;


public interface CustomItem {
    ItemStack createItem();
    Recipe createRecipe();
    NamespacedKey getKey();
}
