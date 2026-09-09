package net.anvian.gofish.item;

import net.anvian.anvianslib.util.RegistryUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class CrateItem extends BlockItem {

    private final Identifier loot;

    public CrateItem(Block block, Properties settings, Identifier loot) {
        super(block, settings);
        this.loot = loot;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        if (player != null && player.isShiftKeyDown()) {
            openCrate(context.getLevel(), player, context.getHand());
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level world, Player user, @NotNull InteractionHand hand) {
        if (user.isShiftKeyDown()) {
            openCrate(world, user, hand);
            return InteractionResult.SUCCESS;
        }

        return super.use(world, user, hand);
    }

    private void openCrate(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide()) {
            getDrops((ServerLevel) world, loot, user.position())
                    .forEach(stack -> Containers.dropItemStack(world, user.getX(), user.getY(), user.getZ(), stack));

            if (!user.isCreative()) {
                user.getItemInHand(hand).shrink(1);
            }
        }
    }

    private List<ItemStack> getDrops(ServerLevel world, Identifier identifier, Vec3 pos) {
        List<ItemStack> output = new ArrayList<>();

        if (world != null && !world.isClientSide()) {
            LootTable supplier = Objects.requireNonNull(world.getServer())
                    .reloadableRegistries()
                    .getLootTable(
                            RegistryUtil.key(Registries.LOOT_TABLE, identifier.getNamespace(), identifier.getPath()));
            LootParams.Builder builder = new LootParams.Builder(world).withParameter(LootContextParams.ORIGIN, pos);

            List<ItemStack> stacks = supplier.getRandomItems(builder.create(LootContextParamSets.CHEST));
            output.addAll(stacks);
        }

        return output;
    }

    @Override
    public void appendHoverText(
            @NonNull ItemStack stack,
            Item.@NonNull TooltipContext context,
            @NonNull TooltipDisplay tooltipDisplay,
            @NonNull Consumer<Component> tooltip,
            @NonNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltip, flag);
        tooltip.accept(Component.translatable("gofish.crate_tooltip")
                .withStyle(ChatFormatting.GRAY)
                .withStyle(ChatFormatting.ITALIC));
    }
}
