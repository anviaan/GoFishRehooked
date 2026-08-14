package net.anvian.gofish.loot.moon;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.anvian.gofish.registry.GoFishLoot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public record FullMoonCondition() implements LootItemCondition {

    public static final FullMoonCondition INSTANCE = new FullMoonCondition();
    public static final Codec<FullMoonCondition> CODEC = Codec.unit(INSTANCE);

    @Override
    public @NotNull LootItemConditionType getType() {
        return GoFishLoot.FULL_MOON.get();
    }

    @Override
    public @NotNull Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);

        if (entity != null) {
            return entity.level().isNight()
                    && entity.level().dimensionType().moonPhase(entity.level().dayTime()) == 0;
        }

        return false;
    }

    public static LootItemCondition.Builder builder() {
        return () -> INSTANCE;
    }
}
