package org.snow2code.plugin.advancements;

import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import org.bukkit.Material;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;

//SNOW
import static org.snow2code.util.Advancements.namespace;

public class FoxFamily extends BaseAdvancement {

    public static AdvancementKey KEY = new AdvancementKey(namespace, "fox_family");

    public FoxFamily(Advancement parent) {
        super(
                KEY.getKey(),
                new AdvancementDisplay(
                        Material.ORANGE_DYE,
                        "§6Fox Family",
                        AdvancementFrameType.TASK,
                        true,
                        true,
                        1f,
                        1f,
                        "§7 More tails, more fun"
                ),
                parent,
                1
        );
    }

}
