package net.anvian.gofish.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import java.util.Set;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class AstralCrateItemRenderer implements NoDataSpecialModelRenderer {
    private static final float COLOR = 0.2F;

    @Override
    public void render(
            ItemDisplayContext displayContext,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay,
            boolean hasFoil) {
        renderSides(poseStack.last().pose(), buffer.getBuffer(RenderType.endPortal()));
    }

    @Override
    public void getExtents(Set<Vector3f> extents) {
        extents.add(new Vector3f(0.0F, 0.0F, 0.0F));
        extents.add(new Vector3f(0.0F, 0.0F, 1.0F));
        extents.add(new Vector3f(0.0F, 1.0F, 0.0F));
        extents.add(new Vector3f(0.0F, 1.0F, 1.0F));
        extents.add(new Vector3f(1.0F, 0.0F, 0.0F));
        extents.add(new Vector3f(1.0F, 0.0F, 1.0F));
        extents.add(new Vector3f(1.0F, 1.0F, 0.0F));
        extents.add(new Vector3f(1.0F, 1.0F, 1.0F));
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
        vertices.addVertex(pose, v1.x(), v1.y(), v1.z()).setColor(COLOR, COLOR, COLOR, 1.0F);
        vertices.addVertex(pose, v2.x(), v2.y(), v2.z()).setColor(COLOR, COLOR, COLOR, 1.0F);
        vertices.addVertex(pose, v3.x(), v3.y(), v3.z()).setColor(COLOR, COLOR, COLOR, 1.0F);
        vertices.addVertex(pose, v4.x(), v4.y(), v4.z()).setColor(COLOR, COLOR, COLOR, 1.0F);
    }

    public static final class Unbaked implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        private Unbaked() {}

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet modelSet) {
            return new AstralCrateItemRenderer();
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }
    }

    private record Vertex(float x, float y, float z) {}
}
