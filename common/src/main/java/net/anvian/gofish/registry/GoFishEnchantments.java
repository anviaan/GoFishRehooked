package net.anvian.gofish.registry;

import net.anvian.anvianslib.util.RegistryUtil;
import net.anvian.gofish.GoFishConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public final class GoFishEnchantments {
    private GoFishEnchantments() {}

    public static final ResourceKey<Enchantment> DEEPFRY =
            RegistryUtil.key(Registries.ENCHANTMENT, GoFishConstants.MOD_ID, "deepfry");

    public static Holder<Enchantment> getDeepfryHolder(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(DEEPFRY);
    }

    public static void init() {
        // NO-OP
    }
}
