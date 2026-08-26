package net.anvian.gofish.mixin;

import net.anvian.gofish.registry.GoFishItems;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionBrewing.class)
public abstract class PotionBrewingMixin {

    @Inject(method = "addVanillaMixes", at = @At("TAIL"))
    private static void goFish$addMixes(PotionBrewing.Builder builder, CallbackInfo callbackInfo) {
        builder.addMix(Potions.AWKWARD, GoFishItems.CLOUDY_CRAB.get(), Potions.SLOW_FALLING);
        builder.addMix(Potions.AWKWARD, GoFishItems.CHARFISH.get(), Potions.WEAKNESS);
        builder.addMix(Potions.AWKWARD, GoFishItems.RAINY_BASS.get(), Potions.WATER_BREATHING);
        builder.addMix(Potions.AWKWARD, GoFishItems.MAGMA_COD.get(), Potions.FIRE_RESISTANCE);
    }
}
