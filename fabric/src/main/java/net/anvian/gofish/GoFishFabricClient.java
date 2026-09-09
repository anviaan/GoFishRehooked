package net.anvian.gofish;

import net.anvian.gofish.client.be.AstralCrateRenderer;
import net.anvian.gofish.client.item.AstralCrateItemRenderer;
import net.anvian.gofish.registry.GoFishEntities;
import net.anvian.gofish.registry.GoFishParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.client.particle.WakeParticle;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public final class GoFishFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(GoFishEntities.ASTRAL_CRATE.get(), context -> new AstralCrateRenderer());
        SpecialModelRenderers.ID_MAPPER.put(GoFish.id("astral_crate"), AstralCrateItemRenderer.Unbaked.MAP_CODEC);
        ParticleProviderRegistry.getInstance().register(GoFishParticles.LAVA_FISHING.get(), WakeParticle.Provider::new);
    }
}
