package com.godoftime.event;

import com.godoftime.GodOfTimeMod;
import com.godoftime.capability.CapabilityEvents;
import com.godoftime.capability.ITimeGodData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GodOfTimeMod.MOD_ID)
public class WorldRewindCapture {

    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;
        ITimeGodData data = CapabilityEvents.get(player);
        data.setLastBrokenBlock(event.getPos(), event.getState());
    }
}
