package net.anvian.gofish.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.anvian.gofish.impl.GoFishLootTables;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class FishCommand {
    private FishCommand() {
    }

    private static final LootContextParamSet FISHING_COMMAND_PARAMS = LootContextParamSet.builder().required(LootContextParams.ORIGIN).required(LootContextParams.TOOL).optional(LootContextParams.THIS_ENTITY).required(LootContextParams.KILLER_ENTITY).build();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fish").requires(source -> source.hasPermission(2)).executes(context -> {
            fish(context, 1);
            return 1;
        }).then(Commands.argument("count", IntegerArgumentType.integer(1, 1000)).executes(context -> {
            fish(context, IntegerArgumentType.getInteger(context, "count"));
            return 1;
        })));
    }

    private static void fish(CommandContext<CommandSourceStack> context, int times) throws CommandSyntaxException {
        CommandSourceStack serverCommandSource = context.getSource();
        ServerPlayer player = serverCommandSource.getPlayerOrException();
        ServerLevel world = context.getSource().getLevel();

        LootParams lootContext = new LootParams.Builder(serverCommandSource.getLevel()).withParameter(LootContextParams.ORIGIN, player.position()).withParameter(LootContextParams.TOOL, player.getItemInHand(player.getUsedItemHand())).withOptionalParameter(LootContextParams.THIS_ENTITY, player).withParameter(LootContextParams.KILLER_ENTITY, player).create(FISHING_COMMAND_PARAMS);

        LootTable table;
        final DimensionType dimension = world.dimensionType();
        if (dimension.ultraWarm()) {
            table = world.getServer().getLootData().getLootTable(GoFishLootTables.NETHER_FISHING);
        } else if (!dimension.bedWorks()) {
            table = world.getServer().getLootData().getLootTable(GoFishLootTables.END_FISHING);
        } else {
            table = world.getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
        }

        for (int z = 0; z < times; z++) {
            List<ItemStack> list = table.getRandomItems(lootContext);
            list.forEach(player::addItem);
        }
    }
}
