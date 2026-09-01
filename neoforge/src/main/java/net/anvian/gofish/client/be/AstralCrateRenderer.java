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

    private record Vertex(float x, float y, float z) {}

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
        renderSide(
                pose,
                vertices,
                new Vertex(0.0F, 0.01F, 0.99F),
                new Vertex(0.99F, 0.01F, 1.0F),
                new Vertex(0.99F, 1.0F, 1.0F),
                new Vertex(0.0F, 1.0F, 1.0F));
        renderSide(
                pose,
                vertices,
                new Vertex(0.01F, 0.99F, 0.01F),
                new Vertex(0.99F, 0.99F, 0.0F),
                new Vertex(0.99F, 0.0F, 0.0F),
                new Vertex(0.01F, 0.0F, 0.0F));
        renderSide(
                pose,
                vertices,
                new Vertex(0.99F, 0.99F, 0.0F),
                new Vertex(0.99F, 0.99F, 1.0F),
                new Vertex(0.99F, 0.0F, 1.0F),
                new Vertex(0.99F, 0.0F, 0.0F));
        renderSide(
                pose,
                vertices,
                new Vertex(0.01F, 0.01F, 0.0F),
                new Vertex(0.01F, 0.01F, 1.0F),
                new Vertex(0.01F, 1.0F, 1.0F),
                new Vertex(0.01F, 1.0F, 0.0F));
        renderSide(
                pose,
                vertices,
                new Vertex(0.01F, 0.01F, 0.0F),
                new Vertex(0.99F, 0.01F, 0.0F),
                new Vertex(0.99F, 0.0F, 1.0F),
                new Vertex(0.01F, 0.0F, 1.0F));
        renderSide(
                pose,
                vertices,
                new Vertex(0.01F, 0.99F, 1.0F),
                new Vertex(0.99F, 0.99F, 1.0F),
                new Vertex(0.99F, 0.99F, 0.0F),
                new Vertex(0.01F, 0.99F, 0.0F));
    }

    private void renderSide(Matrix4f pose, VertexConsumer vertices, Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
        float color = 0.2F;
        vertices.addVertex(pose, v1.x(), v1.y(), v1.z()).setColor(color, color, color, 1.0F);
        vertices.addVertex(pose, v2.x(), v2.y(), v2.z()).setColor(color, color, color, 1.0F);
        vertices.addVertex(pose, v3.x(), v3.y(), v3.z()).setColor(color, color, color, 1.0F);
        vertices.addVertex(pose, v4.x(), v4.y(), v4.z()).setColor(color, color, color, 1.0F);
    }
}
