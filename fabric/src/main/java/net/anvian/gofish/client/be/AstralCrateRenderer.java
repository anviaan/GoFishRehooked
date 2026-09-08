package net.anvian.gofish.client.be;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.anvian.gofish.entity.block.AstralCrateBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.AbstractEndPortalRenderer;
import net.minecraft.client.renderer.blockentity.state.EndPortalRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public final class AstralCrateRenderer extends AbstractEndPortalRenderer<AstralCrateBlockEntity, EndPortalRenderState> {
    private static final float COLOR = 0.2F;

    public AstralCrateRenderer() {
        super();
    }

    @Override
    public @NotNull EndPortalRenderState createRenderState() {
        return new EndPortalRenderState();
    }

    @Override
    public void submit(
            @NotNull EndPortalRenderState renderState,
            @NotNull PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            @NotNull CameraRenderState cameraRenderState) {
        submitNodeCollector.submitCustomGeometry(
                poseStack,
                renderType(),
                (pose, vertices) -> renderSides(pose.pose(), vertices));
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
        vertices.addVertex(pose, quad.x1(), quad.y1(), quad.z1()).setColor(COLOR, COLOR, COLOR, 1.0F);
        vertices.addVertex(pose, quad.x2(), quad.y1(), quad.z2()).setColor(COLOR, COLOR, COLOR, 1.0F);
        vertices.addVertex(pose, quad.x2(), quad.y2(), quad.z3()).setColor(COLOR, COLOR, COLOR, 1.0F);
        vertices.addVertex(pose, quad.x1(), quad.y2(), quad.z4()).setColor(COLOR, COLOR, COLOR, 1.0F);
    }

    private record Quad(float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4) {}
}
