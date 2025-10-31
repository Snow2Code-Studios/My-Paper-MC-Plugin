package org.snow2code.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;
import org.bukkit.inventory.ItemStack;

public class SemiCraft {

    public static boolean IsInteractable(ItemStack item) {
        boolean isConsumable = IsConsumable(item);
        String type = item.getType().toString();

        // If isConsumable is true, return it
        if (isConsumable) {
            return isConsumable;
        }
        
        // Stupid but it might work
        switch (type) {
            case "ARMOR_STAND":
                return true;
            case "BOW":
                return true;
            case "CROSSBOW":
                return true;
            case "DEBUG_STICK":
                return true;
            case "EGG":
                return true;
            case "END_CRYSTAL":
                return true;
            case "ENDER_PEARL":
                return true;
            case "ENDER_EYE":
                return true;
            case "EXPERIENCE_BOTTLE":
                return true;
            case "FIRE_CHARGE":
                return true;
            case "FIREWORK_ROCKET":
                return true;
            case "FISHING_ROD":
                return true;
            case "FLINT_AND_STEEL":
                return true;
            case "GOAT_HORN":
                return true;
            case "ITEM_FRAME":
                return true;
            case "LEAD":
                return true;
            case "MAP":
                return true;
            case "NAME_TAG":
                return true;
            case "OMINOUS_BOTTLE":
                return true;
            case "OMINOUS_TRIAL_KEY":
                return true;
            // case "POTION":
            //     return true;
            case "REDSTONE":
                return true;
            case "SHEARS":
                return true;
            case "SHIELD":
                return true;
            case "SNOWBALL":
                return true;
            // case "SPLASH_POTION":
            //     return true;
            case "SPYGLASS":
                return true;
            case "STRING":
                return true;
            case "TRIAL_KEY":
                return true;
            case "TRIDENT":
                return true;
            case "WIND_CHARGE":
                return true;
            case "WOLF_ARMOR":
                return true;
            case "WRITABLE_BOOK":
                return true;
            case "WRITTEN_BOOK":
                return true;
            case "BARREL":
                return true;
            case "BEACON":
                return true;
            case "BELL":
                return true;
            case "BREWING_STAND":
                return true;
            case "CAMPFIRE":
                return true;
            case "CHEST":
                return true;
            case "CRAFTING_TABLE":
                return true;
            case "DAYLIGHT_DETECTOR":
                return true;
            case "DISPENSER":
                return true;
            case "ENCHANTING_TABLE":
                return true;
            case "JUKEBOX":
                return true;
            case "LEVER":
                return true;
            case "LIGHT":
                return true;
            case "NOTEBLOCK":
                return true;
            case "REDSTONE_WIRE":
                return true;
            case "REPEATER":
                return true;
            case "VAULT":
                return true;
            // case "":
            //     return true;
            // case "":
            //     return true;
        }


        // If the item type contains whatever
        if (type.contains("POTION")) {
            return true;
        } else if (type.contains("SHULKER_BOX")) {
            return true;
        } else if (type.contains("_BOOTS")) {
            return true;
        } else if (type.contains("_CHESTPLATE")) {
            return true;
        } else if (type.contains("_HELMET")) {
            return true;
        } else if (type.contains("_LEGGINGS")) {
            return true;
        } else if (type.contains("HORSE_ARMOR")) {
            return true;
        } else if (type.contains("_AXE")) {
            return true;
        } else if (type.contains("_HOE")) {
            return true;
        } else if (type.contains("_PICKAXE")) {
            return true;
        } else if (type.contains("_SHOVEL")) {
            return true;
        } else if (type.contains("_SWORD")) {
            return true;
        } else if (type.contains("MINECART")) {
            return true;
        } else if (type.contains("SEEDS")) {
            return true;
        } else if (type.contains("BUNDLE")) {
            return true;
        } else if (type.contains("FURNACE")) {
            return true;
        } else if (type.contains("ANVIL")) {
            return true;
        } else if (type.contains("HOPPER")) {
            return true;
        } else if (type.contains("_CAULDRON")) {
            return true;
        } else if (type.contains("BED")) {
            return true;
        } else if (type.contains("_BANNER")) {
            return true;
        } else if (type.contains("_BUTTON")) {
            return true;
        } else if (type.contains("BUCKET")) {
            return true;
        } else if (type.contains("_BOAT")) {
            return true;
        } else if (type.contains("_CHEST")) {
            return true;
        } else if (type.contains("DOOR")) {
            return true;
        } else if (type.contains("_SIGN")) {
            return true;
        } else if (type.contains("_GATE")) {
            return true;
        } else if (type.contains("TORCH")) {
            return true;
        // } else if (type.contains("")) {
        //     return true;
        }

        return false;

    }

    public static boolean IsInteractable(Block block) {
        String type = block.getType().toString();
        
        // Stupid but it might work
        switch (type) {
            case "ARMOR_STAND":
                return true;
            case "ITEM_FRAME":
                return true;
            case "REDSTONE":
                return true;
            case "STRING":
                return true;
            case "BARREL":
                return true;
            case "BEACON":
                return true;
            case "BELL":
                return true;
            case "BREWING_STAND":
                return true;
            case "CAMPFIRE":
                return true;
            case "CHEST":
                return true;
            case "CRAFTING_TABLE":
                return true;
            case "DAYLIGHT_DETECTOR":
                return true;
            case "DISPENSER":
                return true;
            case "ENCHANTING_TABLE":
                return true;
            case "JUKEBOX":
                return true;
            case "LEVER":
                return true;
            case "LIGHT":
                return true;
            case "NOTEBLOCK":
                return true;
            case "REDSTONE_WIRE":
                return true;
            case "REPEATER":
                return true;
            case "VAULT":
                return true;
            // case "":
            //     return true;
            // case "":
            //     return true;
        }


        // If the item type contains whatever
        if (type.contains("SHULKER_BOX")) {
            return true;
        } else if (type.contains("MINECART")) {
            return true;
        } else if (type.contains("FURNACE")) {
            return true;
        } else if (type.contains("ANVIL")) {
            return true;
        } else if (type.contains("HOPPER")) {
            return true;
        } else if (type.contains("_CAULDRON")) {
            return true;
        } else if (type.contains("BED")) {
            return true;
        } else if (type.contains("_BUTTON")) {
            return true;
        } else if (type.contains("_BOAT")) {
            return true;
        } else if (type.contains("_CHEST")) {
            return true;
        } else if (type.contains("DOOR")) {
            return true;
        } else if (type.contains("_SIGN")) {
            return true;
        } else if (type.contains("_GATE")) {
            return true;
        // } else if (type.contains("")) {
        //     return true;
        }

        return false;
    }

    public static boolean IsConsumable(ItemStack item) {
        // Stupid but it might work

        switch (item.getType().toString()) {
            case "APPLE":
                return true;
            case "BAKED_POTATO":
                return true;
            case "BEEF":
                return true;
            case "BEETROOT_SOUP":
                return true;
            case "BREAD":
                return true;
            case "CARROT":
                return true;
            case "CARROT_ON_A_STICK":
                return true;
            case "CHICKEN":
                return true;
            case "CHORUS_FRUIT":
                return true;
            case "COD":
                return true;
            case "COOKED_BEEF":
                return true;
            case "COOKED_CHICKEN":
                return true;
            case "COOKED_COD":
                return true;
            case "COOKED_MUTTON":
                return true;
            case "COOKED_PORKCHOP":
                return true;
            case "COOKED_RABBIT":
                return true;
            case "COOKED_SALMON":
                return true;
            case "COOKIE":
                return true;
            case "CAKE":
                return true;
            case "DRIED_KELP":
                return true;
            case "ENCHANTED_GOLDEN_APPLE":
                return true;
            case "GOLDEN_APPLE":
                return true;
            case "GOLDEN_CARROT":
                return true;
            case "GLOW_BERRIES":
                return true;
            case "HONEY_BOTTLE":
                return true;
            case "MELON_SLICE":
                return true;
            case "MUTTON":
                return true;
            case "MUSHROOM_STEW":
                return true;
            case "POISONOUS_POTATO":
                return true;
            case "POPPED_CHORUS_FRUIT":
                return true;
            case "PORKCHOP":
                return true;
            case "POTATO":
                return true;
            case "PUFFERFISH":
                return true;
            case "PUMPKIN_PIE":
                return true;
            case "RABBIT":
                return true;
            case "RABBIT_STEW":
                return true;
            case "ROTTEN_FLESH":
                return true;
            case "SALMON":
                return true;
            case "SUSPICIOUS_STEW":
                return true;
            case "SWEET_BERRIES":
                return true;
            case "SPIDER_EYE":
                return true;
            case "TROPICAL_FISH":
                return true;
            case "BEETROOTS":
                return true;
            case "MELON":
                return true;
            case "PUMPKIN":
                return true;
            case "PUMPKIN_STEM":
                return true;
        }
        return false;
    }
}
