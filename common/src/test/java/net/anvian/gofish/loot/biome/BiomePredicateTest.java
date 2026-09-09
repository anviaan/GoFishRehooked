package net.anvian.gofish.loot.biome;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class BiomePredicateTest {

    @Test
    public void ignoresEmptyBiomeIds() {
        assertNotNull(BiomePredicate.Builder.create().setValidFromString(List.of("")));
    }
}
