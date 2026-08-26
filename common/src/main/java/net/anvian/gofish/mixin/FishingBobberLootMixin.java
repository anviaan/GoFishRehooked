package net.anvian.gofish.mixin;

import net.anvian.gofish.GoFish;
import net.anvian.gofish.api.FireproofEntity;
import net.anvian.gofish.impl.GoFishLootTables;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingHook.class)
public abstract class FishingBobberLootMixin extends Entity {

    protected FishingBobberLootMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Redirect(
            method = "retrieve",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/server/ReloadableServerRegistries$Holder;getLootTable(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/world/level/storage/loot/LootTable;"))
    private LootTable getTable(ReloadableServerRegistries.Holder lootManager, ResourceKey<LootTable> id) {
        assert level().getServer() != null;

        Entity owner = ((FishingHook) (Object) this).getOwner();
        if (!(owner instanceof ServerPlayer) || GoFish.isFakePlayer(owner)) {
            return level().getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);
        }

        final DimensionType dimension = level().dimensionType();
        if (dimension.ultraWarm()) {
            return this.level().getServer().reloadableRegistries().getLootTable(GoFishLootTables.NETHER_FISHING);
        } else if (!dimension.bedWorks()) {
            return this.level().getServer().reloadableRegistries().getLootTable(GoFishLootTables.END_FISHING);
        }

        return this.level().getServer().reloadableRegistries().getLootTable(GoFishLootTables.OVERWORLD_FISHING);
    }

    @Redirect(
            method = "retrieve",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;setDeltaMovement(DDD)V"))
    private void setFireproof(ItemEntity itemEntity, double x, double y, double z) {
        if (level().dimensionType().ultraWarm()) {
            ((FireproofEntity) itemEntity).gfSetFireproof(true);
        }
        itemEntity.setDeltaMovement(x, y, z);
    }
}
