package net.anvian.gofish.registry;

import net.anvian.gofish.GoFish;
import net.anvian.gofish.particle.CustomDefaultParticleType;
import net.anvian.gofish.platform.PlatformRegistryObject;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class GoFishParticles {
    private GoFishParticles() {}

    public static final PlatformRegistryObject<SimpleParticleType> LAVA_FISHING = register("lava_fishing", false);

    private static PlatformRegistryObject<SimpleParticleType> register(String name, boolean alwaysShow) {
        return GoFish.register(
                BuiltInRegistries.PARTICLE_TYPE, GoFish.id(name), () -> new CustomDefaultParticleType(alwaysShow));
    }

    public static void init() {
        // NO-OP
    }
}
