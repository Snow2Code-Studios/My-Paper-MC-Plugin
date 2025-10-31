package org.snow2code.plugin.advancements;

import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import org.bukkit.Material;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;

//SNOW
import static org.snow2code.util.Advancements.namespace;

public class FishingBuddy extends BaseAdvancement {

    public static AdvancementKey KEY = new AdvancementKey(namespace, "fishing_buddy");

    public FishingBuddy(Advancement parent) {
        super(
                KEY.getKey(),
                new AdvancementDisplay(
                        Material.OAK_SAPLING, // TODO: CHANGE MAT
                        "§bFishing Buddy",
                        AdvancementFrameType.TASK,
                        true,
                        true,
                        1f,
                        1f,
                        "§7Fishing is better with a friend.. or pet"
                ),
                parent,
                1
        );
    }

}
