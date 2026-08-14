package net.anvian.gofish.item;

import net.anvian.gofish.api.FishingBonus;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);

        for (int i = 1; i <= 2; i++) {
            tooltip.add(Component.translatable(String.format("gofish.lure.tooltip_%d", i), lure)
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
