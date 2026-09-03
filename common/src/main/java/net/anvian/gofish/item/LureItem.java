package net.anvian.gofish.item;

import net.anvian.gofish.api.FishingBonus;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class LureItem extends Item implements FishingBonus {

    private final int lure;

    public LureItem(Properties settings, int lure) {
        super(settings);
        this.lure = lure;
    }

    @Override
    public int getLure() {
        return lure;
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            Item.@NotNull TooltipContext context,
            @NotNull TooltipDisplay tooltipDisplay,
            @NotNull Consumer<Component> tooltip,
            @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltip, flag);

        for (int i = 1; i <= 2; i++) {
            tooltip.accept(Component.translatable(String.format("gofish.lure.tooltip_%d", i), lure)
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
