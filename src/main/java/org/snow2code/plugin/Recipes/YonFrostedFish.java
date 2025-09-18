package org.snow2code.plugin.Recipes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.snow2code.plugin.CustomItem;

import java.util.List;

public class YonFrostedFish implements CustomItem {
    private static final String ITEM_NAME = "Yon's Frosted Fish";
    private static final List<Component> LORE = List.of(
            Component.text("").decoration(TextDecoration.ITALIC, false),
            Component.text("Looks delicious… but leaves your head spinning.").decoration(TextDecoration.ITALIC, false)
    );

    @Override
    public ItemStack createItem(JavaPlugin plugin) {
        ItemStack fish = new ItemStack(Material.SALMON);
        ItemMeta meta = fish.getItemMeta();

        meta.displayName(Component.text(ITEM_NAME).color(NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        meta.lore(LORE);

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "rarity"), PersistentDataType.STRING, "epic");

        meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
        meta.addEnchant(Enchantment.SHARPNESS, 17, true);
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);

        FoodComponent food = meta.getFood();
        food.setCanAlwaysEat(true);
        food.setNutrition(100);
        food.setSaturation(5f);
        meta.setFood(food);

        fish.setItemMeta(meta);
        return fish;
    }

    @Override
    public ShapedRecipe createRecipe(JavaPlugin plugin) {
        ShapedRecipe recipe = new ShapedRecipe(getKey(plugin), createItem(plugin));
        recipe.shape("ABC", "XXX", "XXX");

        // A - YonFish (not a item nativily in the game, plugin crafting recipe)
        // B - Any achloal (e.g Snowfall Shot)

        // recipe.setIngredient('A', Material.whatever);
        return recipe;
    }

    @Override
    public NamespacedKey getKey(JavaPlugin plugin) {
        return new NamespacedKey(plugin, "yonfish_nausea");
    }
}
