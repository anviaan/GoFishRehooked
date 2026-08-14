package net.anvian.gofish.registry;

import com.mojang.serialization.Codec;
import net.anvian.gofish.GoFish;
import net.anvian.gofish.loot.WeatherCondition;
import net.anvian.gofish.loot.biome.MatchBiomeLootCondition;
import net.anvian.gofish.loot.moon.FullMoonCondition;
import net.anvian.gofish.platform.PlatformRegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class GoFishLoot {
    private GoFishLoot() {}

    public static final PlatformRegistryObject<LootItemConditionType> MATCH_BIOME =
            register("match_biome", MatchBiomeLootCondition.CODEC);
    public static final PlatformRegistryObject<LootItemConditionType> FULL_MOON =
            register("full_moon", FullMoonCondition.CODEC);
    public static final PlatformRegistryObject<LootItemConditionType> WEATHER =
            register("weather", WeatherCondition.CODEC);

    private static PlatformRegistryObject<LootItemConditionType> register(
            String id, Codec<? extends LootItemCondition> codec) {
        return GoFish.register(
                BuiltInRegistries.LOOT_CONDITION_TYPE, GoFish.id(id), () -> new LootItemConditionType(codec));
    }

    public static void init() {
        // NO-OP
    }
}
