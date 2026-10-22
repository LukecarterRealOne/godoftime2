package com.godoftime.particle;

import com.godoftime.GodOfTimeMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Custom particle types, each with its own sprite (assets/godoftime/textures/particle/*.png)
 * and definition (assets/godoftime/particles/*.json). Registered here (common), rendered via
 * ModParticleFactories (client-only).
 */
public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, GodOfTimeMod.MOD_ID);

    // Trailing light shed by the Astral Sword while held/swung.
    public static final RegistryObject<SimpleParticleType> ASTRAL_TRAIL =
            PARTICLE_TYPES.register("astral_trail", () -> new SimpleParticleType(false));

    // The two orbiting guardian blades (yellow + black).
    public static final RegistryObject<SimpleParticleType> ASTRAL_BLADE_YELLOW =
            PARTICLE_TYPES.register("astral_blade_yellow", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> ASTRAL_BLADE_BLACK =
            PARTICLE_TYPES.register("astral_blade_black", () -> new SimpleParticleType(false));

    // Sharp burst used by combat / erasure abilities (Astral Slash, Erase, Wrath of Hyperverse, etc.)
    public static final RegistryObject<SimpleParticleType> VOID_SLASH =
            PARTICLE_TYPES.register("void_slash", () -> new SimpleParticleType(false));

    // Expanding ring used by time-control abilities (Time Freeze, Clock Stop, Shatter Time, etc.)
    public static final RegistryObject<SimpleParticleType> TIME_RIPPLE =
            PARTICLE_TYPES.register("time_ripple", () -> new SimpleParticleType(false));
}
