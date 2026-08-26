package net.anvian.gofish.enchantment;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class DeepfryEnchantment extends Enchantment {

    public DeepfryEnchantment() {
        super(Enchantment.definition(
                ItemTags.FISHING_ENCHANTABLE,
                2,
                1,
                Enchantment.constantCost(15),
                Enchantment.constantCost(65),
                2,
                EquipmentSlot.MAINHAND,
                EquipmentSlot.OFFHAND));
    }
}
