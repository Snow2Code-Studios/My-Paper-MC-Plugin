package org.snow2code.plugin.advancements;

import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import org.bukkit.Material;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;

//SNOW
import static org.snow2code.util.Advancements.namespace;

public class LeashSupplier extends BaseAdvancement {

    public static AdvancementKey KEY = new AdvancementKey(namespace, "leash_supplier");

    public LeashSupplier(Advancement parent) {
        super(
                KEY.getKey(),
                new AdvancementDisplay(
                        Material.LEAD,
                        "§aLeash Supplier",
                        AdvancementFrameType.TASK,
                        true,
                        true,
                        1f,
                        1f,
                        "§7Stock up on ropes and ties"
                ),
                parent,
                1
        );
    }

}
