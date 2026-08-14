package net.anvian.gofish.platform;

import com.mojang.datafixers.types.Type;
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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;

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
            Registry<V> registry, ResourceLocation id, Supplier<T> supplier) {
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

        addBrewingRecipe(GoFishItems.CLOUDY_CRAB, Potions.SLOW_FALLING);
        addBrewingRecipe(GoFishItems.CHARFISH, Potions.WEAKNESS);
        addBrewingRecipe(GoFishItems.RAINY_BASS, Potions.WATER_BREATHING);
        addBrewingRecipe(GoFishItems.MAGMA_COD, Potions.FIRE_RESISTANCE);
    }

    @SuppressWarnings({"ConstantConditions", "DataFlowIssue"})
    private static void registerAstralCrateEntity() {
        GoFishEntities.ASTRAL_CRATE = GoFishEntities.register(
                "astral_crate",
                () -> BlockEntityType.Builder.of(AstralCrateBlockEntity::new, GoFishBlocks.ASTRAL_CRATE.get())
                        .build(nullDataFixerType()));
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

    private static void addBrewingRecipe(Supplier<Item> ingredient, Potion outputPotion) {
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(@NotNull ItemStack input) {
                return input.is(Items.POTION) && PotionUtils.getPotion(input) == Potions.AWKWARD;
            }

            @Override
            public boolean isIngredient(@NotNull ItemStack input) {
                return input.is(ingredient.get());
            }

            @Override
            public @NotNull ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredientStack) {
                return isInput(input) && isIngredient(ingredientStack)
                        ? PotionUtils.setPotion(new ItemStack(Items.POTION), outputPotion)
                        : ItemStack.EMPTY;
            }
        });
    }

    private static Type<?> nullDataFixerType() {
        return null;
    }

    @Override
    public Item createCrateItem(Block block, Item.Properties properties, ResourceLocation lootTable) {
        return block instanceof AstralCrateBlock
                ? new NeoForgeAstralCrateItem(block, properties, lootTable)
                : new CrateItem(block, properties, lootTable);
    }

    private record Registration(
            ResourceKey<? extends Registry<?>> registryKey,
            ResourceLocation id,
            Supplier<?> supplier,
            PlatformRegistryObject<?> object) {}
}
