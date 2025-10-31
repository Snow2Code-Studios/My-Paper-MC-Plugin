package org.snow2code.plugin.advancements;

import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import org.bukkit.Material;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;

//SNOW
import static org.snow2code.util.Advancements.namespace;

public class NightProwler extends BaseAdvancement {

    public static AdvancementKey KEY = new AdvancementKey(namespace, "night_prowler");

    public NightProwler(Advancement parent) {
        super(
                KEY.getKey(),
                new AdvancementDisplay(
                        Material.BLACK_BED,
                        "§8Night Prowler",
                        AdvancementFrameType.TASK,
                        true,
                        true,
                        1f,
                        1f,
                        "§7The fox walks beside you in the dark"
                ),
                parent,
                1
        );
    }

}
