package net.anvian.gofish.item;

import net.anvian.gofish.api.FishingBonus;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FishingBonusCalculatorTest {

    @Test
    public void countsEachBonusItemTypeOnceAndKeepsValuesSeparate() {
        Bonus repeated = new Bonus(2, 3, 4, true);
        Bonus other = new Bonus(1, 5, 6, false);

        FishingBonusCalculator.Bonuses bonuses =
                FishingBonusCalculator.calculate(List.of(repeated, repeated, repeated, other), 0);

        assertTrue(bonuses.smeltBuff());
        assertEquals(3, bonuses.lure());
        assertEquals(8, bonuses.luck());
        assertEquals(10, bonuses.experience());
    }

    private static final class Bonus implements FishingBonus {
        private final int lure;
        private final int luck;
        private final int experience;
        private final boolean smelt;

        private Bonus(int lure, int luck, int experience, boolean smelt) {
            this.lure = lure;
            this.luck = luck;
            this.experience = experience;
            this.smelt = smelt;
        }

        @Override
        public int getLure() {
            return lure;
        }

        @Override
        public int getLuckOfTheSea() {
            return luck;
        }

        @Override
        public int getBaseExperience() {
            return experience;
        }

        @Override
        public boolean providesAutoSmelt() {
            return smelt;
        }
    }

}
