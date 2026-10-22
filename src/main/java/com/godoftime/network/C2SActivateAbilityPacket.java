package com.godoftime.network;

import com.godoftime.ability.AbilityManager;
import com.godoftime.ability.TimeAbility;
import com.godoftime.capability.CapabilityEvents;
import com.godoftime.capability.ITimeGodData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SActivateAbilityPacket {

    public C2SActivateAbilityPacket() {}

    public static void encode(C2SActivateAbilityPacket msg, FriendlyByteBuf buf) {}

    public static C2SActivateAbilityPacket decode(FriendlyByteBuf buf) {
        return new C2SActivateAbilityPacket();
    }

    public static void handle(C2SActivateAbilityPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;
            ITimeGodData data = CapabilityEvents.get(player);
            TimeAbility ability = TimeAbility.byId(data.getSelectedAbility(data.getMode()));

            if (ability.getMode() != data.getMode()) return; // safety: can't use off-mode abilities

            int cd = data.getCooldown(ability.getId());
            if (cd > 0) {
                player.displayClientMessage(Component.literal(
                        ability.getDisplayName() + " is still recovering (" + (cd / 20) + "s)"), true);
                return;
            }

            boolean success = AbilityManager.execute(player, data, ability);
            if (success) {
                data.setCooldown(ability.getId(), ability.getCooldownTicks());
            }
        });
        ctx.setPacketHandled(true);
    }
}
