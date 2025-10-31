import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.snow2code.util.interfaces.CustomRecipe;

public class RecipeNameHere implements CustomRecipe {

    /*
     * 
     * Change if you want the recipe to be enabled.
     * true: Enabled
     * false: Disabled
     * 
    */
    public boolean enabled = true;

    private static final String ITEM_NAME = "Item Name Here";

    @Override
    public void register()
    {
        // Change everything as needed, like Material.SNOW

        ItemStack item = new ItemStack(Material.SNOW);
        ItemMeta meta = item.getItemMeta();


        // The custom recipe, change it as needed
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), item);
        recipe.addIngredient(4, Material.SNOWBALL);

        plugin.getServer().addRecipe(recipe);
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(plugin, "the_item_key");
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}