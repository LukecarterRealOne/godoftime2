package com.godoftime.network;

import com.godoftime.client.ClientHudState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSyncDataPacket {

    private final int modeId;
    private final int abilityId;

    public S2CSyncDataPacket(int modeId, int abilityId) {
        this.modeId = modeId;
        this.abilityId = abilityId;
    }

    public static void encode(S2CSyncDataPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.modeId);
        buf.writeInt(msg.abilityId);
    }

    public static S2CSyncDataPacket decode(FriendlyByteBuf buf) {
        return new S2CSyncDataPacket(buf.readInt(), buf.readInt());
    }

    public static void handle(S2CSyncDataPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                ClientHudState.update(msg.modeId, msg.abilityId)));
        ctx.setPacketHandled(true);
    }
}
