package org.snow2code.plugin.advancements;

import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import org.bukkit.Material;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;

//SNOW
import static org.snow2code.util.Advancements.namespace;

public class FishConnoisseur extends BaseAdvancement {

    public static AdvancementKey KEY = new AdvancementKey(namespace, "fish_connoisseur");

    public FishConnoisseur(Advancement parent) {
        super(
                KEY.getKey(),
                new AdvancementDisplay(
                        Material.SALMON_BUCKET,
                        "§5Fish Connoisseur",
                        AdvancementFrameType.TASK,
                        true,
                        true,
                        1f,
                        1f,
                        "§8Because one wasn't enough"
                        // Because one is never enough
                ),
                parent,
                1
        );
    }

}
