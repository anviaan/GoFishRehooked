package net.anvian.gofish.entity.block;

import net.anvian.gofish.registry.GoFishEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AstralCrateBlockEntity extends TheEndPortalBlockEntity {

    public AstralCrateBlockEntity(BlockPos pos, BlockState state) {
        super(GoFishEntities.ASTRAL_CRATE.get(), pos, state);
    }
}
