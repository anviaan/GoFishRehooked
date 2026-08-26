package net.anvian.gofish.registry;

import net.anvian.gofish.GoFish;
import net.anvian.gofish.api.SoundInstance;
import net.anvian.gofish.item.ExtendedFishingRodItem;
import net.anvian.gofish.item.LureItem;
import net.anvian.gofish.item.SoulLureItem;
import net.anvian.gofish.item.TooltippedItem;
import net.anvian.gofish.platform.PlatformRegistryObject;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ChorusFruitItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

@SuppressWarnings(value = "unused")
public class GoFishItems {
    private GoFishItems() {}

    public static final PlatformRegistryObject<Item> BLAZE_ROD = register(
            "blaze_rod",
            () -> new ExtendedFishingRodItem.Builder()
                    .durability(125)
                    .autosmelt()
                    .lavaProof(true)
                    .build());

    public static final PlatformRegistryObject<Item> SKELETAL_ROD = register(
            "skeletal_rod",
            () -> new ExtendedFishingRodItem.Builder()
                    .durability(75)
                    .withCastSound(new SoundInstance(SoundEvents.SKELETON_STEP, 1.0F, SoundInstance.DEFAULT_PITCH))
                    .withRetrieveSound(new SoundInstance(SoundEvents.SKELETON_STEP, 0.5F, SoundInstance.DEFAULT_PITCH))
                    .lavaProof(true)
                    .build());

    public static final PlatformRegistryObject<Item> DIAMOND_REINFORCED_ROD = register(
            "diamond_reinforced_rod",
            () -> new ExtendedFishingRodItem.Builder()
                    .durability(300)
                    .color(ChatFormatting.AQUA)
                    .lavaProof(true)
                    .build());

    public static final PlatformRegistryObject<Item> EYE_OF_FISHING = register(
            "ender_rod",
            () -> new ExtendedFishingRodItem.Builder()
                    .durability(250)
                    .color(ChatFormatting.LIGHT_PURPLE)
                    .build());

    public static final PlatformRegistryObject<Item> FROSTED_ROD = register(
            "frosted_rod",
            () -> new ExtendedFishingRodItem.Builder().durability(150).build());

    public static final PlatformRegistryObject<Item> SLIME_ROD = register(
            "slime_rod",
            () -> new ExtendedFishingRodItem.Builder()
                    .durability(150)
                    .color(ChatFormatting.GREEN)
                    .withCastSound(new SoundInstance(SoundEvents.SLIME_JUMP, 0.8F, SoundInstance.DEFAULT_PITCH))
                    .withRetrieveSound(new SoundInstance(SoundEvents.SLIME_JUMP, 0.5F, SoundInstance.DEFAULT_PITCH))
                    .build());

    public static final PlatformRegistryObject<Item> SOUL_ROD = register(
            "soul_rod",
            () -> new ExtendedFishingRodItem.Builder()
                    .durability(250)
                    .color(ChatFormatting.LIGHT_PURPLE)
                    .baseExperienceGain(5)
                    .lavaProof(true)
                    .build());

    public static final PlatformRegistryObject<Item> MATRIX_ROD = register(
            "matrix_rod",
            () -> new ExtendedFishingRodItem.Builder()
                    .durability(200)
                    .color(ChatFormatting.LIGHT_PURPLE)
                    .build());

    public static final PlatformRegistryObject<Item> CELESTIAL_ROD = register(
            "celestial_rod",
            () -> new ExtendedFishingRodItem.Builder()
                    .durability(150)
                    .color(ChatFormatting.LIGHT_PURPLE)
                    .tooltipLines(1)
                    .nightLuck()
                    .build());

    // fish
    public static final PlatformRegistryObject<Item> ENDER_EEL = register(
            "ender_eel",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).build())));
    public static final PlatformRegistryObject<Item> ICICLE_FISH = register(
            "icicle_fish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .effect(new MobEffectInstance(MobEffects.HARM, 0, 0), 1)
                            .build())));
    public static final PlatformRegistryObject<Item> LILYFISH = register(
            "lilyfish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationModifier(0.5f)
                            .build())));
    public static final PlatformRegistryObject<Item> MATRIX_FISH = register(
            "matrix_fish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.5f)
                            .build())));
    public static final PlatformRegistryObject<Item> SEAWEED =
            register("seaweed", () -> new Item(new Item.Properties()));
    public static final PlatformRegistryObject<Item> BAKED_SEAWEED = register(
            "baked_seaweed",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.5f)
                            .build())));
    public static final PlatformRegistryObject<Item> SEAWEED_EEL = register(
            "seaweed_eel",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(4).build())));
    public static final PlatformRegistryObject<Item> SLIMEFISH = register(
            "slimefish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.25f)
                            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 5, 0), 1)
                            .build())));
    public static final PlatformRegistryObject<Item> SNOWBALL_FISH = register(
            "snowball_fish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.25f)
                            .build())));
    public static final PlatformRegistryObject<Item> TERRAFISH = register(
            "terrafish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.5f)
                            .build())));
    public static final PlatformRegistryObject<Item> CARROT_CARP = register(
            "carrot_carp",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.25f)
                            .build())));
    public static final PlatformRegistryObject<Item> BAKED_CARROT_CARP = register(
            "baked_carrot_carp",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationModifier(0.5f)
                            .build())));
    public static final PlatformRegistryObject<Item> OAKFISH = register(
            "oakfish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(3).build())));
    public static final PlatformRegistryObject<Item> CHARFISH = register(
            "charfish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .effect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * 5, 0), 1)
                            .build())));

    // nether
    public static final PlatformRegistryObject<Item> SPIKERFISH =
            register("spikerfish", () -> new Item(new Item.Properties().fireResistant()));
    public static final PlatformRegistryObject<Item> BLACKSTONE_TROUT = register(
            "blackstone_trout",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.75f)
                            .build())
                    .fireResistant()));
    public static final PlatformRegistryObject<Item> GRILLED_BLACKSTONE_TROUT = register(
            "grilled_blackstone_trout",
            () -> new TooltippedItem(
                    new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .nutrition(8)
                                    .saturationModifier(0.8f)
                                    .build())
                            .fireResistant(),
                    2));
    public static final PlatformRegistryObject<Item> GRILLED_BLACKSTONE_DELUXE = register(
            "grilled_blackstone_deluxe",
            () -> new TooltippedItem(
                    new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .nutrition(11)
                                    .saturationModifier(0.8f)
                                    .build())
                            .fireResistant(),
                    3));
    public static final PlatformRegistryObject<Item> BONEFISH =
            register("bonefish", () -> new Item(new Item.Properties().fireResistant()));
    public static final PlatformRegistryObject<Item> GILDED_BLACKSTONE_CARP = register(
            "gilded_blackstone_carp",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.5f)
                            .build())
                    .fireResistant()));
    public static final PlatformRegistryObject<Item> SMOKEY_SALMON = register(
            "smokey_salmon",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationModifier(0.5f)
                            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 15, 0), 1)
                            .build())
                    .fireResistant()));
    public static final PlatformRegistryObject<Item> SOUL_SALMON = register(
            "soul_salmon",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationModifier(0.75f)
                            .build())
                    .fireResistant()));
    public static final PlatformRegistryObject<Item> MAGMA_COD = register(
            "magma_cod",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 15, 0), 1)
                            .nutrition(6)
                            .saturationModifier(0.5f)
                            .build())
                    .fireResistant()));
    public static final PlatformRegistryObject<Item> BASALT_BASS = register(
            "basalt_bass",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.25f)
                            .build())
                    .fireResistant()));
    public static final PlatformRegistryObject<Item> OBSIDIAN_HALIBUT = register(
            "obsidian_halibut",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.25f)
                            .build())
                    .fireResistant()));

    // end
    public static final PlatformRegistryObject<Item> ENDFISH = register(
            "endfish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).build())));
    public static final PlatformRegistryObject<Item> BAKED_ENDFISH = register(
            "baked_endfish",
            () -> new TooltippedItem(
                    new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .nutrition(6)
                                    .saturationModifier(0.8f)
                                    .build()),
                    2));
    public static final PlatformRegistryObject<Item> ENDFISH_AND_CHORUS = register(
            "endfish_and_chorus",
            () -> new TooltippedItem(
                    new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .nutrition(8)
                                    .saturationModifier(1f)
                                    .build()),
                    2));
    public static final PlatformRegistryObject<Item> CHORUS_COD = register(
            "chorus_cod",
            () -> new ChorusFruitItem(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(6).build())
                    .rarity(Rarity.EPIC)));
    public static final PlatformRegistryObject<Item> DRAGONFISH = register(
            "dragonfish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(8).build())
                    .rarity(Rarity.EPIC)));
    public static final PlatformRegistryObject<Item> OMEGA_FLOATER = register(
            "omega_floater",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(6).build())
                    .rarity(Rarity.EPIC)));
    public static final PlatformRegistryObject<Item> PORTAL_PUFFER = register(
            "portal_puffer",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(4).build())
                    .rarity(Rarity.EPIC)));

    // Full Moon fish
    public static final PlatformRegistryObject<Item> LUNARFISH = register(
            "lunarfish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).build())));
    public static final PlatformRegistryObject<Item> GALAXY_STARFISH = register(
            "galaxy_starfish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).build())));
    public static final PlatformRegistryObject<Item> STARRY_SALMON = register(
            "starry_salmon",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).build())));
    public static final PlatformRegistryObject<Item> NEBULA_SWORDFISH = register(
            "nebula_swordfish",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).build())));
    public static final PlatformRegistryObject<Item> AQUATIC_ASTRAL_STEW = register(
            "aquatic_astral_stew",
            () -> new TooltippedItem(
                    new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .nutrition(9)
                                    .saturationModifier(0.75f)
                                    .build()),
                    3));

    // weather
    public static final PlatformRegistryObject<Item> RAINY_BASS = register(
            "rainy_bass",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(3)
                            .saturationModifier(0.5f)
                            .build())));
    public static final PlatformRegistryObject<Item> STEAMED_BASS = register(
            "steamed_bass",
            () -> new TooltippedItem(
                    new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .nutrition(6)
                                    .saturationModifier(0.25f)
                                    .build()),
                    2));
    public static final PlatformRegistryObject<Item> CLOUDY_CRAB = register(
            "cloudy_crab",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(3)
                            .saturationModifier(0.75f)
                            .build())));
    public static final PlatformRegistryObject<Item> THUNDERING_BASS = register(
            "thundering_bass",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.75f)
                            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 15 * 20), 1)
                            .build())));
    public static final PlatformRegistryObject<Item> SMOKED_CLOUDY_CRAB = register(
            "smoked_cloudy_crab",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(7)
                            .saturationModifier(0.25f)
                            .build())));
    public static final PlatformRegistryObject<Item> BLIZZARD_BASS = register(
            "blizzard_bass",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(3)
                            .saturationModifier(0.5f)
                            .build())));

    // accessories
    public static final PlatformRegistryObject<Item> GOLDEN_FISH = register(
            "golden_fish", () -> new LureItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 1));
    public static final PlatformRegistryObject<Item> SIMPLE_LURE =
            register("simple_lure", () -> new LureItem(new Item.Properties().stacksTo(1), 1));
    public static final PlatformRegistryObject<Item> SOUL_LURE =
            register("soul_lure", () -> new SoulLureItem(new Item.Properties().stacksTo(1)));

    public static <T extends Item> PlatformRegistryObject<T> register(
            String name, java.util.function.Supplier<T> item) {
        return GoFish.register(BuiltInRegistries.ITEM, GoFish.id(name), item);
    }

    public static void init() {
        // NO-OP
    }
}
