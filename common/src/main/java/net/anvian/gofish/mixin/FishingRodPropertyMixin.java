package net.anvian.gofish.mixin;

import net.anvian.gofish.api.ExperienceBobber;
import net.anvian.gofish.api.FireproofEntity;
import net.anvian.gofish.api.SmeltingBobber;
import net.anvian.gofish.item.ExtendedFishingRodItem;
import net.anvian.gofish.item.FishingBonusCalculator;
import net.anvian.gofish.registry.GoFishEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingRodItem.class)
public class FishingRodPropertyMixin {

    @Unique
    private Player player;

    @Unique
    private ItemStack heldStack;

    @Inject(method = "use", at = @At("HEAD"))
    private void storeContext(
            Level world,
            Player user,
            InteractionHand hand,
            CallbackInfoReturnable<net.minecraft.world.InteractionResult> cir) {
        this.heldStack = user.getItemInHand(hand);
        this.player = user;
    }

    @ModifyArg(
            method = "use",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/world/entity/projectile/Projectile;spawnProjectile(Lnet/minecraft/world/entity/projectile/Projectile;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/projectile/Projectile;"))
    private Projectile modifyBobber(Projectile projectile) {
        Level world = projectile.level();
        if (projectile instanceof FishingHook bobber) {
            modifyBobber(world, bobber);
        }

        return projectile;
    }

    @Unique
    private void modifyBobber(Level world, FishingHook bobber) {
        FishingBonusCalculator.Bonuses bonuses = FishingBonusCalculator.collect(world, player, false);

        boolean hasDeepfryEnchantment = EnchantmentHelper.getItemEnchantmentLevel(
                        GoFishEnchantments.getDeepfryHolder(world.registryAccess()), heldStack)
                != 0;
        boolean rodAutosmelts = heldStack.getItem() instanceof ExtendedFishingRodItem extendedfishingroditem
                && extendedfishingroditem.autosmelts();
        boolean smelts = hasDeepfryEnchantment || rodAutosmelts || bonuses.smeltBuff();

        ((FireproofEntity) bobber).gfSetFireproof(false);
        ((SmeltingBobber) bobber).gfSetSmelts(smelts);
        ((ExperienceBobber) bobber).gfSetBaseExperience(1 + bonuses.experience());
        FishingBobberEntityAccessor accessor = (FishingBobberEntityAccessor) bobber;
        accessor.setLureLevel(Math.min((accessor.getLureLevel() + bonuses.lure()), 5));
        accessor.setLuckOfTheSeaLevel(accessor.getLuckOfTheSeaLevel() + bonuses.luck());
    }
}
