package net.anvian.gofish.mixin;

import net.anvian.gofish.item.ExtendedFishingRodItem;
import net.anvian.gofish.registry.GoFishParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FishingHook.class)
public abstract class FishingBobberLavaFishingMixin extends Entity {

    protected FishingBobberLavaFishingMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow
    public abstract Player getPlayerOwner();

    @Override
    @Shadow
    public abstract void remove(Entity.@NotNull RemovalReason reason);

    @ModifyVariable(
            method = "tick",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z",
                            ordinal = 0),
            index = 2)
    @SuppressWarnings("java:S107")
    private float bobberInLava(float value) {
        BlockPos blockPos = this.blockPosition();
        FluidState fluidState = this.level().getFluidState(blockPos);
        Player player = getPlayerOwner();

        if (!fluidState.is(FluidTags.LAVA)) {
            return value;
        }

        if (canFishInLava(player)) {
            return fluidState.getHeight(this.level(), blockPos);
        }

        if (!player.isCreative()) {
            player.getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(5, player, EquipmentSlot.MAINHAND);
        }

        if (level() instanceof ServerLevel serverlevel) {
            serverlevel.sendParticles(ParticleTypes.LAVA, getX(), getY(), getZ(), 5, 0, 1, 0, 0);
        }

        player.playSound(SoundEvents.GENERIC_BURN, .5f, 1f);
        remove(RemovalReason.KILLED);

        return value;
    }

    @Redirect(
            method = "tick",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z",
                            ordinal = 1))
    private boolean fallOutsideLiquid(FluidState fluid, TagKey<Fluid> tag) {
        return !fluid.isEmpty();
    }

    @Inject(
            method = "catchingFish",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
                            ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD)
    @SuppressWarnings("java:S107")
    private void fishingLavaParticles(
            BlockPos pos,
            CallbackInfo ci,
            ServerLevel serverWorld,
            int i,
            BlockPos blockPos,
            float f,
            float g,
            float h,
            double d,
            double e,
            double j,
            BlockState blockState) {
        if (!blockState.is(Blocks.LAVA)) {
            return;
        }
        if (this.random.nextFloat() < 0.15F) {
            serverWorld.sendParticles(ParticleTypes.LAVA, d, e - 0.1D, j, 1, g, 0.1D, h, 0.0D);
        }

        float dZ = f * 0.04F;
        float dX = h * 0.04F;
        serverWorld.sendParticles(GoFishParticles.LAVA_FISHING.get(), d, e, j, 0, dX, 0.01D, (-dZ), 1.0D);
        serverWorld.sendParticles(GoFishParticles.LAVA_FISHING.get(), d, e, j, 0, (-dX), 0.01D, dZ, 1.0D);
    }

    @Inject(
            method = "catchingFish",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
                            ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILHARD)
    @SuppressWarnings("java:S107")
    private void fishSecondaryLavaParticles(
            BlockPos pos,
            CallbackInfo ci,
            ServerLevel serverWorld,
            int i,
            BlockPos blockPos,
            float f,
            float g,
            float h,
            double d,
            double e,
            double j,
            BlockState blockState) {
        if (blockState.is(Blocks.LAVA)) {
            serverWorld.sendParticles(
                    ParticleTypes.LAVA,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    2 + this.random.nextInt(2),
                    0.10000000149011612D,
                    0.0D,
                    0.10000000149011612D,
                    0.0D);
        }
    }

    private boolean canFishInLava(Player player) {
        Item mainHandItem = player.getMainHandItem().getItem();
        Item offHandItem = player.getOffhandItem().getItem();

        return isLavaFishingRod(mainHandItem) || isLavaFishingRod(offHandItem);
    }

    private boolean isLavaFishingRod(Item item) {
        return item instanceof ExtendedFishingRodItem usedRod && usedRod.canFishInLava();
    }

    @Redirect(
            method =
                    "getOpenWaterTypeForBlock(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/entity/projectile/FishingHook$OpenWaterType;",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean isInValidLiquid(FluidState fluidState, TagKey<Fluid> tag) {
        return !fluidState.isEmpty();
    }
}
