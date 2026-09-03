package net.anvian.gofish.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

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
            @NotNull TooltipDisplay tooltipDisplay,
            @NotNull Consumer<Component> tooltip,
            @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltip, flag);

        if (lines > 0) {
            for (int i = 1; i <= lines; i++) {
                tooltip.accept(Component.translatable(String.format("%s.tooltip_%d", getDescriptionId(), i))
                        .withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
