package net.anvian.gofish.client.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.anvian.gofish.entity.block.AstralCrateBlockEntity;
import net.anvian.gofish.registry.GoFishBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class AstralCrateItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    private final AstralCrateBlockEntity crate = new AstralCrateBlockEntity(
            BlockPos.ZERO, GoFishBlocks.ASTRAL_CRATE.get().defaultBlockState());

    @Override
    public void render(
            ItemStack stack,
            ItemDisplayContext displayContext,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        Minecraft minecraft = Minecraft.getInstance();
        poseStack.pushPose();
        minecraft.getBlockEntityRenderDispatcher().renderItem(crate, poseStack, buffer, packedLight, packedOverlay);
        minecraft
                .getBlockRenderer()
                .renderSingleBlock(
                        GoFishBlocks.ASTRAL_CRATE.get().defaultBlockState(),
                        poseStack,
                        buffer,
                        packedLight,
                        packedOverlay);
        poseStack.popPose();
    }
}
