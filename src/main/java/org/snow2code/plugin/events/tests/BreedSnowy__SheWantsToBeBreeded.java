package org.snow2code.plugin.events.tests;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.ItemStack;
import org.snow2code.util.SemiLogger;

public class BreedSnowy__SheWantsToBeBreeded implements Listener {
    
    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {
        LivingEntity mother = event.getMother();
        LivingEntity father = event.getFather();
        Player player = (Player) event.getBreeder();
        ItemStack bredWith = event.getBredWith();
        int xp = event.getExperience();

        if ( mother.getType() == EntityType.FOX && father.getType() == EntityType.FOX ) {
            double chance = Math.random();
            int litterSize = 0;

            
            // if ( chance < 0.1 ) { // 10%
            //     litterSize = 1;
            if ( chance < 0.2 ) { // 20%
                litterSize = 2;
            } else if ( chance < 0.3 ) { // 30%
                litterSize = 3;
            } else if ( chance < 0.25 ) { // 25%
                litterSize = 4;
            } else if ( chance < 0.1 ) { // 10%
                litterSize = 5;
            } else if ( chance < 0.05 ) { // 5%
                litterSize = 6;
            }

            // Old code cuz I adjusted my code to be acturate to fox litter size
            /*
            if (chance < 0.2) { // 20%
                litterSize = 2;
            } else if ( chance < 0.05 ) { // 5%
                litterSize = 3;
            }
            */
            

            // Is litterSize more than 0?
            if ( litterSize > 0 ) {
                // Spawn em kits
                for ( int i = 1; i < litterSize; i++ ) {
                    boolean randKit = false;
                    Fox motherFox = (Fox) mother;
                    Fox fatherFox = (Fox) father;

                    // If mother is a snow fox and father is a red fox, do the random kit chance
                    if ( motherFox.getFoxType() == Fox.Type.SNOW && fatherFox.getFoxType() == Fox.Type.RED ) {
                        randKit = true;
                    }

                    // If mother is a red fox and father is snow fox, do the random kit chance
                    if ( motherFox.getFoxType() == Fox.Type.RED && fatherFox.getFoxType() == Fox.Type.SNOW ) {
                        randKit = true;
                    }

                    Fox kit = (Fox) event.getEntity().getWorld().spawnEntity( event.getEntity().getLocation(), EntityType.FOX );
                    kit.setAge(-24000);

                    if ( randKit ) {
                        if ( Math.random() < 0.5 ) { // 50%
                            kit.setFoxType(Fox.Type.RED);
                        } else {
                            kit.setFoxType(Fox.Type.SNOW);
                        }
                    } else {
                        if ( motherFox.getFoxType() == Fox.Type.SNOW && fatherFox.getFoxType() == Fox.Type.SNOW ) {
                            kit.setFoxType(Fox.Type.SNOW);
                        } else if ( motherFox.getFoxType() == Fox.Type.SNOW && fatherFox.getFoxType() == Fox.Type.SNOW ) {
                            kit.setFoxType(Fox.Type.RED);
                        }
                    }
                }

                event.setExperience(xp * litterSize);

                SemiLogger.Debug(
                    String.format(
                        "\n\nentity=%s\nmother=%s\nfather=%s\nbreeder=%s\nbredWIth=%s\nxp=%s\n\n",
                        event.getEntity(),
                        mother,
                        father,
                        player,
                        bredWith,
                        xp
                    )
                );
            }
        }
    }

}
