package net.anvian.gofish.loot.moon;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import net.anvian.gofish.registry.GoFishLoot;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public record FullMoonCondition() implements LootItemCondition {

    public static final FullMoonCondition INSTANCE = new FullMoonCondition();
    public static final MapCodec<FullMoonCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public @NotNull LootItemConditionType getType() {
        return GoFishLoot.FULL_MOON.get();
    }

    @Override
    public @NotNull Set<ContextKey<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.THIS_ENTITY);
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);

        if (entity != null) {
            return entity.level().isDarkOutside()
                    && entity.level()
                                    .environmentAttributes()
                                    .getValue(EnvironmentAttributes.MOON_PHASE, entity.position())
                            == MoonPhase.FULL_MOON;
        }

        return false;
    }

    public static LootItemCondition.Builder builder() {
        return () -> INSTANCE;
    }
}
