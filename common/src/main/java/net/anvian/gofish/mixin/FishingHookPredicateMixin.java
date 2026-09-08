package net.anvian.gofish.mixin;

import net.anvian.gofish.util.PermissionChecks;
import net.minecraft.advancements.criterion.FishingHookPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHookPredicate.class)
public class FishingHookPredicateMixin {

    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private void overrideCreativePredicate(
            Entity entity, ServerLevel world, Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ServerPlayer player && player.isCreative() && PermissionChecks.hasPermission(player)) {
            cir.setReturnValue(true);
        }
    }
}
