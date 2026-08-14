package net.anvian.gofish.registry;

import net.anvian.gofish.GoFish;
import net.anvian.gofish.entity.block.AstralCrateBlockEntity;
import net.anvian.gofish.platform.PlatformRegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class GoFishEntities {
    private GoFishEntities() {}

    public static PlatformRegistryObject<BlockEntityType<AstralCrateBlockEntity>> ASTRAL_CRATE;

    public static <T extends BlockEntity> PlatformRegistryObject<BlockEntityType<T>> register(
            String name, Supplier<BlockEntityType<T>> entity) {
        return GoFish.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, GoFish.id(name), entity);
    }

    public static void init() {
        // NO-OP
    }
}
