package net.anvian.gofish;

import net.anvian.gofish.platform.NeoForgePlatformHooks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(GoFish.MOD_ID)
public final class GoFishNeoForge {
    public GoFishNeoForge(IEventBus modEventBus) {
        GoFish.init(new NeoForgePlatformHooks(modEventBus));
        if (FMLEnvironment.dist == Dist.CLIENT) {
            GoFishNeoForgeClient.register(modEventBus);
        }
    }
}
