package net.anvian.gofish.client.be;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.anvian.gofish.entity.block.AstralCrateBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public final class AstralCrateRenderer extends TheEndPortalRenderer<AstralCrateBlockEntity> {

    private static final float[][] SIDES = {
        {0.0F, 0.99F, 0.01F, 1.0F, 0.99F, 1.0F, 1.0F, 1.0F},
        {0.01F, 0.99F, 0.99F, 0.0F, 0.01F, 0.0F, 0.0F, 0.0F},
        {0.99F, 0.99F, 0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F},
        {0.01F, 0.01F, 0.01F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F},
        {0.01F, 0.99F, 0.01F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F},
        {0.01F, 0.99F, 0.99F, 0.99F, 1.0F, 1.0F, 0.0F, 0.0F}
    };

    public AstralCrateRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(
            @NotNull AstralCrateBlockEntity entity,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        renderSides(poseStack.last().pose(), buffer.getBuffer(renderType()));
    }

    private void renderSides(Matrix4f pose, VertexConsumer vertices) {
        for (float[] side : SIDES) {
            renderSide(pose, vertices, side);
        }
    }

    private void renderSide(Matrix4f pose, VertexConsumer vertices, float[] side) {
        float color = 0.2F;
        vertices.vertex(pose, side[0], side[2], side[4])
                .color(color, color, color, 1.0F)
                .endVertex();
        vertices.vertex(pose, side[1], side[2], side[5])
                .color(color, color, color, 1.0F)
                .endVertex();
        vertices.vertex(pose, side[1], side[3], side[6])
                .color(color, color, color, 1.0F)
                .endVertex();
        vertices.vertex(pose, side[0], side[3], side[7])
                .color(color, color, color, 1.0F)
                .endVertex();
    }
}
