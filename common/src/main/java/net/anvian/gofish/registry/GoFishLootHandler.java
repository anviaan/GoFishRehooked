package net.anvian.gofish.registry;

import net.anvian.gofish.loot.WeatherCondition;
import net.anvian.gofish.loot.biome.MatchBiomeLootCondition;
import net.anvian.gofish.loot.moon.FullMoonCondition;
import net.anvian.gofish.mixin.LootPoolAccessor;
import net.anvian.gofish.mixin.LootTableAccessor;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class GoFishLootHandler {
    private GoFishLootHandler() {}

    public static void addFishEntries(LootPool.Builder pool) {
        addFishEntries(pool::add);
    }

    public static void addFishEntries(LootTable table) {
        List<LootPool> pools = ((LootTableAccessor) table).getPools();
        if (!pools.isEmpty()) {
            LootPoolAccessor pool = (LootPoolAccessor) pools.get(0);
            List<LootPoolEntryContainer> entries = new ArrayList<>(pool.getEntries());
            entries.addAll(createFishEntries());
            pool.setEntries(entries);
        }
    }

    public static List<LootPoolEntryContainer> createFishEntries() {
        List<LootPoolEntryContainer> entries = new ArrayList<>();
        addFishEntries(builder -> entries.add(builder.build()));
        return entries;
    }

    private static void addFishEntries(Consumer<LootPoolEntryContainer.Builder<?>> add) {
        add.accept(entry(GoFishItems.ICICLE_FISH.get(), 10, MatchBiomeLootCondition.builder(GoFishTags.ICY)));
        add.accept(entry(GoFishItems.SNOWBALL_FISH.get(), 10, MatchBiomeLootCondition.builder(GoFishTags.ICY)));

        add.accept(entry(GoFishItems.SLIMEFISH.get(), 10, MatchBiomeLootCondition.builder(GoFishTags.SWAMP)));
        add.accept(entry(GoFishItems.LILYFISH.get(), 10, MatchBiomeLootCondition.builder(GoFishTags.SWAMP)));

        add.accept(entry(GoFishItems.SEAWEED_EEL.get(), 10, MatchBiomeLootCondition.builder(BiomeTags.IS_OCEAN)));
        add.accept(entry(GoFishItems.TERRAFISH.get(), 10, MatchBiomeLootCondition.builder(BiomeTags.IS_BADLANDS)));

        add.accept(entry(GoFishItems.CARROT_CARP.get(), 10, MatchBiomeLootCondition.builder(GoFishTags.PLAINS)));
        add.accept(entry(GoFishItems.OAKFISH.get(), 10, MatchBiomeLootCondition.builder(GoFishTags.PLAINS)));
        add.accept(entry(GoFishItems.CARROT_CARP.get(), 10, MatchBiomeLootCondition.builder(BiomeTags.IS_FOREST)));
        add.accept(entry(GoFishItems.OAKFISH.get(), 10, MatchBiomeLootCondition.builder(BiomeTags.IS_FOREST)));

        add.accept(entry(GoFishItems.LUNARFISH.get(), 50, FullMoonCondition.builder()));
        add.accept(entry(GoFishItems.GALAXY_STARFISH.get(), 25, FullMoonCondition.builder()));
        add.accept(entry(GoFishItems.STARRY_SALMON.get(), 50, FullMoonCondition.builder()));
        add.accept(entry(GoFishItems.NEBULA_SWORDFISH.get(), 25, FullMoonCondition.builder()));

        add.accept(entry(GoFishItems.RAINY_BASS.get(), 100, WeatherCondition.builder(true, false, false)));
        add.accept(entry(GoFishItems.THUNDERING_BASS.get(), 50, WeatherCondition.builder(false, true, false)));
        add.accept(entry(
                GoFishItems.CLOUDY_CRAB.get(),
                50,
                LocationCheck.checkLocation(LocationPredicate.Builder.atYLocation(MinMaxBounds.Doubles.atLeast(150)))));
        add.accept(entry(GoFishItems.BLIZZARD_BASS.get(), 100, WeatherCondition.builder(false, false, true)));
    }

    private static LootPoolEntryContainer.Builder<?> entry(
            ItemLike item, int weight, LootItemCondition.Builder condition) {
        return LootItem.lootTableItem(item).setWeight(weight).when(condition);
    }
}
