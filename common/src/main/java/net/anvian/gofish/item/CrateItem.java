package net.anvian.gofish.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrateItem extends BlockItem {

    private final ResourceLocation loot;

    public CrateItem(Block block, Properties settings, ResourceLocation loot) {
        super(block, settings);
        this.loot = loot;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        if (player != null && player.isShiftKeyDown()) {
            openCrate(context.getLevel(), player, context.getHand());
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }

        return super.useOn(context);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level world, Player user, @NotNull InteractionHand hand) {
        if (user.isShiftKeyDown()) {
            openCrate(world, user, hand);
            return InteractionResultHolder.success(user.getItemInHand(hand));
        }

        return super.use(world, user, hand);
    }

    private void openCrate(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide) {
            getDrops((ServerLevel) world, loot, user.position())
                    .forEach(stack -> Containers.dropItemStack(world, user.getX(), user.getY(), user.getZ(), stack));

            if (!user.isCreative()) {
                user.getItemInHand(hand).shrink(1);
            }
        }
    }

    private List<ItemStack> getDrops(ServerLevel world, ResourceLocation identifier, Vec3 pos) {
        List<ItemStack> output = new ArrayList<>();

        if (world != null && !world.isClientSide) {
            LootTable supplier = Objects.requireNonNull(world.getServer())
                    .reloadableRegistries()
                    .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, identifier));
            LootParams.Builder builder = new LootParams.Builder(world).withParameter(LootContextParams.ORIGIN, pos);

            List<ItemStack> stacks = supplier.getRandomItems(builder.create(LootContextParamSets.CHEST));
            output.addAll(stacks);
        }

        return output;
    }

    @Override
    public void appendHoverText(
            ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("gofish.crate_tooltip")
                .withStyle(ChatFormatting.GRAY)
                .withStyle(ChatFormatting.ITALIC));
    }
}
