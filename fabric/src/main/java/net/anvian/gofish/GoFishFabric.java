package net.anvian.gofish;

import net.anvian.gofish.platform.FabricPlatformHooks;
import net.fabricmc.api.ModInitializer;

public final class GoFishFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GoFish.init(new FabricPlatformHooks());
    }
}
