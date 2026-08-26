package net.anvian.gofish.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TooltippedItem extends Item {

    private final int lines;

    public TooltippedItem(Properties settings, int lines) {
        super(settings);
        this.lines = lines;
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            Item.@NotNull TooltipContext context,
            @NotNull List<Component> tooltip,
            @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        if (lines > 0) {
            for (int i = 1; i <= lines; i++) {
                tooltip.add(Component.translatable(String.format("%s.tooltip_%d", getDescriptionId(), i))
                        .withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
