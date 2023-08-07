package com.duzo.fakeplayers.networking;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.networking.packets.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Network {
    private static SimpleChannel INSTANCE;

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
        net.messageBuilder(RequestServerUpdateSkinsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestServerUpdateSkinsC2SPacket::decode)
                .encoder(RequestServerUpdateSkinsC2SPacket::encode)
                .consumerMainThread(RequestServerUpdateSkinsC2SPacket::handle)
                .add();
        net.messageBuilder(SendSkinMessageS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SendSkinMessageS2CPacket::decode)
                .encoder(SendSkinMessageS2CPacket::encode)
                .consumerMainThread(SendSkinMessageS2CPacket::handle)
                .add();
        net.messageBuilder(UpdateHumanoidAIC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpdateHumanoidAIC2SPacket::decode)
                .encoder(UpdateHumanoidAIC2SPacket::encode)
                .consumerMainThread(UpdateHumanoidAIC2SPacket::handle)
                .add();
        net.messageBuilder(SendHumanoidChatC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SendHumanoidChatC2SPacket::decode)
                .encoder(SendHumanoidChatC2SPacket::encode)
                .consumerMainThread(SendHumanoidChatC2SPacket::handle)
                .add();
        net.messageBuilder(UpdateHumanoidSittingC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpdateHumanoidSittingC2SPacket::decode)
                .encoder(UpdateHumanoidSittingC2SPacket::encode)
                .consumerMainThread(UpdateHumanoidSittingC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAll(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}