package com.godoftime.network;

import com.godoftime.ability.TimeAbility;
import com.godoftime.ability.TimeMode;
import com.godoftime.capability.CapabilityEvents;
import com.godoftime.capability.ITimeGodData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class C2SCycleAbilityPacket {

    private final boolean forward;

    public C2SCycleAbilityPacket(boolean forward) {
        this.forward = forward;
    }

    public static void encode(C2SCycleAbilityPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.forward);
    }

    public static C2SCycleAbilityPacket decode(FriendlyByteBuf buf) {
        return new C2SCycleAbilityPacket(buf.readBoolean());
    }

    public static void handle(C2SCycleAbilityPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;
            ITimeGodData data = CapabilityEvents.get(player);
            TimeMode mode = data.getMode();
            List<TimeAbility> list = TimeAbility.forMode(mode);
            int current = data.getSelectedAbility(mode);
            int index = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == current) {
                    index = i;
                    break;
                }
            }
            index = msg.forward ? (index + 1) % list.size() : (index - 1 + list.size()) % list.size();
            TimeAbility newAbility = list.get(index);
            data.setSelectedAbility(mode, newAbility.getId());
            player.displayClientMessage(Component.literal(newAbility.getDisplayName())
                    .withStyle(mode.getColor()), true);
            NetworkHandler.CHANNEL.send(net.minecraftforge.network.PacketDistributor.PLAYER.with(() -> player),
                    new S2CSyncDataPacket(mode.getId(), newAbility.getId()));
        });
        ctx.setPacketHandled(true);
    }
}
