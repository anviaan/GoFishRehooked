package net.anvian.gofish.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.anvian.gofish.entity.block.AstralCrateBlockEntity;
import net.anvian.gofish.registry.GoFishBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class AstralCrateItemRenderer extends BlockEntityWithoutLevelRenderer {

    private final AstralCrateBlockEntity crate = new AstralCrateBlockEntity(
            BlockPos.ZERO, GoFishBlocks.ASTRAL_CRATE.get().defaultBlockState());

    public AstralCrateItemRenderer() {
        super(
                Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(
            @NotNull ItemStack stack,
            @NotNull ItemDisplayContext displayContext,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getBlockEntityRenderDispatcher().renderItem(crate, poseStack, buffer, packedLight, packedOverlay);
        minecraft
                .getBlockRenderer()
                .renderSingleBlock(
                        GoFishBlocks.ASTRAL_CRATE.get().defaultBlockState(),
                        poseStack,
                        buffer,
                        packedLight,
                        packedOverlay);
    }
}
