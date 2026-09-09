package net.anvian.gofish.loot.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.anvian.anvianslib.util.RegistryUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public record BiomePredicate(List<ResourceKey<Biome>> valid) {

    public static final Codec<BiomePredicate> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(ResourceKey.codec(Registries.BIOME)
                            .listOf()
                            .fieldOf("valid")
                            .forGetter(BiomePredicate::valid))
                    .apply(instance, BiomePredicate::new));

    public static BiomePredicate create(List<ResourceKey<Biome>> valid) {
        return new BiomePredicate(valid);
    }

    public List<ResourceKey<Biome>> getValid() {
        return valid;
    }

    public boolean test(Holder<Biome> biome) {
        for (ResourceKey<Biome> key : valid) {
            if (biome.is(key)) {
                return true;
            }
        }
        return false;
    }

    private static ResourceKey<Biome> key(Identifier identifier) {
        return RegistryUtil.key(Registries.BIOME, identifier.getNamespace(), identifier.getPath());
    }

    public static class Builder {

        private List<ResourceKey<Biome>> valid;

        public static Builder create() {
            return new BiomePredicate.Builder();
        }

        public Builder setValid(List<ResourceKey<Biome>> valid) {
            this.valid = valid;
            return this;
        }

        public Builder setValidFromString(List<String> valid) {
            List<ResourceKey<Biome>> rKeys = new ArrayList<>();
            for (String str : valid) {
                if (!str.isEmpty()) {
                    rKeys.add(key(Identifier.parse(str)));
                }
            }

            return setValid(rKeys);
        }

        public Builder add(ResourceKey<Biome> biome) {
            valid.add(biome);
            return this;
        }

        public Builder add(String biome) {
            if (!biome.isEmpty()) {
                valid.add(key(Identifier.parse(biome)));
            }

            return this;
        }

        public Builder of(BiomePredicate biomePredicate) {
            this.valid = biomePredicate.valid;
            return this;
        }

        public BiomePredicate build() {
            return new BiomePredicate(this.valid);
        }
    }
}
