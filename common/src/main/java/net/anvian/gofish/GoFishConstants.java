package net.anvian.gofish;

import net.anvian.anvianslib.util.RegistryUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.List;

public final class GoFishConstants {

    private GoFishConstants() {}

    public static final String MOD_ID = "gofish";
    public static final String MOD_NAME = "Go Fish Rehooked";
    public static final String MOD_VERSION = "2.0.0";
    public static final ResourceKey<CreativeModeTab> ITEM_GROUP =
            RegistryUtil.key(Registries.CREATIVE_MODE_TAB, MOD_ID, "group");
    static final List<String> CREATIVE_ITEM_ORDER = List.of(
            // Fishing rods
            "blaze_rod",
            "skeletal_rod",
            "diamond_reinforced_rod",
            "ender_rod",
            "frosted_rod",
            "slime_rod",
            "soul_rod",
            "matrix_rod",
            "celestial_rod",
            // Accessories
            "simple_lure",
            "golden_fish",
            "soul_lure",
            // Crates
            "wooden_crate",
            "iron_crate",
            "golden_crate",
            "diamond_crate",
            "supply_crate",
            "frosted_crate",
            "slimey_crate",
            "astral_crate",
            "fiery_crate",
            "soul_crate",
            "gilded_blackstone_crate",
            "end_crate",
            // Overworld
            "icicle_fish",
            "snowball_fish",
            "slimefish",
            "lilyfish",
            "seaweed_eel",
            "seaweed",
            "baked_seaweed",
            "terrafish",
            "carrot_carp",
            "baked_carrot_carp",
            "oakfish",
            "charfish",
            // Nether
            "smokey_salmon",
            "magma_cod",
            "bonefish",
            "obsidian_halibut",
            "basalt_bass",
            "spikerfish",
            "gilded_blackstone_carp",
            "blackstone_trout",
            "grilled_blackstone_trout",
            "grilled_blackstone_deluxe",
            "soul_salmon",
            // The End
            "endfish",
            "baked_endfish",
            "endfish_and_chorus",
            "matrix_fish",
            "ender_eel",
            "chorus_cod",
            "dragonfish",
            "omega_floater",
            "portal_puffer",
            // Full moon
            "lunarfish",
            "galaxy_starfish",
            "starry_salmon",
            "nebula_swordfish",
            "aquatic_astral_stew",
            // Weather and altitude
            "rainy_bass",
            "steamed_bass",
            "thundering_bass",
            "blizzard_bass",
            "cloudy_crab",
            "smoked_cloudy_crab");
}
