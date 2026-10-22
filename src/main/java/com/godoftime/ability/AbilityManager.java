package com.godoftime.ability;

import com.godoftime.capability.ITimeGodData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import com.godoftime.particle.ModParticles;

import java.util.List;

public class AbilityManager {

    public static boolean execute(ServerPlayer player, ITimeGodData data, TimeAbility ability) {
        ServerLevel level = player.serverLevel();
        player.level().playSound(null, player.blockPosition(), SoundEvents.BEACON_AMBIENT, SoundSource.PLAYERS, 0.6F, 1.2F);

        switch (ability) {
            // ===================== YELLOW =====================
            case ASTRAL_SLASH -> {
                AABB box = player.getBoundingBox().inflate(4.0);
                hostilesIn(level, box).forEach(e -> e.hurt(player.damageSources().playerAttack(player), 14.0F));
                sparkle(level, player, ModParticles.VOID_SLASH.get(), 10);
            }
            case TIME_REND -> {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 2));
                message(player, "Your strikes ignore time and armor alike.");
            }
            case CHRONO_EXECUTE -> {
                LivingEntity target = rayTraceEntity(player, 6.0);
                if (target != null && target.getHealth() / target.getMaxHealth() <= 0.15F) {
                    target.hurt(player.damageSources().magic(), target.getMaxHealth());
                    sparkle(level, target, ModParticles.VOID_SLASH.get(), 20);
                } else {
                    message(player, "Target isn't weak enough yet.");
                    return false;
                }
            }
            case SOLAR_FLARE -> {
                AABB box = player.getBoundingBox().inflate(6.0);
                hostilesIn(level, box).forEach(e -> {
                    e.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
                    e.setSecondsOnFire(5);
                });
                level.sendParticles(ModParticles.VOID_SLASH.get(), player.getX(), player.getY() + 1, player.getZ(), 10, 1.5, 1.0, 1.5, 0.05);
            }
            case BLADE_STORM -> data.setBladeAuraTicks(Math.max(data.getBladeAuraTicks(), 20 * 10));
            case RAPID_STRIKES -> player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 3));
            case GRAVITY_CRUSH -> {
                AABB box = player.getBoundingBox().inflate(5.0);
                hostilesIn(level, box).forEach(e -> {
                    e.setDeltaMovement(e.getDeltaMovement().x, 1.1, e.getDeltaMovement().z);
                    e.hurtMarked = true;
                    e.hurt(player.damageSources().magic(), 6.0F);
                });
            }
            case TEMPORAL_PIERCE -> {
                LivingEntity target = rayTraceEntity(player, 6.0);
                if (target != null) {
                    int prevTime = target.invulnerableTime;
                    target.invulnerableTime = 0;
                    target.hurt(player.damageSources().magic(), 16.0F);
                    target.invulnerableTime = Math.min(target.invulnerableTime, prevTime);
                }
            }
            case ERASE -> {
                HitResult hit = player.pick(20.0, 0.0F, false);
                if (hit.getType() == HitResult.Type.ENTITY && ((EntityHitResult) hit).getEntity() instanceof LivingEntity le) {
                    le.remove(Entity.RemovalReason.KILLED);
                } else if (hit.getType() == HitResult.Type.BLOCK) {
                    BlockPos pos = ((BlockHitResult) hit).getBlockPos();
                    level.removeBlock(pos, false);
                }
            }
            case OVERCLOCK -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 160, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 160, 1));
            }
            case METEOR_MARK -> {
                LivingEntity target = rayTraceEntity(player, 16.0);
                if (target != null) {
                    BlockPos pos = target.blockPosition();
                    level.getServer().tell(new net.minecraft.server.TickTask(level.getServer().getTickCount() + 40, () -> {
                        level.explode(player, pos.getX(), pos.getY(), pos.getZ(), 2.0F, false, net.minecraft.world.level.Level.ExplosionInteraction.NONE);
                    }));
                    message(player, target.getName().getString() + " has been marked.");
                }
            }
            case JUDGEMENT_BEAM -> {
                Vec3 start = player.getEyePosition();
                Vec3 look = player.getLookAngle();
                for (int i = 1; i <= 20; i++) {
                    Vec3 p = start.add(look.scale(i));
                    level.sendParticles(ModParticles.ASTRAL_TRAIL.get(), p.x, p.y, p.z, 1, 0, 0, 0, 0);
                }
                AABB beamBox = new AABB(start, start.add(look.scale(20))).inflate(1.0);
                hostilesIn(level, beamBox).forEach(e -> e.hurt(player.damageSources().magic(), 18.0F));
            }
            case BERSERKERS_HOUR -> {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 2));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 0)); // cleared immediately; placeholder for lifesteal hook
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1, 0));
            }
            case SHATTER_TIME -> {
                LivingEntity target = rayTraceEntity(player, 8.0);
                if (target != null) {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 200));
                    target.addEffect(new MobEffectInstance(MobEffects.JUMP, 60, 128));
                    level.sendParticles(ModParticles.TIME_RIPPLE.get(), target.getX(), target.getY() + 1, target.getZ(), 16, 0.6, 0.6, 0.6, 0.02);
                }
            }
            case INFINITE_EDGE -> player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60, 9));
            case WRATH_OF_HYPERVERSE -> {
                AABB box = player.getBoundingBox().inflate(8.0);
                hostilesIn(level, box).forEach(e -> e.hurt(player.damageSources().magic(), 40.0F));
                level.sendParticles(ModParticles.VOID_SLASH.get(), player.getX(), player.getY() + 1, player.getZ(), 30, 2.0, 1.5, 2.0, 0.15);
            }
            case GOLDEN_EXECUTION -> {
                LivingEntity target = rayTraceEntity(player, 6.0);
                if (target != null) {
                    float missing = target.getMaxHealth() - target.getHealth();
                    target.hurt(player.damageSources().magic(), Math.max(4.0F, missing * 0.5F));
                }
            }

            // ===================== GREEN =====================
            case CHRONO_HEAL -> player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 2));
            case TIME_LOOP_CHECKPOINT -> {
                data.setCheckpoint(player.getX(), player.getY(), player.getZ());
                message(player, "This moment is saved in time.");
            }
            case REWIND_SELF -> {
                if (data.hasCheckpoint()) {
                    player.teleportTo(data.getCheckpointX(), data.getCheckpointY(), data.getCheckpointZ());
                } else {
                    message(player, "No checkpoint set — use Time Loop Checkpoint first.");
                    return false;
                }
            }
            case AGE_REVERSAL -> player.removeAllEffects();
            case BLOOM_BURST, FERTILE_TIME -> growCropsAround(level, player, 6);
            case LIFE_THREAD -> {
                AABB box = player.getBoundingBox().inflate(8.0);
                level.getEntitiesOfClass(Player.class, box).forEach(p ->
                        p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1)));
            }
            case HASTE_FIELD -> {
                AABB box = player.getBoundingBox().inflate(8.0);
                level.getEntitiesOfClass(Player.class, box).forEach(p -> {
                    p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1));
                    p.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 1));
                });
            }
            case REGROWTH_AURA -> {
                AABB box = player.getBoundingBox().inflate(8.0);
                level.getEntitiesOfClass(Player.class, box).forEach(p ->
                        p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 160, 1)));
            }
            case SECOND_CHANCE -> {
                data.setSecondChanceArmed(true);
                message(player, "Time will catch you, once.");
            }
            case SLOW_DECAY -> {
                player.getFoodData().setFoodLevel(20);
                player.removeEffect(MobEffects.POISON);
                player.removeEffect(MobEffects.WITHER);
            }
            case VITAL_SURGE -> player.heal(10.0F);
            case GUARDIANS_PACE -> player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 400, 2));
            case RESTORATION_PULSE -> {
                AABB box = player.getBoundingBox().inflate(8.0);
                level.getEntitiesOfClass(Player.class, box).forEach(p -> {
                    p.removeEffect(MobEffects.POISON);
                    p.removeEffect(MobEffects.WITHER);
                });
            }
            case TEMPORAL_SHIELD -> player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4));
            case NATURES_CLOCK -> {
                AABB box = player.getBoundingBox().inflate(8.0);
                level.getEntitiesOfClass(Animal.class, box).forEach(a -> {
                    if (a.getAge() < 0) a.setAge(Math.min(0, a.getAge() + 600));
                    a.setInLove(null);
                });
            }
            case SERENITY_FIELD -> {
                AABB box = player.getBoundingBox().inflate(10.0);
                level.getEntitiesOfClass(Mob.class, box).forEach(m -> m.setTarget(null));
            }

            // ===================== BLUE =====================
            case TIME_FREEZE -> {
                AABB box = player.getBoundingBox().inflate(7.0);
                hostilesIn(level, box).forEach(e -> {
                    e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 250));
                    level.sendParticles(ModParticles.TIME_RIPPLE.get(), e.getX(), e.getY() + 1, e.getZ(), 10, 0.4, 0.4, 0.4, 0.02);
                });
                level.sendParticles(ModParticles.TIME_RIPPLE.get(), player.getX(), player.getY() + 1, player.getZ(), 20, 3.0, 1.0, 3.0, 0.02);
            }
            case TIME_SLOW -> {
                AABB box = player.getBoundingBox().inflate(8.0);
                hostilesIn(level, box).forEach(e -> e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3)));
            }
            case BLINK -> {
                Vec3 target = player.position().add(player.getLookAngle().scale(8));
                player.teleportTo(target.x, target.y, target.z);
            }
            case WORLD_REWIND -> {
                if (data.getLastBrokenPos() != null) {
                    level.setBlockAndUpdate(data.getLastBrokenPos(), data.getLastBrokenState());
                    message(player, "A moment undone.");
                } else {
                    return false;
                }
            }
            case HASTE_TIME -> player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 3));
            case DAY_NIGHT_SHIFT -> level.setDayTime(level.getDayTime() + 12000);
            case WEATHER_CONTROL -> {
                if (level.isRaining()) {
                    level.setWeatherParameters(6000, 0, false, false);
                } else {
                    level.setWeatherParameters(0, 6000, true, false);
                }
            }
            case PHASE_STEP -> {
                Vec3 target = player.position().add(player.getLookAngle().scale(3));
                player.teleportTo(target.x, target.y, target.z);
            }
            case SLOW_FALL -> player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 0));
            case CLOCK_STOP -> {
                AABB box = player.getBoundingBox().inflate(10.0);
                level.getEntitiesOfClass(LivingEntity.class, box, e -> !(e instanceof Player))
                        .forEach(e -> {
                            e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 250));
                            level.sendParticles(ModParticles.TIME_RIPPLE.get(), e.getX(), e.getY() + 1, e.getZ(), 10, 0.4, 0.4, 0.4, 0.02);
                        });
                level.sendParticles(ModParticles.TIME_RIPPLE.get(), player.getX(), player.getY() + 1, player.getZ(), 30, 5.0, 1.5, 5.0, 0.02);
            }
            case FAST_FORWARD -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 160, 2));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 160, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 160, 2));
            }
            case TIME_ANCHOR -> {
                if (!data.hasAnchor()) {
                    data.setAnchor(player.getX(), player.getY(), player.getZ(), 0);
                    message(player, "Anchor dropped in time.");
                } else {
                    player.teleportTo(data.getAnchorX(), data.getAnchorY(), data.getAnchorZ());
                    data.clearAnchor();
                    message(player, "Pulled back to the anchor.");
                }
            }
            case DILATION_FIELD -> {
                AABB box = player.getBoundingBox().inflate(7.0);
                hostilesIn(level, box).forEach(e -> e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3)));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2));
            }
            case CHRONO_VISION -> {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0));
                AABB box = player.getBoundingBox().inflate(16.0);
                hostilesIn(level, box).forEach(e -> e.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0)));
            }
            case PARADOX_STEP -> {
                LivingEntity target = rayTraceEntity(player, 12.0);
                if (target != null) {
                    Vec3 pPos = player.position();
                    Vec3 tPos = target.position();
                    player.teleportTo(tPos.x, tPos.y, tPos.z);
                    target.teleportTo(pPos.x, pPos.y, pPos.z);
                }
            }
            case ETERNAL_MOMENT -> {
                AABB box = player.getBoundingBox().inflate(14.0);
                hostilesIn(level, box).forEach(e -> e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 250)));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 140, 3));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 140, 2));
                level.sendParticles(ModParticles.TIME_RIPPLE.get(), player.getX(), player.getY() + 1, player.getZ(), 60, 7.0, 2.0, 7.0, 0.02);
                level.sendParticles(ModParticles.VOID_SLASH.get(), player.getX(), player.getY() + 1, player.getZ(), 20, 1.0, 1.0, 1.0, 0.1);
            }
        }

        message(player, ability.getDisplayName());
        return true;
    }

    private static List<LivingEntity> hostilesIn(ServerLevel level, AABB box) {
        return level.getEntitiesOfClass(LivingEntity.class, box,
                e -> e instanceof Mob m && !m.getType().getCategory().isFriendly() && e.isAlive());
    }

    private static LivingEntity rayTraceEntity(ServerPlayer player, double distance) {
        HitResult hit = player.pick(distance, 0.0F, false);
        if (hit.getType() == HitResult.Type.ENTITY && ((EntityHitResult) hit).getEntity() instanceof LivingEntity le) {
            return le;
        }
        return null;
    }

    private static void growCropsAround(ServerLevel level, ServerPlayer player, int radius) {
        BlockPos center = player.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-radius, -2, -radius), center.offset(radius, 2, radius))) {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof CropBlock crop) {
                level.setBlockAndUpdate(pos, crop.getStateForAge(crop.getMaxAge()));
            }
        }
        level.sendParticles(ParticleTypes.HAPPY_VILLAGER, center.getX(), center.getY() + 1, center.getZ(), 40, radius, 1, radius, 0.1);
    }

    private static void sparkle(ServerLevel level, Entity at, net.minecraft.core.particles.ParticleOptions type, int count) {
        level.sendParticles(type, at.getX(), at.getY() + 1, at.getZ(), count, 0.5, 0.5, 0.5, 0.05);
    }

    private static void message(ServerPlayer player, String msg) {
        player.displayClientMessage(Component.literal(msg), true);
    }
}
