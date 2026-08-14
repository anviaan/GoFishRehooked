package net.anvian.gofish.mixin;

import net.anvian.gofish.api.ExperienceBobber;
import net.anvian.gofish.api.FireproofEntity;
import net.anvian.gofish.api.FishingBonus;
import net.anvian.gofish.api.SmeltingBobber;
import net.anvian.gofish.item.ExtendedFishingRodItem;
import net.anvian.gofish.registry.GoFishEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

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
            CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        this.heldStack = user.getItemInHand(hand);
        this.player = user;
    }

    @Redirect(
            method = "use",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean modifyBobber(Level world, Entity entity) {
        if (entity instanceof FishingHook bobber) {
            modifyBobber(world, bobber);
        }

        return world.addFreshEntity(entity);
    }

    @Unique
    private void modifyBobber(Level world, FishingHook bobber) {
        boolean smeltBuff = false;
        int bonusLure = 0;
        int bonusLuck = 0;
        int bonusExperience = 0;

        List<FishingBonus> found = new ArrayList<>();
        for (ItemStack stack : player.getInventory().items) {
            Item item = stack.getItem();

            if (item instanceof FishingBonus bonus && !found.contains(bonus) && bonus.shouldApply(world, player)) {
                found.add(bonus);
                smeltBuff = bonus.providesAutoSmelt() || smeltBuff;
                bonusLure += bonus.getLure();
                bonusLuck += bonus.getLuckOfTheSea();
                bonusExperience += bonus.getBaseExperience();
            }
        }

        boolean hasDeepfryEnchantment =
                EnchantmentHelper.getItemEnchantmentLevel(GoFishEnchantments.DEEPFRY.get(), heldStack) != 0;
        boolean rodAutosmelts = heldStack.getItem() instanceof ExtendedFishingRodItem extendedfishingroditem
                && extendedfishingroditem.autosmelts();
        boolean smelts = hasDeepfryEnchantment || rodAutosmelts || smeltBuff;

        ((FireproofEntity) bobber).gfSetFireproof(false);
        ((SmeltingBobber) bobber).gfSetSmelts(smelts);
        ((ExperienceBobber) bobber).gfSetBaseExperience(1 + bonusExperience);
        FishingBobberEntityAccessor accessor = (FishingBobberEntityAccessor) bobber;
        accessor.setLureLevel(Math.min((accessor.getLureLevel() + bonusLure), 5));
        accessor.setLuckOfTheSeaLevel(accessor.getLuckOfTheSeaLevel() + bonusLuck);
    }
}
