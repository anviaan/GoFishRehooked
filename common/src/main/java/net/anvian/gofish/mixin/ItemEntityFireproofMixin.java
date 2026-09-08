package net.anvian.gofish.mixin;

import net.anvian.gofish.api.FireproofEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityFireproofMixin extends Entity implements FireproofEntity {


    @Unique
    private boolean gfFireImmune;

    protected ItemEntityFireproofMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "fireImmune", at = @At("RETURN"), cancellable = true)
    private void isLavaFishingLoot(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || gfFireImmune);
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
        return gfFireImmune;
    }

    @Override
    public void gfSetFireproof(boolean value) {
        gfFireImmune = value;
    }
}
