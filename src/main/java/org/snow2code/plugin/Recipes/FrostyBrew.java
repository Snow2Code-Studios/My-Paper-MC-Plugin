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

public class FrostyBrew implements CustomItem {

    @Override
    public ItemStack createItem(JavaPlugin plugin) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();

        meta.displayName(Component.text("Frosty Brew").color(NamedTextColor.AQUA));
        meta.lore(List.of(
                Component.text("Looks dangerous… but feels refreshing.").color(NamedTextColor.GRAY)
        ));

        meta.setColor(Color.fromRGB(180, 220, 255)); // icy tint

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "rarity"), PersistentDataType.STRING, "drink");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "custom_key"), PersistentDataType.STRING, "frosty_brew");

        potion.setItemMeta(meta);
        return potion;
    }

    @Override
    public ShapedRecipe createRecipe(JavaPlugin plugin) {
        ShapedRecipe recipe = new ShapedRecipe(getKey(plugin), createItem(plugin));
        recipe.shape("ABC", "XXX", "XXX");
        recipe.setIngredient('A', Material.GLASS_BOTTLE);
        recipe.setIngredient('B', Material.SUGAR);
        recipe.setIngredient('C', Material.SNOW_BLOCK);
        return recipe;
    }

    @Override
    public NamespacedKey getKey(JavaPlugin plugin) {
        return new NamespacedKey(plugin, "frosty_brew");
    }
}
