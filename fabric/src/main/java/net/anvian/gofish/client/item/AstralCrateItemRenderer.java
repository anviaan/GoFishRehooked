package net.anvian.gofish.client.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public final class AstralCrateItemRenderer implements NoDataSpecialModelRenderer {
    private static final float COLOR = 0.2F;

    @Override
    public void submit(
            @NotNull PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int packedLight,
            int packedOverlay,
            boolean hasFoil,
            int outlineColor) {
        submitNodeCollector.submitCustomGeometry(
                poseStack, RenderTypes.endPortal(), (pose, vertices) -> renderSides(pose.pose(), vertices));
    }

    @Override
    public void getExtents(Consumer<Vector3fc> extents) {
        extents.accept(new Vector3f(0.0F, 0.0F, 0.0F));
        extents.accept(new Vector3f(0.0F, 0.0F, 1.0F));
        extents.accept(new Vector3f(0.0F, 1.0F, 0.0F));
        extents.accept(new Vector3f(0.0F, 1.0F, 1.0F));
        extents.accept(new Vector3f(1.0F, 0.0F, 0.0F));
        extents.accept(new Vector3f(1.0F, 0.0F, 1.0F));
        extents.accept(new Vector3f(1.0F, 1.0F, 0.0F));
        extents.accept(new Vector3f(1.0F, 1.0F, 1.0F));
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

    public static final class Unbaked implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        private Unbaked() {}

        @Override
        public NoDataSpecialModelRenderer bake(SpecialModelRenderer.BakingContext context) {
            return new AstralCrateItemRenderer();
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }
    }

    private record Quad(float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4) {}
}
