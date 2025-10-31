package org.snow2code.util;

import com.fren_gor.ultimateAdvancementAPI.*;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import org.bukkit.Material;


//
import org.snow2code.plugin.advancements.*;
import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class Advancements {

    // snow2code_advancements
    public static String namespace = "snow2code_plugin";

    private static UltimateAdvancementAPI api = UltimateAdvancementAPI.getInstance(plugin);
    public static AdvancementTab advancementTab = api.createAdvancementTab(namespace);
    public static RootAdvancement rootAdvancement = new RootAdvancement(
            advancementTab,
            "root",
            new AdvancementDisplay(
                    Material.SNOW_BLOCK,
                    "Snowy Adventures", // §a
                    AdvancementFrameType.TASK,
                    true,
                    true,
                    0f,
                    0f,
                    "Snowy x Frost"
                    // "§7Where pets, foxes and curious things await."
            ),
            "textures/block/red_terracotta.png",
            1
    );


    public static PetMaster petMaster= new PetMaster(rootAdvancement);
    public static FoxyBusiness foxyBusiness = new FoxyBusiness(rootAdvancement);
    public static FoxFamily foxfamily = new FoxFamily(rootAdvancement);
    public static SneakySnack sneakySnack = new SneakySnack(rootAdvancement);
    public static FishCraft fishCraft = new FishCraft(rootAdvancement);
    public static LeashSupplier leashSupplier = new LeashSupplier(rootAdvancement);
    public static NightProwler nightProwler = new NightProwler(rootAdvancement);
    public static FishConnoisseur fishConnoisseur = new FishConnoisseur(rootAdvancement);
    public static FishingBuddy fishingBuddy = new FishingBuddy(rootAdvancement);
    // public static OhNo ohNo = new OhNo(rootAdvancement);
    public static TangledUp tangledUp = new TangledUp(rootAdvancement);

    public static void registerAdvancements() {
        advancementTab.registerAdvancements(
                rootAdvancement,

                petMaster,
                foxyBusiness,
                foxfamily,
                sneakySnack,
                fishCraft,
                leashSupplier,
                nightProwler,
                fishConnoisseur,
                fishingBuddy,
                // ohNo
                tangledUp
        );
    }

}
