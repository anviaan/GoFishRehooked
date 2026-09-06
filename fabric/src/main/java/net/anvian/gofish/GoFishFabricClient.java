package net.anvian.gofish;

import net.anvian.gofish.client.be.AstralCrateRenderer;
import net.anvian.gofish.client.item.AstralCrateItemRenderer;
import net.anvian.gofish.registry.GoFishBlocks;
import net.anvian.gofish.registry.GoFishEntities;
import net.anvian.gofish.registry.GoFishParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.WakeParticle;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public final class GoFishFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(GoFishBlocks.ASTRAL_CRATE.get(), ChunkSectionLayer.CUTOUT);
        BlockEntityRenderers.register(GoFishEntities.ASTRAL_CRATE.get(), AstralCrateRenderer::new);
        SpecialModelRenderers.ID_MAPPER.put(
                GoFish.id("astral_crate"), AstralCrateItemRenderer.Unbaked.MAP_CODEC);
        ParticleFactoryRegistry.getInstance().register(GoFishParticles.LAVA_FISHING.get(), WakeParticle.Provider::new);
    }
}
