package com.duzo.fakeplayers.networking.packets;

import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class UpdateHumanoidNameTagShownC2SPacket {
    public boolean messageIsValid;
    private UUID uuid;
    private boolean shown;

    public UpdateHumanoidNameTagShownC2SPacket(UUID humanoidUUID, boolean shown) {
        this.uuid = humanoidUUID;
        this.shown = shown;
        this.messageIsValid = true;
    }
    public UpdateHumanoidNameTagShownC2SPacket() {
        this.messageIsValid = false;
    }

    public static UpdateHumanoidNameTagShownC2SPacket decode(FriendlyByteBuf buf) {
        UpdateHumanoidNameTagShownC2SPacket packet = new UpdateHumanoidNameTagShownC2SPacket();

        try {
            packet.shown = buf.readBoolean();
            packet.uuid = buf.readUUID();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }

        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;

        buf.writeBoolean(this.shown);
        buf.writeUUID(this.uuid);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // This only runs on the server
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            if (level.getEntity(this.uuid) instanceof HumanoidEntity humanoid) {
                humanoid.setNametagShown(this.shown);
            }
        });
        return true;
    }
}
