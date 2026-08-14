package net.anvian.gofish;

import net.anvian.gofish.client.be.AstralCrateRenderer;
import net.anvian.gofish.client.item.AstralCrateItemRenderer;
import net.anvian.gofish.registry.GoFishBlocks;
import net.anvian.gofish.registry.GoFishEntities;
import net.anvian.gofish.registry.GoFishItems;
import net.anvian.gofish.registry.GoFishParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.particle.WakeParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;

public final class GoFishFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(GoFishBlocks.ASTRAL_CRATE.get(), RenderType.cutout());
        BlockEntityRenderers.register(GoFishEntities.ASTRAL_CRATE.get(), AstralCrateRenderer::new);
        BuiltinItemRendererRegistry.INSTANCE.register(
                GoFishBlocks.ASTRAL_CRATE.get().asItem(), new AstralCrateItemRenderer());
        ParticleFactoryRegistry.getInstance().register(GoFishParticles.LAVA_FISHING.get(), WakeParticle.Provider::new);

        registerFishingRodPredicates(GoFishItems.BLAZE_ROD.get());
        registerFishingRodPredicates(GoFishItems.CELESTIAL_ROD.get());
        registerFishingRodPredicates(GoFishItems.FROSTED_ROD.get());
        registerFishingRodPredicates(GoFishItems.SOUL_ROD.get());
        registerFishingRodPredicates(GoFishItems.MATRIX_ROD.get());
        registerFishingRodPredicates(GoFishItems.SLIME_ROD.get());
        registerFishingRodPredicates(GoFishItems.DIAMOND_REINFORCED_ROD.get());
        registerFishingRodPredicates(GoFishItems.SKELETAL_ROD.get());
        registerFishingRodPredicates(GoFishItems.EYE_OF_FISHING.get());
    }

    private static void registerFishingRodPredicates(Item item) {
        ItemProperties.register(item, GoFish.id("cast"), (stack, level, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }

            boolean mainHand = livingEntity.getMainHandItem() == stack;
            boolean offHand = livingEntity.getOffhandItem() == stack;
            if (livingEntity.getMainHandItem().getItem() instanceof FishingRodItem) {
                offHand = false;
            }

            return (mainHand || offHand) && livingEntity instanceof Player player && player.fishing != null
                    ? 1.0F
                    : 0.0F;
        });
    }
}
