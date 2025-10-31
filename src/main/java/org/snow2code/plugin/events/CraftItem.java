package org.snow2code.plugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.snow2code.util.*;

public class CraftItem implements Listener {
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if ( event.getWhoClicked() instanceof Player ) {
            ItemStack result = event.getCurrentItem();
            
            if ( result != null ) {
                ItemMeta itemMeta = result.getItemMeta();
                switch ( itemMeta.getDisplayName() ) {
                    case "§5Yon’ Fish", "Yon's Fish":
                        SemiLogger.Debug("Crafted Yon Fish");
                        break;
                    default:
                        SemiLogger.Debug("Item type: " + result.getType());
                        break;
                }
            }
        }
    }
}
