package net.anvian.gofish.loot;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.anvian.gofish.registry.GoFishLoot;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public record WeatherCondition(Optional<Boolean> raining, Optional<Boolean> thundering, Optional<Boolean> snowing)
        implements LootItemCondition {

    public static final Codec<WeatherCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ExtraCodecs.strictOptionalField(Codec.BOOL, "raining").forGetter(WeatherCondition::raining),
                    ExtraCodecs.strictOptionalField(Codec.BOOL, "thundering").forGetter(WeatherCondition::thundering),
                    ExtraCodecs.strictOptionalField(Codec.BOOL, "snowing").forGetter(WeatherCondition::snowing))
            .apply(instance, WeatherCondition::new));

    @Override
    public LootItemConditionType getType() {
        return GoFishLoot.WEATHER.get();
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.THIS_ENTITY, LootContextParams.ORIGIN);
    }

    @Override
    public boolean test(LootContext lootContext) {
        @Nullable Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        @Nullable Vec3 pos = lootContext.getParamOrNull(LootContextParams.ORIGIN);

        if (entity != null && pos != null) {
            Level world = entity.level();

            if (raining.isPresent() && raining.get() && !world.isRaining()) {
                return false;
            }

            if (thundering.isPresent() && thundering.get() && !world.isThundering()) {
                return false;
            }

            if (snowing.isPresent() && snowing.get()) {
                // >= .15 = no snow
                if (world.getBiome(entity.blockPosition())
                        .value()
                        .warmEnoughToRain(new BlockPos(
                                (int) Math.floor(pos.x), (int) Math.floor(pos.y), (int) Math.floor(pos.z)))) {
                    return false;
                }

                return world.isRaining();
            }

            return true;
        }

        return false;
    }

    public static LootItemCondition.Builder builder(boolean raining, boolean thundering, boolean snowing) {
        return () -> new WeatherCondition(Optional.of(raining), Optional.of(thundering), Optional.of(snowing));
    }
}
