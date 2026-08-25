package net.anvian.gofish;

import net.anvian.gofish.platform.ForgePlatformHooks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GoFishConstants.MOD_ID)
public final class GoFishForge {
    public GoFishForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        GoFish.init(new ForgePlatformHooks(modEventBus));
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> GoFishForgeClient::register);
    }
}
