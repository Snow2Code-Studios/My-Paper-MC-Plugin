package org.snow2code.plugin.recipes.drinks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.snow2code.util.interfaces.CustomItem;

import java.util.List;

public class FrostfireLager implements CustomItem {

    @Override
    public ItemStack createItem() {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();

        meta.displayName(Component.text("Frostfire Lager").color(NamedTextColor.DARK_AQUA));
        meta.lore(List.of(Component.text("A bold sip that tingles like frost and fire combined.").color(NamedTextColor.GRAY)));

        meta.setColor(Color.fromRGB(50, 100, 255));

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "rarity"), PersistentDataType.STRING, "drink");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "custom_key"), PersistentDataType.STRING, "frostfire_lager");

        potion.setItemMeta(meta);
        return potion;
    }

    @Override
    public ShapelessRecipe createRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), createItem());
        recipe.addIngredient(Material.GLASS_BOTTLE);
        recipe.addIngredient(Material.ICE);
        recipe.addIngredient(Material.SUGAR);
        return recipe;
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(plugin, "frostfire_lager");
    }
}
