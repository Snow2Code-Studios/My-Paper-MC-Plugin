package org.snow2code.plugin.Recipes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.snow2code.plugin.CustomItem;

import java.util.List;

public class SnowboundSpirit implements CustomItem {

    @Override
    public ItemStack createItem(JavaPlugin plugin) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();

        meta.displayName(Component.text(" ").color(NamedTextColor.DARK_AQUA));
        meta.lore(List.of(
                Component.text(" ").color(NamedTextColor.GRAY)
        ));

        meta.setColor(Color.fromRGB(0, 0, 0));

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "rarity"), PersistentDataType.STRING, "drink");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "custom_key"), PersistentDataType.STRING, "");

        potion.setItemMeta(meta);
        return potion;
    }

    @Override
    public ShapedRecipe createRecipe(JavaPlugin plugin) {
        ShapedRecipe recipe = new ShapedRecipe(getKey(plugin), createItem(plugin));
        recipe.shape("AAA", "XXX", "XXX");
        recipe.setIngredient('A', Material.GLASS_BOTTLE);
        recipe.setIngredient('A', Material.GLASS_BOTTLE);
        recipe.setIngredient('A', Material.GLASS_BOTTLE);
        return recipe;
    }

    @Override
    public NamespacedKey getKey(JavaPlugin plugin) {
        return new NamespacedKey(plugin, "");
    }
}
