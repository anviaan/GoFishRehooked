package net.anvian.gofish;

import net.anvian.gofish.client.be.AstralCrateRenderer;
import net.anvian.gofish.item.AstralCrateItemRenderer;
import net.anvian.gofish.registry.GoFishBlocks;
import net.anvian.gofish.registry.GoFishEntities;
import net.anvian.gofish.registry.GoFishParticles;
import net.minecraft.client.particle.WakeParticle;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

public final class GoFishNeoForgeClient {

    private GoFishNeoForgeClient() {}

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(GoFishNeoForgeClient::clientSetup);
        modEventBus.addListener(GoFishNeoForgeClient::registerParticles);
        modEventBus.addListener(GoFishNeoForgeClient::registerClientExtensions);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> BlockEntityRenderers.register(GoFishEntities.ASTRAL_CRATE.get(), AstralCrateRenderer::new));
    }

    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GoFishParticles.LAVA_FISHING.get(), WakeParticle.Provider::new);
    }

    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
                new IClientItemExtensions() {
                    private AstralCrateItemRenderer renderer;

                    @Override
                    public net.minecraft.client.renderer.@NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        if (renderer == null) {
                            renderer = new AstralCrateItemRenderer();
                        }
                        return renderer;
                    }
                },
                GoFishBlocks.ASTRAL_CRATE.get().asItem());
    }
}
