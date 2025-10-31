package org.snow2code.plugin.advancements;

import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import org.bukkit.Material;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;

//SNOW
import static org.snow2code.util.Advancements.namespace;

public class FishCraft extends BaseAdvancement {

    public static AdvancementKey KEY = new AdvancementKey(namespace, "fish_craft");

    public FishCraft(Advancement parent) {
        super(
                KEY.getKey(),
                new AdvancementDisplay(
                        Material.SALMON,
                        "§bFish Craft",
                        AdvancementFrameType.TASK,
                        true,
                        true,
                        1f,
                        1f,
                        "§7A curious creation from the water"
                ),
                parent,
                1
        );
    }

}
