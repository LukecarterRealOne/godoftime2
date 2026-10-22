package com.godoftime.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The Astral Sword. Forged out of broken time itself.
 * - Deals absurd, effectively infinite damage to anything it strikes.
 * - Right click to erase the block or entity you're looking at from existence.
 * - Constantly sheds yellow astral light (particles).
 */
public class AstralSwordItem extends SwordItem {

    // A "big enough to be functionally infinite" damage value. Avoids Float.MAX_VALUE which
    // can misbehave with vanilla damage calculations / mods that multiply it.
    public static final float INFINITE_DAMAGE = 3.4E7F;

    public AstralSwordItem(Properties properties) {
        super(ModItemTiers.ASTRAL, 8, -2.0F, properties.fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Infinite damage: the target simply ceases to be, as time no longer permits its existence.
        if (!target.level().isClientSide) {
            target.level().playSound(null, target.blockPosition(), SoundEvents.BEACON_DEACTIVATE,
                    SoundSource.PLAYERS, 1.0F, 0.6F);
            spawnBladeBurst(target.level(), target.getX(), target.getY() + target.getBbHeight() / 2.0, target.getZ());
            if (target.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                target.hurt(target.damageSources().magic(), INFINITE_DAMAGE);
                if (target.isAlive() && !(target instanceof net.minecraft.world.entity.player.Player)) {
                    // Absolute erasure for anything that somehow survives (e.g. damage-resistant bosses).
                    target.remove(net.minecraft.world.entity.Entity.RemovalReason.KILLED);
                }
            }
        }
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            HitResult hit = player.pick(24.0D, 0.0F, false);
            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                BlockPos pos = blockHit.getBlockPos();
                BlockState state = level.getBlockState(pos);
                if (!state.isAir() && state.getDestroySpeed(level, pos) >= 0) {
                    level.removeBlock(pos, false);
                    level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.0F, 1.4F);
                    spawnBladeBurst(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                    player.swing(hand, true);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        // Right-clicking a block directly also erases it.
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!level.isClientSide) {
            BlockState state = level.getBlockState(pos);
            if (!state.isAir()) {
                level.removeBlock(pos, false);
                spawnBladeBurst(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (level.isClientSide && selected && entity instanceof Player player && level.getGameTime() % 2 == 0) {
            // Yellow astral light sheds off the blade while held — custom sprite, not vanilla END_ROD.
            Vec3 tip = player.position().add(0, player.getEyeHeight() * 0.6, 0)
                    .add(player.getLookAngle().scale(0.6));
            level.addParticle(com.godoftime.particle.ModParticles.ASTRAL_TRAIL.get(),
                    tip.x, tip.y, tip.z, 0, 0.01, 0);
        }
    }

    private void spawnBladeBurst(Level level, double x, double y, double z) {
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(com.godoftime.particle.ModParticles.VOID_SLASH.get(), x, y, z, 14, 0.4, 0.4, 0.4, 0.08);
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
