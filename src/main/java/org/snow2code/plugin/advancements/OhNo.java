package org.snow2code.plugin.advancements;

import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import org.bukkit.Material;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;

//SNOW
import static org.snow2code.util.Advancements.namespace;

public class OhNo extends BaseAdvancement {

    public static AdvancementKey KEY = new AdvancementKey(namespace, "oh_no");

    public OhNo(Advancement parent) {
        super(
                KEY.getKey(),
                new AdvancementDisplay(
                        Material.CREEPER_HEAD,
                        "§4Oh no...",
                        AdvancementFrameType.TASK,
                        true,
                        true,
                        1f,
                        1f,
                        "§7That thing shouldn't of been leashed..."
                        // This isn't how leashes are meant to be used...
                ),
                parent,
                1
        );
    }

}
