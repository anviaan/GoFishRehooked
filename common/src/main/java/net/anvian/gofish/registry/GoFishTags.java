package net.anvian.gofish.registry;

import net.anvian.gofish.GoFish;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class GoFishTags {
    private GoFishTags() {}

    public static final TagKey<Biome> ICY =
            TagKey.create(Registries.BIOME, new ResourceLocation(GoFish.MOD_ID, "icy_biomes"));
    public static final TagKey<Biome> PLAINS =
            TagKey.create(Registries.BIOME, new ResourceLocation(GoFish.MOD_ID, "plains_biomes"));
    public static final TagKey<Biome> SWAMP =
            TagKey.create(Registries.BIOME, new ResourceLocation(GoFish.MOD_ID, "swamp_biomes"));
}
