package net.anvian.gofish.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public final class GoFishEnchantments {
    private GoFishEnchantments() {}

    public static final ResourceKey<Enchantment> DEEPFRY =
            ResourceKey.create(Registries.ENCHANTMENT, net.anvian.gofish.GoFish.id("deepfry"));

    public static Holder<Enchantment> getDeepfryHolder(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(DEEPFRY);
    }

    public static void init() {
        // NO-OP
    }
}
