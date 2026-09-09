package net.anvian.gofish.mixin;

import net.anvian.gofish.api.SmeltingBobber;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(FishingHook.class)
public abstract class FishingBobberAutosmeltMixin extends Entity implements SmeltingBobber {

    protected FishingBobberAutosmeltMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Unique
    private boolean gfSmelts = false;

    @Override
    public boolean gfCanSmelt() {
        return gfSmelts;
    }

    @Override
    public void gfSetSmelts(boolean value) {
        this.gfSmelts = value;
    }

    @ModifyVariable(
            method = "retrieve",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/entity/item/ItemEntity;setDeltaMovement(DDD)V",
                            shift = At.Shift.AFTER),
            ordinal = 0)
    private ItemEntity processOutput(ItemEntity entity) {
        if (gfSmelts) {
            Optional<RecipeHolder<SmeltingRecipe>> cooked = ((net.minecraft.server.level.ServerLevel) level())
                    .recipeAccess()
                    .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(entity.getItem()), level());

            cooked.ifPresent(smeltingRecipe ->
                    entity.setItem(smeltingRecipe.value().assemble(new SingleRecipeInput(entity.getItem()))));
        }

        return entity;
    }
}
