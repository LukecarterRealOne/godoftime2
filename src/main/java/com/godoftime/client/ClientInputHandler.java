package com.godoftime.client;

import com.godoftime.GodOfTimeMod;
import com.godoftime.network.C2SActivateAbilityPacket;
import com.godoftime.network.C2SCycleAbilityPacket;
import com.godoftime.network.C2SSwitchModePacket;
import com.godoftime.network.NetworkHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GodOfTimeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientInputHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        while (ModKeyBindings.SWITCH_MODE.consumeClick()) {
            NetworkHandler.CHANNEL.sendToServer(new C2SSwitchModePacket());
        }
        while (ModKeyBindings.USE_ABILITY.consumeClick()) {
            NetworkHandler.CHANNEL.sendToServer(new C2SActivateAbilityPacket());
        }
        while (ModKeyBindings.CYCLE_NEXT.consumeClick()) {
            NetworkHandler.CHANNEL.sendToServer(new C2SCycleAbilityPacket(true));
        }
        while (ModKeyBindings.CYCLE_PREV.consumeClick()) {
            NetworkHandler.CHANNEL.sendToServer(new C2SCycleAbilityPacket(false));
        }
    }
}
