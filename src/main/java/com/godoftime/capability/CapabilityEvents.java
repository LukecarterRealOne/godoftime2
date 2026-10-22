package com.godoftime.capability;

import com.godoftime.GodOfTimeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GodOfTimeMod.MOD_ID)
public class CapabilityEvents {

    public static final ResourceLocation TIME_GOD_ID = new ResourceLocation(GodOfTimeMod.MOD_ID, "time_god_data");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<net.minecraft.world.entity.Entity> event) {
        if (event.getObject() instanceof Player) {
            TimeGodProvider provider = new TimeGodProvider();
            event.addCapability(TIME_GOD_ID, provider);
            event.addListener(provider::invalidate);
        }
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(TimeGodProvider.TIME_GOD_CAPABILITY).ifPresent(oldStore -> {
                event.getEntity().getCapability(TimeGodProvider.TIME_GOD_CAPABILITY).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    public static ITimeGodData get(Player player) {
        return player.getCapability(TimeGodProvider.TIME_GOD_CAPABILITY).orElseThrow(
                () -> new IllegalStateException("God of Time capability missing on " + player.getName().getString()));
    }
}
