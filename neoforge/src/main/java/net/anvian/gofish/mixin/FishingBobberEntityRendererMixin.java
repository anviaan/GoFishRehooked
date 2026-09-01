package net.anvian.gofish.mixin;

import net.anvian.gofish.item.ExtendedFishingRodItem;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.state.FishingHookRenderState;
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

    @Inject(method = "extractRenderState", at = @At("HEAD"))
    private void storeContext(
            FishingHook hook, FishingHookRenderState renderState, float partialTick, CallbackInfo callbackInfo) {
        gofishOwner = hook.getPlayerOwner();
    }

    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true, index = 4)
    private int modifyFishingRodAnimation(int light) {
        if (gofishOwner == null) {
            return light;
        }

        ItemStack itemStack = gofishOwner.getMainHandItem();
        return itemStack.getItem() != Items.FISHING_ROD && itemStack.getItem() instanceof ExtendedFishingRodItem
                ? -light
                : light;
    }
}
