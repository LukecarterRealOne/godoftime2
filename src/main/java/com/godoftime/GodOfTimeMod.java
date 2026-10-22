package com.godoftime;

import com.godoftime.item.ModItems;
import com.godoftime.network.NetworkHandler;
import com.godoftime.particle.ModParticles;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GodOfTimeMod.MOD_ID)
public class GodOfTimeMod {

    public static final String MOD_ID = "godoftime";

    public GodOfTimeMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modBus);
        ModParticles.PARTICLE_TYPES.register(modBus);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::buildCreativeTab);

        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }

    private void buildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.ASTRAL_SWORD.get());
            event.accept(ModItems.CHRONO_CORE.get());
        }
    }
}
