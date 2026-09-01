package net.anvian.gofish.registry;

import net.anvian.gofish.GoFish;
import net.anvian.gofish.block.AstralCrateBlock;
import net.anvian.gofish.block.CrateBlock;
import net.anvian.gofish.platform.PlatformRegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

@SuppressWarnings(value = "unused")
public class GoFishBlocks {
    private GoFishBlocks() {}

    public static final PlatformRegistryObject<Block> WOODEN_CRATE = registerCrate(
            "wooden_crate",
            () -> new CrateBlock(
                    blockProperties("wooden_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD))),
            new Item.Properties().stacksTo(8),
            GoFish.id("gameplay/fishing/wooden_crate"));
    public static final PlatformRegistryObject<Block> IRON_CRATE = registerCrate(
            "iron_crate",
            () -> new CrateBlock(
                    blockProperties("iron_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK))),
            new Item.Properties().stacksTo(8),
            GoFish.id("gameplay/fishing/iron_crate"));
    public static final PlatformRegistryObject<Block> GOLDEN_CRATE = registerCrate(
            "golden_crate",
            () -> new CrateBlock(
                    blockProperties("golden_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK))),
            new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON),
            GoFish.id("gameplay/fishing/golden_crate"));
    public static final PlatformRegistryObject<Block> DIAMOND_CRATE = registerCrate(
            "diamond_crate",
            () -> new CrateBlock(
                    blockProperties("diamond_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK))),
            new Item.Properties().stacksTo(8).rarity(Rarity.RARE),
            GoFish.id("gameplay/fishing/diamond_crate"));
    public static final PlatformRegistryObject<Block> FROSTED_CRATE = registerCrate(
            "frosted_crate",
            () -> new CrateBlock(
                    blockProperties("frosted_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ICE))),
            new Item.Properties().stacksTo(8).rarity(Rarity.RARE),
            GoFish.id("gameplay/fishing/frosted_crate"));
    public static final PlatformRegistryObject<Block> SLIMEY_CRATE = registerCrate(
            "slimey_crate",
            () -> new CrateBlock(
                    blockProperties("slimey_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK))),
            new Item.Properties().stacksTo(8),
            GoFish.id("gameplay/fishing/slimey_crate"));
    public static final PlatformRegistryObject<Block> SUPPLY_CRATE = registerCrate(
            "supply_crate",
            () -> new CrateBlock(
                    blockProperties("supply_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD))),
            new Item.Properties().stacksTo(8),
            GoFish.id("gameplay/fishing/supply_crate"));
    public static final PlatformRegistryObject<Block> FIERY_CRATE = registerCrate(
            "fiery_crate",
            () -> new CrateBlock(
                    blockProperties("fiery_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS))),
            new Item.Properties().fireResistant().stacksTo(8),
            GoFish.id("gameplay/fishing/fiery_crate"));
    public static final PlatformRegistryObject<Block> SOUL_CRATE = registerCrate(
            "soul_crate",
            () -> new CrateBlock(blockProperties("soul_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))),
            new Item.Properties().fireResistant().stacksTo(8).rarity(Rarity.RARE),
            GoFish.id("gameplay/fishing/soul_crate"));
    public static final PlatformRegistryObject<Block> GILDED_BLACKSTONE_CRATE = registerCrate(
            "gilded_blackstone_crate",
            () -> new CrateBlock(blockProperties(
                    "gilded_blackstone_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.GILDED_BLACKSTONE))),
            new Item.Properties().fireResistant().stacksTo(8).rarity(Rarity.UNCOMMON),
            GoFish.id("gameplay/fishing/gilded_blackstone_crate"));
    public static final PlatformRegistryObject<Block> ASTRAL_CRATE = registerCrate(
            "astral_crate",
            () -> new AstralCrateBlock(blockProperties(
                    "astral_crate",
                    BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).noOcclusion())),
            new Item.Properties().fireResistant().stacksTo(8).rarity(Rarity.EPIC),
            GoFish.id("gameplay/fishing/astral_crate"));
    public static final PlatformRegistryObject<Block> END_CRATE = registerCrate(
            "end_crate",
            () -> new AstralCrateBlock(
                    blockProperties("end_crate", BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE))),
            new Item.Properties().fireResistant().stacksTo(8).rarity(Rarity.EPIC),
            GoFish.id("gameplay/fishing/end_crate"));

    public static <T extends Block> PlatformRegistryObject<T> register(String name, Supplier<T> block) {
        return GoFish.register(BuiltInRegistries.BLOCK, GoFish.id(name), block);
    }

    private static BlockBehaviour.Properties blockProperties(String name, BlockBehaviour.Properties properties) {
        return properties.setId(ResourceKey.create(Registries.BLOCK, GoFish.id(name)));
    }

    public static <T extends Block> PlatformRegistryObject<T> registerCrate(
            String name, Supplier<T> block, Item.Properties settings, ResourceLocation id) {
        settings.setId(ResourceKey.create(Registries.ITEM, GoFish.id(name)));
        PlatformRegistryObject<T> registeredBlock = GoFish.register(BuiltInRegistries.BLOCK, GoFish.id(name), block);
        GoFish.register(
                BuiltInRegistries.ITEM,
                GoFish.id(name),
                () -> GoFish.createCrateItem(registeredBlock.get(), settings, id));
        return registeredBlock;
    }

    public static <T extends Block> PlatformRegistryObject<T> register(
            String name, Supplier<T> block, Item.Properties settings) {
        settings.setId(ResourceKey.create(Registries.ITEM, GoFish.id(name)));
        PlatformRegistryObject<T> registeredBlock = GoFish.register(BuiltInRegistries.BLOCK, GoFish.id(name), block);
        GoFish.register(BuiltInRegistries.ITEM, GoFish.id(name), () -> new BlockItem(registeredBlock.get(), settings));
        return registeredBlock;
    }

    public static void init() {
        // NO-OP
    }
}
