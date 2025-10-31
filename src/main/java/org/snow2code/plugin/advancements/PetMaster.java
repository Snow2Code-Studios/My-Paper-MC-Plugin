package org.snow2code.plugin.advancements;

import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import org.bukkit.Material;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;

//SNOW
import static org.snow2code.util.Advancements.namespace;

public class PetMaster extends BaseAdvancement {

    public static AdvancementKey KEY = new AdvancementKey(namespace, "pet_master");

    public PetMaster(Advancement parent) {
        super(
                KEY.getKey(),
                new AdvancementDisplay(
                        Material.LEAD,
                        "§dPet Master",
                        AdvancementFrameType.TASK,
                        true,
                        true,
                        1f,
                        1f,
                        "§7The bond begins between you and your loyal companion"
                ),
                parent,
                1
        );
    }

}
