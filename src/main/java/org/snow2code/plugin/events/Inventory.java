package org.snow2code.plugin.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.meta.ItemMeta;

import org.snow2code.util.*;
// import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class Inventory implements Listener {

    @EventHandler
    public void inventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();

        // Is player not null?
        if ( player != null ) {
            player.getInventory().forEach(item -> {
                // Is item not null?
                if ( item != null ) {
                    // Has item meta?
                    if ( item.hasItemMeta() ) {
                        ItemMeta itemMeta = item.getItemMeta();

                        // Yon Fish
                        if ( itemMeta.getDisplayName().equals("§5Yon’s Fish") || itemMeta.getDisplayName().equals("Yon's Fish") ) {
                            if ( item.getAmount() >= 10 ) {
                                Advancements.fishConnoisseur.grant(player);
                            }
                        }
                    }

                    // Leash Supplier
                    if ( item.getType() == Material.LEAD ) {
                        if ( item.getAmount() >= 10 ) {
                            Advancements.leashSupplier.grant(player);
                        }
                    }
                }
            });
        }
    }
}
