package com.duzo.fakeplayers.network;

import com.duzo.fakeplayers.Fakeplayers;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class NetworkConstants {
    public static final Identifier SEND_SKIN_PACKET_ID = new Identifier(Fakeplayers.MOD_ID, "send_skin");

    public static void sendPacketToAll(MinecraftServer server, Identifier packet, PacketByteBuf buf) {
        for (ServerPlayerEntity player : PlayerLookup.all(server)) {
            ServerPlayNetworking.send(player,packet,buf);
        }
    }
}
