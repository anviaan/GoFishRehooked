package net.anvian.gofish;

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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public final class GoFish {

    public static final Logger LOG = LoggerFactory.getLogger(GoFishConstants.MOD_NAME);

    private static IPlatformHooks platform;
    private static boolean initialized;

    private GoFish() {}

    public static void init(IPlatformHooks hooks) {
        if (initialized) {
            return;
        }

        platform = hooks;
        initialized = true;

        LOG.info(
                "Initializing {} v{} on {}",
                GoFishConstants.MOD_ID,
                GoFishConstants.MOD_VERSION,
                platform.getClass().getSimpleName());

        LibUtil.setupTelemetry(GoFishConstants.MOD_ID, GoFishConstants.MOD_VERSION);

        GoFishBlocks.init();
        GoFishItems.init();
        GoFishEnchantments.init();
        GoFishLoot.init();
        GoFishParticles.init();
        GoFishEntities.init();

        register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                GoFishConstants.ITEM_GROUP.location(),
                () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                        .icon(() -> new ItemStack(GoFishItems.GOLDEN_FISH.get()))
                        .title(Component.translatable("itemGroup.gofish.group"))
                        .displayItems((parameters, output) -> {
                            Set<ResourceLocation> orderedItems = new HashSet<>();

                            GoFishConstants.CREATIVE_ITEM_ORDER.forEach(name -> {
                                ResourceLocation itemId = id(name);
                                output.accept(BuiltInRegistries.ITEM.get(itemId));
                                orderedItems.add(itemId);
                            });

                            BuiltInRegistries.ITEM.entrySet().stream()
                                    .filter(entry -> GoFishConstants.MOD_ID.equals(
                                            entry.getKey().location().getNamespace()))
                                    .filter(entry -> !orderedItems.contains(
                                            entry.getKey().location()))
                                    .forEach(entry -> output.accept(entry.getValue()));
                        })
                        .build());

        hooks.registerPlatformHooks();
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(GoFishConstants.MOD_ID, name);
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
