package net.anvian.gofish.platform;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface IPlatformHooks {

    void registerPlatformHooks();

    Item createCrateItem(Block block, Item.Properties properties, ResourceLocation lootTable);

    boolean isFakePlayer(Entity entity);

    <V, T extends V> PlatformRegistryObject<T> register(
            Registry<V> registry, ResourceLocation id, Supplier<T> supplier);
}
