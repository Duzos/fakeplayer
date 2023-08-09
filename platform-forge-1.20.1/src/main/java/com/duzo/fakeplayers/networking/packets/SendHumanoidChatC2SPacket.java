package com.duzo.fakeplayers.networking.packets;

import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SendHumanoidChatC2SPacket {
    public boolean messageIsValid;
    private UUID uuid;
    private String message;

    public SendHumanoidChatC2SPacket(UUID humanoidUUID, String message) {
        this.uuid = humanoidUUID;
        this.message = message;
        this.messageIsValid = true;
    }
    public SendHumanoidChatC2SPacket() {
        this.messageIsValid = false;
    }

    public static SendHumanoidChatC2SPacket decode(FriendlyByteBuf buf) {
        SendHumanoidChatC2SPacket packet = new SendHumanoidChatC2SPacket();

        try {
            packet.uuid = buf.readUUID();
            packet.message = buf.readUtf();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }

        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;

        buf.writeUUID(this.uuid);
        buf.writeUtf(this.message);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // This only runs on the server
            ServerPlayer player = context.getSender();
            ServerLevel level = (ServerLevel) player.level();

            if (level.getEntity(this.uuid) instanceof FakePlayerEntity humanoid) {
                humanoid.sendChat(this.message);
            }
        });
        return true;
    }
}
