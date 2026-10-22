package com.godoftime.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class AstralSpriteParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected AstralSpriteParticle(ClientLevel level, double x, double y, double z,
                                    double dx, double dy, double dz,
                                    SpriteSet sprites, float r, float g, float b,
                                    float scale, int lifetime) {
        super(level, x, y, z, dx, dy, dz);
        this.sprites = sprites;
        this.xd = dx;
        this.yd = dy;
        this.zd = dz;
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
        this.alpha = 1.0F;
        this.quadSize = scale;
        this.lifetime = lifetime;
        this.hasPhysics = false;
        this.gravity = 0.0F;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(sprites);
        // Fade out over the back half of its life, shrink slightly as it goes.
        if (this.age > this.lifetime / 2) {
            this.alpha = 1.0F - ((float) (this.age - this.lifetime / 2) / (this.lifetime / 2F));
        }
        this.quadSize = Math.max(0.0F, this.quadSize - 0.004F);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    // ---- Generic factory reused for all 5 custom particle types; each is registered with its
    //      own SpriteSet (from its own texture/json) and its own color + size + lifetime tuning. ----
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        private final float r, g, b, scale;
        private final int lifetime;

        public Factory(SpriteSet sprites, float r, float g, float b, float scale, int lifetime) {
            this.sprites = sprites;
            this.r = r;
            this.g = g;
            this.b = b;
            this.scale = scale;
            this.lifetime = lifetime;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                        double x, double y, double z,
                                        double dx, double dy, double dz) {
            return new AstralSpriteParticle(level, x, y, z, dx, dy, dz, sprites, r, g, b, scale, lifetime);
        }
    }
}
