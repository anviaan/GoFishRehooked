package net.anvian.gofish;

import net.anvian.gofish.client.be.AstralCrateRenderer;
import net.anvian.gofish.registry.GoFishEntities;
import net.anvian.gofish.registry.GoFishParticles;
import net.minecraft.client.particle.WakeParticle;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public final class GoFishForgeClient {

    private GoFishForgeClient() {
    }

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(GoFishForgeClient::registerParticles);
        modEventBus.addListener(GoFishForgeClient::clientSetup);
    }

    private static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GoFishParticles.LAVA_FISHING.get(), WakeParticle.Provider::new);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(net.anvian.gofish.registry.GoFishBlocks.ASTRAL_CRATE.get(), RenderType.cutout());
            BlockEntityRenderers.register(GoFishEntities.ASTRAL_CRATE.get(), AstralCrateRenderer::new);
        });
    }
}
