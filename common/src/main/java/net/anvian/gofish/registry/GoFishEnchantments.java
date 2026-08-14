package net.anvian.gofish.registry;

import net.anvian.gofish.GoFish;
import net.anvian.gofish.enchantment.DeepfryEnchantment;
import net.anvian.gofish.platform.PlatformRegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Supplier;

public class GoFishEnchantments {
    private GoFishEnchantments() {}

    public static final PlatformRegistryObject<Enchantment> DEEPFRY = register("deepfry", DeepfryEnchantment::new);

    public static PlatformRegistryObject<Enchantment> register(String name, Supplier<Enchantment> enchantment) {
        return GoFish.register(BuiltInRegistries.ENCHANTMENT, GoFish.id(name), enchantment);
    }

    public static void init() {
        // NO-OP
    }
}
