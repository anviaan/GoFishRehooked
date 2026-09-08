package net.anvian.gofish.platform;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface IPlatformHooks {

    void registerPlatformHooks();

    Item createCrateItem(Block block, Item.Properties properties, Identifier lootTable);

    boolean isFakePlayer(Entity entity);

    <V, T extends V> PlatformRegistryObject<T> register(Registry<V> registry, Identifier id, Supplier<T> supplier);
}
