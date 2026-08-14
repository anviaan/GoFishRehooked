package net.anvian.gofish.mixin;

import net.anvian.gofish.api.FireproofEntity;
import net.anvian.gofish.impl.GoFishLootTables;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootDataManager;
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
                                    "Lnet/minecraft/world/level/storage/loot/LootDataManager;getLootTable(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/world/level/storage/loot/LootTable;"))
    private LootTable getTable(LootDataManager lootManager, ResourceLocation id) {
        assert level().getServer() != null;

        final DimensionType dimension = level().dimensionType();
        if (dimension.ultraWarm()) {
            return this.level().getServer().getLootData().getLootTable(GoFishLootTables.NETHER_FISHING);
        } else if (!dimension.bedWorks()) {
            return this.level().getServer().getLootData().getLootTable(GoFishLootTables.END_FISHING);
        }

        return this.level().getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
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
