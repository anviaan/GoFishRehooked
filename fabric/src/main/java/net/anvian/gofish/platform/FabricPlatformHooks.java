package net.anvian.gofish.platform;

import com.mojang.brigadier.CommandDispatcher;
import net.anvian.gofish.command.FishCommand;
import net.anvian.gofish.entity.block.AstralCrateBlockEntity;
import net.anvian.gofish.item.CrateItem;
import net.anvian.gofish.registry.GoFishEntities;
import net.anvian.gofish.registry.GoFishItems;
import net.anvian.gofish.registry.GoFishLootHandler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.function.Supplier;

public final class FabricPlatformHooks implements IPlatformHooks {

    @Override
    public void registerPlatformHooks() {
        registerPlatformHooksInternal();
    }

    private static void registerPlatformHooksInternal() {
        GoFishEntities.ASTRAL_CRATE = GoFishEntities.register(
                "astral_crate",
                () -> BlockEntityType.Builder.of(
                                AstralCrateBlockEntity::new, net.anvian.gofish.registry.GoFishBlocks.ASTRAL_CRATE.get())
                        .build(null));

        CommandRegistrationCallback.EVENT.register(FabricPlatformHooks::registerCommands);
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (BuiltInLootTables.FISHING_FISH.equals(id) && source.isBuiltin()) {
                tableBuilder.modifyPools(GoFishLootHandler::addFishEntries);
            }
        });

        FuelRegistry.INSTANCE.add(GoFishItems.OAKFISH.get(), 300);
        FuelRegistry.INSTANCE.add(GoFishItems.CHARFISH.get(), 1600);

        PotionBrewing.addMix(Potions.AWKWARD, GoFishItems.CLOUDY_CRAB.get(), Potions.SLOW_FALLING);
        PotionBrewing.addMix(Potions.AWKWARD, GoFishItems.CHARFISH.get(), Potions.WEAKNESS);
        PotionBrewing.addMix(Potions.AWKWARD, GoFishItems.RAINY_BASS.get(), Potions.WATER_BREATHING);
        PotionBrewing.addMix(Potions.AWKWARD, GoFishItems.MAGMA_COD.get(), Potions.FIRE_RESISTANCE);
    }

    private static void registerCommands(
            CommandDispatcher<net.minecraft.commands.CommandSourceStack> dispatcher,
            net.minecraft.commands.CommandBuildContext registryAccess,
            net.minecraft.commands.Commands.CommandSelection environment) {
        FishCommand.register(dispatcher);
    }

    @Override
    public Item createCrateItem(Block block, Item.Properties properties, ResourceLocation lootTable) {
        return new CrateItem(block, properties, lootTable);
    }

    @Override
    public boolean isFakePlayer(Entity entity) {
        return entity instanceof FakePlayer || entity.getClass().getName().contains("FakePlayer");
    }

    @Override
    public <V, T extends V> PlatformRegistryObject<T> register(
            Registry<V> registry, ResourceLocation id, Supplier<T> supplier) {
        PlatformRegistryObject<T> object = new PlatformRegistryObject<>(id);
        object.set(Registry.register(registry, id, supplier.get()));
        return object;
    }
}
