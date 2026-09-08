package net.anvian.gofish.mixin;

import net.anvian.gofish.api.FireproofEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FishingHook.class)
public abstract class FishingBobberFireproofMixin extends Entity implements FireproofEntity {

    protected FishingBobberFireproofMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Unique
    private boolean gfFireImmune;

    @Override
    public boolean isOnFire() {
        return !gfFireImmune && super.isOnFire();
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
