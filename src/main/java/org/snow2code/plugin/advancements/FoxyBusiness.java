package org.snow2code.plugin.advancements;

import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import org.bukkit.Material;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;

//SNOW
import static org.snow2code.util.Advancements.namespace;

public class FoxyBusiness extends BaseAdvancement {

    public static AdvancementKey KEY = new AdvancementKey(namespace, "foxy_business");

    // I should consider changing this from FoxyBusiness to FoxBussiness
    public FoxyBusiness(Advancement parent) {
        super(
                KEY.getKey(),
                new AdvancementDisplay(
                        Material.FOX_SPAWN_EGG,
                        "§6Foxy Business",
                        AdvancementFrameType.TASK,
                        true,
                        true,
                        1f,
                        1f,
                        "§7Your first fox jonis the fun"
                ),
                parent,
                1
        );
    }

}
