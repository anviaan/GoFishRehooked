package net.anvian.gofish.item;

import net.anvian.gofish.api.FishingBonus;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SoulLureItem extends Item implements FishingBonus {

    public SoulLureItem(Properties settings) {
        super(settings);
    }

    @Override
    public int getLuckOfTheSea() {
        return 1;
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            Item.@NotNull TooltipContext context,
            @NotNull List<Component> tooltip,
            @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        tooltip.add(Component.translatable(String.format("gofish.lure.tooltip_%d", 1))
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(String.format("gofish.lots.tooltip_%d", 2), 1, " in Soul Sand Valley")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean shouldApply(Level world, Player player) {
        return world.getBiome(player.blockPosition()).is(Biomes.SOUL_SAND_VALLEY);
    }
}
