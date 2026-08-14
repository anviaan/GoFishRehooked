package net.anvian.gofish;

import net.anvian.anvianslib.platform.Services;
import net.anvian.anvianslib.util.LibUtil;
import net.anvian.gofish.platform.IPlatformHooks;
import net.anvian.gofish.platform.PlatformRegistryObject;
import net.anvian.gofish.registry.GoFishBlocks;
import net.anvian.gofish.registry.GoFishEnchantments;
import net.anvian.gofish.registry.GoFishEntities;
import net.anvian.gofish.registry.GoFishItems;
import net.anvian.gofish.registry.GoFishLoot;
import net.anvian.gofish.registry.GoFishParticles;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GoFish {

    public static final String MOD_ID = "gofish";
    public static final String MOD_NAME = "Go Fish";
    public static final String MOD_VERSION = "2.0.0";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final ResourceKey<CreativeModeTab> ITEM_GROUP =
            ResourceKey.create(Registries.CREATIVE_MODE_TAB, id("group"));

    private static IPlatformHooks platform;
    private static boolean initialized;

    private GoFish() {}

    public static void init(IPlatformHooks hooks) {
        if (initialized) {
            return;
        }

        platform = hooks;
        initialized = true;

        LOG.info("Initializing {} v{} on {}", MOD_ID, MOD_VERSION, Services.PLATFORM.getPlatformName());
        LibUtil.setupTelemetry(MOD_ID, MOD_VERSION);

        GoFishBlocks.init();
        GoFishItems.init();
        GoFishEnchantments.init();
        GoFishLoot.init();
        GoFishParticles.init();
        GoFishEntities.init();

        register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                ITEM_GROUP.location(),
                () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                        .icon(() -> new ItemStack(GoFishItems.GOLDEN_FISH.get()))
                        .title(Component.translatable("itemGroup.gofish.group"))
                        .displayItems((parameters, output) -> BuiltInRegistries.ITEM.entrySet().stream()
                                .filter(entry ->
                                        MOD_ID.equals(entry.getKey().location().getNamespace()))
                                .forEach(entry -> output.accept(entry.getValue())))
                        .build());

        hooks.registerPlatformHooks();
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static Item createCrateItem(Block block, Item.Properties properties, ResourceLocation lootTable) {
        if (platform == null) {
            throw new IllegalStateException("Go Fish platform hooks were not initialized");
        }

        return platform.createCrateItem(block, properties, lootTable);
    }

    public static boolean isFakePlayer(Entity entity) {
        if (platform == null) {
            throw new IllegalStateException("Go Fish platform hooks were not initialized");
        }

        return platform.isFakePlayer(entity);
    }

    public static <V, T extends V> PlatformRegistryObject<T> register(
            Registry<V> registry, ResourceLocation id, java.util.function.Supplier<T> supplier) {
        if (platform == null) {
            throw new IllegalStateException("Go Fish platform hooks were not initialized");
        }

        return platform.register(registry, id, supplier);
    }
}
