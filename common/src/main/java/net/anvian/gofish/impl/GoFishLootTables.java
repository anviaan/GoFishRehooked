package net.anvian.gofish.impl;

import net.anvian.anvianslib.util.RegistryUtil;
import net.anvian.gofish.GoFishConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class GoFishLootTables {
    private GoFishLootTables() {}

    public static final ResourceKey<LootTable> OVERWORLD_FISHING = key("gameplay/fishing/overworld/fishing");
    public static final ResourceKey<LootTable> NETHER_FISHING = key("gameplay/fishing/nether/fishing");
    public static final ResourceKey<LootTable> END_FISHING = key("gameplay/fishing/end/fishing");

    private static ResourceKey<LootTable> key(String path) {
        return RegistryUtil.key(Registries.LOOT_TABLE, GoFishConstants.MOD_ID, path);
    }
}
