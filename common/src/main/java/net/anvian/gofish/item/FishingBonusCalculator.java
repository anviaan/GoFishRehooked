package net.anvian.gofish.item;

import net.anvian.gofish.api.FishingBonus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

public final class FishingBonusCalculator {

    private FishingBonusCalculator() {}

    public static Bonuses collect(Level world, Player player, boolean nightLuck) {
        boolean bonusLuck = nightLuck && world != null && world.isDarkOutside();
        List<FishingBonus> applicable = new ArrayList<>();
        Set<Item> found = new HashSet<>();
        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            Item item = stack.getItem();

            if (item instanceof FishingBonus bonus && !found.contains(item) && bonus.shouldApply(world, player)) {
                found.add(item);
                applicable.add(bonus);
            }
        }

        return calculate(applicable, bonusLuck ? 1 : 0);
    }

    static Bonuses calculate(Iterable<? extends FishingBonus> bonuses, int initialLuck) {
        boolean smeltBuff = false;
        int bonusLure = 0;
        int bonusLuck = initialLuck;
        int bonusExperience = 0;

        Set<FishingBonus> found = Collections.newSetFromMap(new IdentityHashMap<>());
        for (FishingBonus bonus : bonuses) {
            if (found.add(bonus)) {
                smeltBuff = bonus.providesAutoSmelt() || smeltBuff;
                bonusLure += bonus.getLure();
                bonusLuck += bonus.getLuckOfTheSea();
                bonusExperience += bonus.getBaseExperience();
            }
        }

        return new Bonuses(smeltBuff, bonusLure, bonusLuck, bonusExperience);
    }

    public record Bonuses(boolean smeltBuff, int lure, int luck, int experience) {}
}
