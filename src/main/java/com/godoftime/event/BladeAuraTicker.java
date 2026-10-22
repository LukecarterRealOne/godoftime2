package com.godoftime.event;

import com.godoftime.capability.ITimeGodData;
import com.godoftime.particle.ModParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 * Renders a pair of orbiting astral blades behind the player (custom sprite particles,
 * yellow + black) and periodically strikes any hostile entity nearby while the aura is active.
 */
public class BladeAuraTicker {

    public static void tick(ServerPlayer player, ITimeGodData data) {
        if (data.getBladeAuraTicks() <= 0) return;
        if (!(player.level() instanceof ServerLevel level)) return;

        double t = player.tickCount * 0.25;
        float yaw = (float) Math.toRadians(player.getYRot() + 180);

        // Two blades orbiting behind the player, one yellow one black, weaving around each other.
        for (int i = 0; i < 2; i++) {
            double angle = t + (i * Math.PI);
            double radius = 1.3;
            double bx = player.getX() - Math.sin(yaw) * 1.2 + Math.cos(angle) * radius * 0.4;
            double by = player.getY() + 1.1 + Math.sin(angle) * 0.4;
            double bz = player.getZ() - Math.cos(yaw) * 1.2 + Math.sin(angle) * radius * 0.4;

            level.sendParticles(i == 0 ? ModParticles.ASTRAL_BLADE_YELLOW.get() : ModParticles.ASTRAL_BLADE_BLACK.get(),
                    bx, by, bz, 1, 0.0, 0.0, 0.0, 0.0);
        }

        // Every half second, the blades lash out at anything hostile nearby.
        if (player.tickCount % 10 == 0) {
            AABB box = player.getBoundingBox().inflate(7.0);
            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, box,
                    e -> e instanceof Mob m && !m.getType().getCategory().isFriendly() && e.isAlive());
            for (LivingEntity target : targets) {
                target.hurt(player.damageSources().magic(), 20.0F);
                level.sendParticles(ModParticles.VOID_SLASH.get(), target.getX(), target.getY() + target.getBbHeight() / 2,
                        target.getZ(), 6, 0.3, 0.3, 0.3, 0.1);
            }
        }
    }
}
