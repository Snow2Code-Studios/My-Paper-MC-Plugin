package org.snow2code.plugin.recipes.drinks;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import org.snow2code.util.SemiFunc;
import org.snow2code.util.interfaces.CustomRecipe;
import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class GlacialWhiskey implements CustomRecipe {
    public boolean enabled = true;

    @Override
    public void register() {
        ItemStack drink = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) drink.getItemMeta();

        meta.displayName(Component.text("Glacial Whiskey").color(NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        meta.lore(List.of(Component.text("Old and icy... warms the soul but blurs the mind.").color(NamedTextColor.GRAY)));

        meta.setColor(Color.fromRGB(0, 150, 180));

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "rarity"), PersistentDataType.STRING, "epic");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "custom_key"), PersistentDataType.STRING, "glacial_whiskey");

        drink.setItemMeta(meta);

        // ShapelessRecipe recipe = new ShapelessRecipe(getKey(), drink);
        ShapedRecipe recipe = new ShapedRecipe(getKey(), drink);
        recipe.shape("ABC", "XXX", "XXX");

        recipe.setIngredient('A', Material.GLASS_BOTTLE);
        recipe.setIngredient('B', Material.BLUE_ICE);
        recipe.setIngredient('C', Material.SUGAR);
        
        // SemiFunc. = drink;

        server.addRecipe(recipe);
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(plugin, "glacial_whiskey");
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
