package net.anvian.gofish.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.anvian.gofish.item.ExtendedFishingRodItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHookRenderer.class)
public class FishingBobberEntityRendererMixin {

    private Player gofishOwner;

    @Inject(method = "render", at = @At("HEAD"))
    private void storeContext(
            FishingHook hook,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            CallbackInfo callbackInfo) {
        gofishOwner = hook.getPlayerOwner();
    }

    @ModifyVariable(
            method = "render*",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttackAnim(F)F"),
            index = 6)
    private int modifyFishingRodAnimation(int light) {
        ItemStack itemStack = gofishOwner.getMainHandItem();
        return itemStack.getItem() != Items.FISHING_ROD && itemStack.getItem() instanceof ExtendedFishingRodItem
                ? -light
                : light;
    }
}
