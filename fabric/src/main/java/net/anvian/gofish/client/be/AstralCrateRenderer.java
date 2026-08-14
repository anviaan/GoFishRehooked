package net.anvian.gofish.client.be;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.anvian.gofish.entity.block.AstralCrateBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import org.joml.Matrix4f;

import javax.annotation.Nonnull;

public final class AstralCrateRenderer extends TheEndPortalRenderer<AstralCrateBlockEntity> {
    private static final float COLOR = 0.2F;

    public AstralCrateRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(
            @Nonnull AstralCrateBlockEntity entity,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        renderSides(poseStack.last().pose(), buffer.getBuffer(renderType()));
    }

    private void renderSides(Matrix4f pose, VertexConsumer vertices) {
        renderSide(pose, vertices, new Quad(0.0F, 0.99F, 0.01F, 1.0F, 0.99F, 1.0F, 1.0F, 1.0F));
        renderSide(pose, vertices, new Quad(0.01F, 0.99F, 0.99F, 0.0F, 0.01F, 0.0F, 0.0F, 0.0F));
        renderSide(pose, vertices, new Quad(0.99F, 0.99F, 0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F));
        renderSide(pose, vertices, new Quad(0.01F, 0.01F, 0.01F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F));
        renderSide(pose, vertices, new Quad(0.01F, 0.99F, 0.01F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F));
        renderSide(pose, vertices, new Quad(0.01F, 0.99F, 0.99F, 0.99F, 1.0F, 1.0F, 0.0F, 0.0F));
    }

    private void renderSide(Matrix4f pose, VertexConsumer vertices, Quad quad) {
        vertices.vertex(pose, quad.x1(), quad.y1(), quad.z1()).color(COLOR, COLOR, COLOR, 1.0F).endVertex();
        vertices.vertex(pose, quad.x2(), quad.y1(), quad.z2()).color(COLOR, COLOR, COLOR, 1.0F).endVertex();
        vertices.vertex(pose, quad.x2(), quad.y2(), quad.z3()).color(COLOR, COLOR, COLOR, 1.0F).endVertex();
        vertices.vertex(pose, quad.x1(), quad.y2(), quad.z4()).color(COLOR, COLOR, COLOR, 1.0F).endVertex();
    }

    private record Quad(float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4) {}
}
