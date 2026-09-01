package net.anvian.gofish.item;

import net.anvian.gofish.api.ExperienceBobber;
import net.anvian.gofish.api.FireproofEntity;
import net.anvian.gofish.api.SmeltingBobber;
import net.anvian.gofish.api.SoundInstance;
import net.anvian.gofish.registry.GoFishEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExtendedFishingRodItem extends FishingRodItem {

    public record RodConfig(
            SoundInstance retrieve,
            SoundInstance cast,
            int baseLure,
            int baseLOTS,
            int baseExperience,
            boolean autosmelt,
            boolean lavaProof,
            boolean nightLuck,
            ChatFormatting formatting,
            int lines) {}

    private final RodConfig config;

    public ExtendedFishingRodItem(Properties settings, RodConfig config) {
        super(settings);
        this.config = config;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, @NotNull InteractionHand hand) {
        ItemStack heldStack = user.getItemInHand(hand);
        RandomSource random = world.random;

        if (user.fishing != null) {
            handleRetrieve(world, user, hand, heldStack, random);
        } else {
            handleCast(world, user, heldStack, random);
        }

        return InteractionResultHolder.sidedSuccess(heldStack, world.isClientSide());
    }

    private void handleRetrieve(
            Level world, Player user, InteractionHand hand, ItemStack heldStack, RandomSource random) {
        if (!world.isClientSide) {
            int damage = user.fishing.retrieve(heldStack);
            heldStack.hurtAndBreak(
                    damage, user, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }

        playSound(world, user, random, config.retrieve);
    }

    private void handleCast(Level world, Player user, ItemStack heldStack, RandomSource random) {
        playSound(world, user, random, config.cast);

        if (!world.isClientSide) {
            spawnBobber(world, user, heldStack);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
    }

    private void playSound(Level world, Player user, RandomSource random, SoundInstance sound) {
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                sound.getSound(),
                SoundSource.NEUTRAL,
                sound.getVolume(random),
                sound.getPitch(random));
    }

    private void spawnBobber(Level world, Player user, ItemStack heldStack) {
        if (!(world instanceof ServerLevel serverWorld)) {
            return;
        }

        FishingBonusCalculator.Bonuses bonuses = FishingBonusCalculator.collect(world, user, config.nightLuck);

        boolean smelts = shouldSmelt(serverWorld, heldStack, bonuses.smeltBuff());

        int lure = Math.min(
                (int) (EnchantmentHelper.getFishingTimeReduction(serverWorld, heldStack, user) * 20.0F)
                        + config.baseLure
                        + bonuses.lure(),
                5);
        int lots = EnchantmentHelper.getFishingLuckBonus(serverWorld, heldStack, user)
                + config.baseLOTS
                + bonuses.luck();

        FishingHook bobber = new FishingHook(user, world, lots, lure);
        world.addFreshEntity(bobber);
        ((FireproofEntity) bobber).gfSetFireproof(config.lavaProof);
        ((SmeltingBobber) bobber).gfSetSmelts(smelts);
        ((ExperienceBobber) bobber).gfSetBaseExperience(config.baseExperience + bonuses.experience());
    }

    private boolean shouldSmelt(ServerLevel world, ItemStack heldStack, boolean smeltBuff) {
        boolean hasDeepfryEnchantment =
                EnchantmentHelper.getItemEnchantmentLevel(
                                GoFishEnchantments.getDeepfryHolder(world.registryAccess()), heldStack)
                        != 0;
        boolean rodAutosmelts = heldStack.getItem() instanceof ExtendedFishingRodItem extendedfishingroditem
                && extendedfishingroditem.autosmelts();
        return hasDeepfryEnchantment || rodAutosmelts || smeltBuff;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component name = super.getName(stack);
        if (name instanceof MutableComponent mutablecomponent) {
            mutablecomponent.withStyle(config.formatting);
        }

        return name;
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            Item.@NotNull TooltipContext context,
            @NotNull List<Component> tooltip,
            @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        if (config.lines > 0) {
            for (int i = 1; i <= config.lines; i++) {
                tooltip.add(Component.translatable(String.format("%s.tooltip_%d", getDescriptionId(), i))
                        .withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public boolean autosmelts() {
        return config.autosmelt;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    public boolean canFishInLava() {
        return config.lavaProof;
    }

    public static class Builder {

        private final Item.Properties settings = new Item.Properties().durability(100);
        private SoundInstance retrieve =
                new SoundInstance(SoundEvents.FISHING_BOBBER_RETRIEVE, 1.0F, SoundInstance.DEFAULT_PITCH);
        private SoundInstance cast =
                new SoundInstance(SoundEvents.FISHING_BOBBER_THROW, 0.5F, SoundInstance.DEFAULT_PITCH);
        private int experience = 1;
        private boolean autosmelt = false;
        private boolean lavaProof = false;
        private boolean nightLuck = false;
        private ChatFormatting formatting = ChatFormatting.WHITE;
        private int tooltipLines = 0;

        public Builder() {
            // NO-OP
        }

        public Builder durability(int durability) {
            this.settings.durability(durability);
            return this;
        }

        public Builder color(ChatFormatting formatting) {
            this.formatting = formatting;
            return this;
        }

        public Builder withRetrieveSound(SoundInstance sound) {
            this.retrieve = sound;
            return this;
        }

        public Builder withCastSound(SoundInstance sound) {
            this.cast = sound;
            return this;
        }

        public Builder baseExperienceGain(int experience) {
            this.experience = experience;
            return this;
        }

        public Builder autosmelt() {
            this.autosmelt = true;
            return this;
        }

        public Builder lavaProof(boolean lavaProof) {
            this.lavaProof = lavaProof;
            return this;
        }

        public Builder nightLuck() {
            this.nightLuck = true;
            return this;
        }

        public Builder tooltipLines(int tooltipLines) {
            this.tooltipLines = tooltipLines;
            return this;
        }

        public ExtendedFishingRodItem build() {
            int baseLOTS = 0;
            int baseLure = 0;
            RodConfig config = new RodConfig(
                    retrieve,
                    cast,
                    baseLure,
                    baseLOTS,
                    experience,
                    autosmelt,
                    lavaProof,
                    nightLuck,
                    formatting,
                    tooltipLines);
            return new ExtendedFishingRodItem(settings, config);
        }
    }
}
