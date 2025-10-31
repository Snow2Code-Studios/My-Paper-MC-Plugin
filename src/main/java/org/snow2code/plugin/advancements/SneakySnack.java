package org.snow2code.plugin.advancements;

import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import org.bukkit.Material;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;

//SNOW
import static org.snow2code.util.Advancements.namespace;

public class SneakySnack extends BaseAdvancement {

    public static AdvancementKey KEY = new AdvancementKey(namespace, "sneaky_snack");

    public SneakySnack(Advancement parent) {
        super(
                KEY.getKey(),
                new AdvancementDisplay(
                        Material.SWEET_BERRIES,
                        "§cSneaky Snack",
                        AdvancementFrameType.TASK,
                        true,
                        true,
                        1f,
                        1f,
                        "§7A berry for your furry firend"
                ),
                parent,
                1
        );
    }

}
