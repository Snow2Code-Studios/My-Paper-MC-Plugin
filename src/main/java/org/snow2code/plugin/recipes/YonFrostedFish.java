package org.snow2code.plugin.recipes;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import org.snow2code.util.SemiFunc;
import org.snow2code.util.interfaces.CustomRecipe;
import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class YonFrostedFish implements CustomRecipe {
    public boolean enabled = true;

    private static final String ITEM_NAME = "Yon's Frosted Fish";
    private static final List<Component> LORE = List.of(
        Component.text("").decoration(TextDecoration.ITALIC, false),
        Component.text("Looks delicious... but leaves your head spinning.").decoration(TextDecoration.ITALIC, false)
    );

    @Override
    public void register() {
        ItemStack fish = new ItemStack(Material.SALMON);
        ItemMeta meta = fish.getItemMeta();

        meta.displayName(Component.text(ITEM_NAME).color(NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        meta.lore(LORE);

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "rarity"), PersistentDataType.STRING, "epic");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "custom_key"), PersistentDataType.STRING, "yonkagor_fish");
        meta.setMaxStackSize(99);

        meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        meta.addEnchant(Enchantment.SHARPNESS, 5, true);

        FoodComponent food = meta.getFood();
        food.setCanAlwaysEat(true);
        food.setNutrition(100);
        food.setSaturation(5f);
        meta.setFood(food);

        fish.setItemMeta(meta);

        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), fish);
        recipe.addIngredient(Material.SALMON);
        recipe.addIngredient(SemiFunc.hiimyonkagorandilikefish);

        server.addRecipe(recipe);
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(plugin, "yonkagor_fish");
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
