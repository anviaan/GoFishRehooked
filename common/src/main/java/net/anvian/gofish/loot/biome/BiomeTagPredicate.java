package net.anvian.gofish.loot.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public record BiomeTagPredicate(List<TagKey<Biome>> valid) {

    public static final Codec<BiomeTagPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    TagKey.codec(Registries.BIOME).listOf().fieldOf("valid").forGetter(BiomeTagPredicate::valid))
            .apply(instance, BiomeTagPredicate::new));

    public static BiomeTagPredicate create(List<TagKey<Biome>> valid) {
        return new BiomeTagPredicate(valid);
    }

    public List<TagKey<Biome>> getValid() {
        return valid;
    }

    public boolean test(Holder<Biome> biome) {

        for (TagKey<Biome> tag : valid) {
            if (biome.is(tag)) {
                return true;
            }
        }

        return false;
    }

    public static class Builder {

        private List<TagKey<Biome>> valid = new ArrayList<>();

        public static Builder create() {
            return new BiomeTagPredicate.Builder();
        }

        public Builder setValid(List<TagKey<Biome>> valid) {
            this.valid = valid;
            return this;
        }

        public Builder setValidByString(List<String> valid) {
            List<TagKey<Biome>> tagKeys = new ArrayList<>();
            for (String str : valid) {
                tagKeys.add(TagKey.create(Registries.BIOME, new ResourceLocation(str)));
            }
            return setValid(tagKeys);
        }

        public Builder add(String tag) {
            if (!tag.isEmpty()) {
                this.valid.add(TagKey.create(Registries.BIOME, new ResourceLocation(tag)));
            }

            return this;
        }

        public Builder of(BiomeTagPredicate biomePredicate) {
            this.valid = biomePredicate.valid;
            return this;
        }

        public BiomeTagPredicate build() {
            return new BiomeTagPredicate(this.valid);
        }
    }
}
