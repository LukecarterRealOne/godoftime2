package com.godoftime.particle;

import com.godoftime.GodOfTimeMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GodOfTimeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticleFactories {

    @SubscribeEvent
    public static void registerProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.ASTRAL_TRAIL.get(), sprites ->
                new AstralSpriteParticle.Factory(sprites, 1.0F, 0.86F, 0.25F, 0.35F, 14));

        event.registerSpriteSet(ModParticles.ASTRAL_BLADE_YELLOW.get(), sprites ->
                new AstralSpriteParticle.Factory(sprites, 1.0F, 0.82F, 0.15F, 0.55F, 10));

        event.registerSpriteSet(ModParticles.ASTRAL_BLADE_BLACK.get(), sprites ->
                new AstralSpriteParticle.Factory(sprites, 0.08F, 0.08F, 0.1F, 0.55F, 10));

        event.registerSpriteSet(ModParticles.VOID_SLASH.get(), sprites ->
                new AstralSpriteParticle.Factory(sprites, 1.0F, 0.95F, 0.6F, 0.9F, 12));

        event.registerSpriteSet(ModParticles.TIME_RIPPLE.get(), sprites ->
                new AstralSpriteParticle.Factory(sprites, 0.45F, 0.85F, 1.0F, 1.1F, 20));
    }
}
