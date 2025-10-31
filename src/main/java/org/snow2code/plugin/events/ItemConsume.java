package org.snow2code.plugin.events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class ItemConsume implements Listener {
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();

        if ( item != null ) {
            Player player = event.getPlayer();
            ItemMeta meta = item.getItemMeta();
            String rarity = meta.getPersistentDataContainer().get( new NamespacedKey(plugin, "rarity"), PersistentDataType.STRING );

            if ( rarity.contains("drink") ) {
                // event.setCancelled(false);
                String key = meta.getPersistentDataContainer().get( new NamespacedKey(plugin, "custom_key"), PersistentDataType.STRING );
                int duration = 0;

                switch (key) {
                    /*
                        Frosty Brew	" I ", " B ", " S "	Glass Bottle (B), Snow Block (S), Sugar (I)
                        Snow Nectar	" I ", " B ", " D "	Glass Bottle, Ice (I), Blue Dye (D)
                        Chill Elixir	" S ", " B ", " C "	Glass Bottle, Snow Block (S), Sugar (C)
                        Snowdrift Ale	" S ", " S ", " B "	Glass Bottle, 2x Snow Blocks (S)
                        Frostfire Lager	" I ", " B ", " C "	Glass Bottle, Ice (I), Sugar (C)
                        Glacial Whiskey	" I ", " B ", " C "	Glass Bottle, Ice Block (I), Sugar (C)
                        Snowcap Cider	" S ", " B ", " C "	Glass Bottle, Snowball (S), Sugar (C)
                        Frosty Night	" D ", " B ", " S "	Glass Bottle, Snow Block (S), Blue Dye (D)
                        Snowbound Spirit	" B ", " I ", " D "	Glass Bottle, Ice (I), Blue Dye (D)
                        Iceberg Shiver	" I ", " B ", " C "	Glass Bottle, Ice Block (I), Sugar (C)
                        Snowfall Shot	" S ", " B ", " C "	Glass Bottle, Snowball (S), Sugar (C)
                        Frozen Delight	" L ", " S ", " B "	Glass Bottle, Lilac Dye (L), Snow Block (S)
                    */
                    
                    case "frosty_brew":
                        // 180, 220, 255
                        // "Looks dangerous… but feels refreshing."

                        // Glass Bottle + Snow Block + Sugar " I ", " B ", " S "
                        duration = 20*25;
                        break;
                    case "snow_nectar":
                        // 240, 250, 255
                        // "A sweet chill that dances on your tongue."

                        // Glass Bottle + Ice + Blue Dye " I ", " B ", " D "
                        duration = 20*20;
                        break;
                    case "chill_elixir":
                        // 150, 200, 255
                        // "One sip and the world slows down."

                        // Glass Bottle + Snow Block + Sugar " S ", " B ", " S "
                        duration = 20*30;
                        break;

                    case "snowdrift_ale":
                        // 210, 240, 255
                        // "Smooth, but leaves a chill in your veins."

                        // Glass Bottle + Snow Block + Sugar " S ", " S ", " B "
                        duration = 20*25;
                        break;
                    case "frostfire_lager":
                        // 50, 100, 255
                        // "A bold sip that tingles like frost and fire combined."

                        // Glass Bottle + Ice + Sugar " I ", " S ", " B "
                        duration = 20*20;
                        break;
                    case "glacial_whiskey":
                        // 0, 150, 180
                        // "Old and icy… warms the soul but blurs the mind."

                        // Glass Bottle + Ice Block + Sugar " I ", " B ", " S "
                        duration = 20*30;
                        break;
                    case "snowcap_cider":
                        duration = 20*20;
                        break;
                    case "frosty_night":
                        // 20, 30, 100
                        // "One sip, and the world slows under the moonlight."

                        // Glass Bottle + Snow Block + Sugar " S ", " B ", " S "
                        duration = 20*25;
                        break;
                    case "snowbound_spirit":
                        duration = 20*30;
                        break;
                    case "iceberg_shiver":
                        duration = 20*20;
                        break;
                    case "snowfall_shot":
                        duration = 20*10;
                        break;
                    case "frozen_delight":
                        duration = 20*25;
                        break;
                    default:
                        duration = 20*15;
                        break;
                }

                player.addPotionEffect( new PotionEffect(PotionEffectType.NAUSEA, duration, 1, false, false) );
            } else if ( rarity.contains("epic") ) {
                String key = meta.getPersistentDataContainer().get( new NamespacedKey(plugin, "custom_key"), PersistentDataType.STRING );
                int duration = 0;

                switch (key) {
                    case "yonfish_nausea":
                        // YonFish + Alchol (custom, not actual)
                        player.addPotionEffect( new PotionEffect(PotionEffectType.NAUSEA, duration, 1, false, false) );
                        break;
                    default:
                        player.addPotionEffect( new PotionEffect(PotionEffectType.HEALTH_BOOST, 1000, 1, false, false) );
                        player.addPotionEffect( new PotionEffect(PotionEffectType.INSTANT_HEALTH, 1, 5, false, false) );
                        player.addPotionEffect( new PotionEffect(PotionEffectType.LUCK, 1000, 1, false, false) );
                        player.addPotionEffect( new PotionEffect(PotionEffectType.HASTE, 1000, 2, false, false) );
                        break;
                }
            }
        }
    }
}
