package com.godoftime.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AscensionClientHandler {

    public static void playAscension() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.gui == null) return;

        // Large, dramatic title — shown the instant death would have happened, before revival completes.
        mc.gui.setTitle(Component.literal("I AM THE GOD OF HYPERVERSE")
                .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD));
        mc.gui.setSubtitle(Component.literal("Time itself refuses to let this end")
                .withStyle(ChatFormatting.YELLOW));
        mc.gui.setTimes(5, 70, 20);

        if (mc.player != null) {
            mc.player.playSound(net.minecraft.sounds.SoundEvents.BEACON_POWER_SELECT, 1.0F, 0.5F);
            mc.player.playSound(net.minecraft.sounds.SoundEvents.WARDEN_ROAR, 0.6F, 1.6F);
        }
    }
}
