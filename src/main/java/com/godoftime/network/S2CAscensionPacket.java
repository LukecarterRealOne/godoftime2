package com.godoftime.network;

import com.godoftime.client.AscensionClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CAscensionPacket {

    public S2CAscensionPacket() {}

    public static void encode(S2CAscensionPacket msg, FriendlyByteBuf buf) {}

    public static S2CAscensionPacket decode(FriendlyByteBuf buf) {
        return new S2CAscensionPacket();
    }

    public static void handle(S2CAscensionPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> AscensionClientHandler::playAscension));
        ctx.setPacketHandled(true);
    }
}
