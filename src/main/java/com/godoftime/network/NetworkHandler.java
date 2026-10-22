package com.godoftime.network;

import com.godoftime.GodOfTimeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(GodOfTimeMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void register() {
        CHANNEL.registerMessage(id++, C2SSwitchModePacket.class,
                C2SSwitchModePacket::encode, C2SSwitchModePacket::decode, C2SSwitchModePacket::handle);

        CHANNEL.registerMessage(id++, C2SActivateAbilityPacket.class,
                C2SActivateAbilityPacket::encode, C2SActivateAbilityPacket::decode, C2SActivateAbilityPacket::handle);

        CHANNEL.registerMessage(id++, C2SCycleAbilityPacket.class,
                C2SCycleAbilityPacket::encode, C2SCycleAbilityPacket::decode, C2SCycleAbilityPacket::handle);

        CHANNEL.registerMessage(id++, S2CSyncDataPacket.class,
                S2CSyncDataPacket::encode, S2CSyncDataPacket::decode, S2CSyncDataPacket::handle);

        CHANNEL.registerMessage(id++, S2CAscensionPacket.class,
                S2CAscensionPacket::encode, S2CAscensionPacket::decode, S2CAscensionPacket::handle);
    }
}
