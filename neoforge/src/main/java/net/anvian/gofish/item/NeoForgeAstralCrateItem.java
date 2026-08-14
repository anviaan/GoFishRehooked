package net.anvian.gofish.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public final class NeoForgeAstralCrateItem extends CrateItem {

    public NeoForgeAstralCrateItem(Block block, Item.Properties properties, ResourceLocation lootTable) {
        super(block, properties, lootTable);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private AstralCrateItemRenderer renderer;

            @Override
            public net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new AstralCrateItemRenderer();
                }
                return renderer;
            }
        });
    }
}
