package com.godoftime.network;

import com.godoftime.ability.TimeMode;
import com.godoftime.capability.CapabilityEvents;
import com.godoftime.capability.ITimeGodData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SSwitchModePacket {

    public C2SSwitchModePacket() {}

    public static void encode(C2SSwitchModePacket msg, FriendlyByteBuf buf) {}

    public static C2SSwitchModePacket decode(FriendlyByteBuf buf) {
        return new C2SSwitchModePacket();
    }

    public static void handle(C2SSwitchModePacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;
            ITimeGodData data = CapabilityEvents.get(player);
            TimeMode next = data.getMode().next();
            data.setMode(next);
            player.displayClientMessage(Component.literal("Stance shifted to ")
                    .append(Component.literal(next.getDisplayName()).withStyle(next.getColor())), true);
            NetworkHandler.CHANNEL.send(net.minecraftforge.network.PacketDistributor.PLAYER.with(() -> player),
                    new S2CSyncDataPacket(next.getId(), data.getSelectedAbility(next)));
        });
        ctx.setPacketHandled(true);
    }
}
