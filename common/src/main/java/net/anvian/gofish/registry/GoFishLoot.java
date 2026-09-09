package net.anvian.gofish.registry;

import com.mojang.serialization.MapCodec;
import net.anvian.gofish.GoFish;
import net.anvian.gofish.loot.WeatherCondition;
import net.anvian.gofish.loot.biome.MatchBiomeLootCondition;
import net.anvian.gofish.loot.moon.FullMoonCondition;
import net.anvian.gofish.platform.PlatformRegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class GoFishLoot {
    private GoFishLoot() {}

    public static final PlatformRegistryObject<MapCodec<? extends LootItemCondition>> MATCH_BIOME =
            register("match_biome", MatchBiomeLootCondition.CODEC);
    public static final PlatformRegistryObject<MapCodec<? extends LootItemCondition>> FULL_MOON =
            register("full_moon", FullMoonCondition.CODEC);
    public static final PlatformRegistryObject<MapCodec<? extends LootItemCondition>> WEATHER =
            register("weather", WeatherCondition.CODEC);

    private static PlatformRegistryObject<MapCodec<? extends LootItemCondition>> register(
            String id, MapCodec<? extends LootItemCondition> codec) {
        return GoFish.register(BuiltInRegistries.LOOT_CONDITION_TYPE, GoFish.id(id), () -> codec);
    }

    public static void init() {
        // NO-OP
    }
}
