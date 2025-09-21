package org.snow2code.plugin.recipes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.snow2code.util.SemiLogger;
import org.snow2code.util.interfaces.CustomItem;
import org.snow2code.util.interfaces.CustomRecipe;

import java.util.List;

public class YonFish implements CustomRecipe {
    public boolean enabled = true;

    public static ItemStack hiimyonkagorandilikefish;

    private static final String ITEM_NAME = "Yon’s Fish";
    private static final List<Component> LORE = List.of(
            Component.text("").decoration(TextDecoration.ITALIC, false),
            Component.text("§7Carried from the §1river§7, kept close ever since.").decoration(TextDecoration.ITALIC, false)
    );

    @Override
    public void register()
    {
        ItemStack fish = new ItemStack(Material.SALMON);
        ItemMeta meta = fish.getItemMeta();

        meta.displayName(Component.text(ITEM_NAME).color(NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        meta.lore(LORE);

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "rarity"), PersistentDataType.STRING, "epic");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "custom_key"), PersistentDataType.STRING, "yonkagor_fish");

        meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
        meta.addEnchant(Enchantment.SHARPNESS, 17, true);
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);

        FoodComponent food = meta.getFood();
        food.setCanAlwaysEat(true);
        food.setNutrition(100);
        food.setSaturation(5f);
        meta.setFood(food);

        fish.setItemMeta(meta);

        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), fish);
        recipe.addIngredient(Material.SALMON);
        recipe.addIngredient(Material.COOKED_SALMON);

        hiimyonkagorandilikefish = fish;

        plugin.getServer().addRecipe(recipe);
    }

    @Override
    public NamespacedKey getKey() {
//        return "yonkagor_fish";
        return new NamespacedKey(plugin, "yonkagor_fish");
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

//    public static ItemStack hiimyonkagorandilikefish;
//
//    @Override
//    public ItemStack createItem() {
//    }
//
//    @Override
//    public ShapelessRecipe createRecipe() {
//    }
//
//    @Override
//    public NamespacedKey getKey() {
//    }
}
