package com.duzo.fakeplayers.networking;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.networking.packets.SendImageDownloadMessageS2CPacket;
import com.duzo.fakeplayers.networking.packets.SendSkinMessageS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Network {
    private static SimpleChannel INSTANCE;
    private static final Logger LOGGER = LogManager.getLogger();
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(FakePlayers.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;
        
        net.messageBuilder(SendImageDownloadMessageS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SendImageDownloadMessageS2CPacket::decode)
                .encoder(SendImageDownloadMessageS2CPacket::encode)
                .consumerMainThread(SendImageDownloadMessageS2CPacket::handle)
                .add();

        net.messageBuilder(SendSkinMessageS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SendSkinMessageS2CPacket::decode)
                .encoder(SendSkinMessageS2CPacket::encode)
                .consumerMainThread(SendSkinMessageS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAll(MSG message) {
        if (INSTANCE != null && message != null) {
            try {
                INSTANCE.send(PacketDistributor.ALL.noArg(), message);
            } catch (NullPointerException e) {
                LOGGER.warn("Failed to send message to all players.");
            }
        }
    }
}