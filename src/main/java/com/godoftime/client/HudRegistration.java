package com.godoftime.client;

import com.godoftime.GodOfTimeMod;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GodOfTimeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class HudRegistration {

    @net.minecraftforge.eventbus.api.SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("godoftime_hud", ModHudOverlay.INSTANCE);
    }
}
