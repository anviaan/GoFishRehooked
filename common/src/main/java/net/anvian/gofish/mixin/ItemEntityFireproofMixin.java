package net.anvian.gofish.mixin;

import net.anvian.gofish.api.FireproofEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityFireproofMixin extends Entity implements FireproofEntity {

    @Unique
    private static final EntityDataAccessor<Boolean> GF_FIRE_IMMUNE =
            SynchedEntityData.defineId(ItemEntityFireproofMixin.class, EntityDataSerializers.BOOLEAN);

    protected ItemEntityFireproofMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "defineSynchedData", at = @At("RETURN"))
    private void registerFireImmuneTracker(CallbackInfo ci) {
        entityData.define(GF_FIRE_IMMUNE, false);
    }

    @Inject(method = "fireImmune", at = @At("RETURN"), cancellable = true)
    private void isLavaFishingLoot(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || entityData.get(GF_FIRE_IMMUNE));
    }

    @Override
    public boolean isOnFire() {
        if (gfIsFireproof()) {
            return false;
        }

        return super.isOnFire();
    }

    @Override
    public boolean gfIsFireproof() {
        return entityData.get(GF_FIRE_IMMUNE);
    }

    @Override
    public void gfSetFireproof(boolean value) {
        entityData.set(GF_FIRE_IMMUNE, value);
    }
}
