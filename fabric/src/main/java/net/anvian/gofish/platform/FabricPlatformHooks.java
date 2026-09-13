package net.anvian.gofish.platform;

import com.mojang.brigadier.CommandDispatcher;
import net.anvian.gofish.GoFish;
import net.anvian.gofish.GoFishConstants;
import net.anvian.gofish.command.FishCommand;
import net.anvian.gofish.entity.block.AstralCrateBlockEntity;
import net.anvian.gofish.item.CrateItem;
import net.anvian.gofish.registry.GoFishEntities;
import net.anvian.gofish.registry.GoFishItems;
import net.anvian.gofish.registry.GoFishLootHandler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.function.Supplier;

public final class FabricPlatformHooks implements IPlatformHooks {

    @Override
    public void registerPlatformHooks() {
        GoFishEntities.ASTRAL_CRATE = GoFishEntities.register(
                "astral_crate",
                () -> FabricBlockEntityTypeBuilder.create(
                                AstralCrateBlockEntity::new, net.anvian.gofish.registry.GoFishBlocks.ASTRAL_CRATE.get())
                        .build());

        CommandRegistrationCallback.EVENT.register(FabricPlatformHooks::registerCommands);
        CreativeModeTabEvents.modifyOutputEvent(GoFishConstants.ITEM_GROUP)
                .register(output -> {
                    GoFish.addCreativeItems(output::accept);
                    GoFish.addDeepfryBook(output::accept, output.getContext().holders());
                });
        LootTableEvents.MODIFY.register((id, tableBuilder, source, _) -> {
            if (BuiltInLootTables.FISHING_FISH.equals(id) && source.isBuiltin()) {
                tableBuilder.modifyPools(GoFishLootHandler::addFishEntries);
            }
        });

        FuelValueEvents.BUILD.register((builder, _) -> {
            builder.add(GoFishItems.OAKFISH.get(), 300);
            builder.add(GoFishItems.CHARFISH.get(), 1600);
        });
    }

    private static void registerCommands(
            CommandDispatcher<net.minecraft.commands.CommandSourceStack> dispatcher,
            net.minecraft.commands.CommandBuildContext registryAccess,
            net.minecraft.commands.Commands.CommandSelection environment) {
        FishCommand.register(dispatcher);
    }

    @Override
    public Item createCrateItem(Block block, Item.Properties properties, Identifier lootTable) {
        return new CrateItem(block, properties, lootTable);
    }

    @Override
    public boolean isFakePlayer(Entity entity) {
        return entity instanceof FakePlayer || entity.getClass().getName().contains("FakePlayer");
    }

    @Override
    public <V, T extends V> PlatformRegistryObject<T> register(
            Registry<V> registry, Identifier id, Supplier<T> supplier) {
        PlatformRegistryObject<T> object = new PlatformRegistryObject<>(id);
        object.set(Registry.register(registry, id, supplier.get()));
        return object;
    }
}
