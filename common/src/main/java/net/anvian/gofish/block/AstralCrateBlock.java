package net.anvian.gofish.block;

import net.anvian.gofish.entity.block.AstralCrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AstralCrateBlock extends Block implements EntityBlock {

    public AstralCrateBlock(Properties settings) {
        super(settings);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new AstralCrateBlockEntity(pos, state);
    }
}
