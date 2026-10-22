package com.godoftime.client;

import com.godoftime.GodOfTimeMod;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = GodOfTimeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class ClientSetup {

    @net.minecraftforge.eventbus.api.SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ModKeyBindings.SWITCH_MODE);
        event.register(ModKeyBindings.USE_ABILITY);
        event.register(ModKeyBindings.CYCLE_NEXT);
        event.register(ModKeyBindings.CYCLE_PREV);
    }

    @net.minecraftforge.eventbus.api.SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        // nothing extra needed at this time
    }
}
