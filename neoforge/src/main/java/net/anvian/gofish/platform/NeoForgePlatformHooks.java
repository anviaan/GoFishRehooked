package net.anvian.gofish.platform;

import net.anvian.gofish.GoFish;
import net.anvian.gofish.GoFishConstants;
import net.anvian.gofish.block.AstralCrateBlock;
import net.anvian.gofish.command.FishCommand;
import net.anvian.gofish.entity.block.AstralCrateBlockEntity;
import net.anvian.gofish.item.CrateItem;
import net.anvian.gofish.item.NeoForgeAstralCrateItem;
import net.anvian.gofish.registry.GoFishBlocks;
import net.anvian.gofish.registry.GoFishEntities;
import net.anvian.gofish.registry.GoFishItems;
import net.anvian.gofish.registry.GoFishLootHandler;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class NeoForgePlatformHooks implements IPlatformHooks {

    private final IEventBus modEventBus;
    private final List<Registration> registrations = new ArrayList<>();

    public NeoForgePlatformHooks(IEventBus modEventBus) {
        this.modEventBus = modEventBus;
    }

    @Override
    public <V, T extends V> PlatformRegistryObject<T> register(
            Registry<V> registry, Identifier id, Supplier<T> supplier) {
        PlatformRegistryObject<T> object = new PlatformRegistryObject<>(id);
        registrations.add(new Registration(registry.key(), id, supplier, object));
        return object;
    }

    @Override
    public void registerPlatformHooks() {
        registerAstralCrateEntity();

        modEventBus.addListener(this::registerObjects);
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
        NeoForge.EVENT_BUS.addListener(this::modifyLootTable);
        NeoForge.EVENT_BUS.addListener(this::registerFuel);

        NeoForge.EVENT_BUS.addListener(this::registerBrewingRecipes);
        modEventBus.addListener(NeoForgePlatformHooks::buildCreativeTab);
    }

    @SuppressWarnings({"ConstantConditions"})
    private static void registerAstralCrateEntity() {
        GoFishEntities.ASTRAL_CRATE = GoFishEntities.register(
                "astral_crate",
                () -> new BlockEntityType<>(AstralCrateBlockEntity::new, GoFishBlocks.ASTRAL_CRATE.get()));
    }

    private void registerObjects(RegisterEvent event) {
        registrations.stream()
                .filter(registration -> event.getRegistryKey().equals(registration.registryKey()))
                .forEach(registration -> registerObject(event, registration));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void registerObject(RegisterEvent event, Registration registration) {
        Object value = registration.supplier().get();
        setObject(registration.object(), value);
        event.register((ResourceKey) registration.registryKey(), registration.id(), () -> value);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void setObject(PlatformRegistryObject object, Object value) {
        object.set(value);
    }

    private void registerCommands(RegisterCommandsEvent event) {
        FishCommand.register(event.getDispatcher());
    }

    private void modifyLootTable(LootTableLoadEvent event) {
        if (BuiltInLootTables.FISHING_FISH.equals(event.getName())) {
            GoFishLootHandler.addFishEntries(event.getTable());
        }
    }

    private void registerFuel(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(GoFishItems.OAKFISH.get())) {
            event.setBurnTime(300);
        } else if (event.getItemStack().is(GoFishItems.CHARFISH.get())) {
            event.setBurnTime(1600);
        }
    }

    private void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(Potions.AWKWARD, GoFishItems.CLOUDY_CRAB.get(), Potions.SLOW_FALLING);
        builder.addMix(Potions.AWKWARD, GoFishItems.CHARFISH.get(), Potions.WEAKNESS);
        builder.addMix(Potions.AWKWARD, GoFishItems.RAINY_BASS.get(), Potions.WATER_BREATHING);
        builder.addMix(Potions.AWKWARD, GoFishItems.MAGMA_COD.get(), Potions.FIRE_RESISTANCE);
    }

    private static void buildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (GoFishConstants.ITEM_GROUP.equals(event.getTabKey())) {
            GoFish.addCreativeItems(item -> event.accept(new ItemStack(item)));
        }
    }

    @Override
    public Item createCrateItem(Block block, Item.Properties properties, Identifier lootTable) {
        return block instanceof AstralCrateBlock
                ? new NeoForgeAstralCrateItem(block, properties, lootTable)
                : new CrateItem(block, properties, lootTable);
    }

    @Override
    public boolean isFakePlayer(Entity entity) {
        return entity instanceof net.neoforged.neoforge.common.util.FakePlayer
                || entity.getClass().getName().contains("FakePlayer");
    }

    private record Registration(
            ResourceKey<? extends Registry<?>> registryKey,
            Identifier id,
            Supplier<?> supplier,
            PlatformRegistryObject<?> object) {}
}
