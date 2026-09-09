package net.anvian.gofish.registry;

import net.anvian.anvianslib.util.RegistryUtil;
import net.anvian.gofish.GoFishConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class GoFishTags {
    private GoFishTags() {}

    public static final TagKey<Biome> ICY = RegistryUtil.tag(Registries.BIOME, GoFishConstants.MOD_ID, "icy_biomes");
    public static final TagKey<Biome> PLAINS =
            RegistryUtil.tag(Registries.BIOME, GoFishConstants.MOD_ID, "plains_biomes");
    public static final TagKey<Biome> SWAMP =
            RegistryUtil.tag(Registries.BIOME, GoFishConstants.MOD_ID, "swamp_biomes");
}
