package com.duzo.fakeplayers.networking.packets;

import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class UpdateHumanoidSittingC2SPacket {
    public boolean messageIsValid;
    private UUID uuid;
    private boolean sat;

    public UpdateHumanoidSittingC2SPacket(UUID humanoidUUID, boolean sat) {
        this.uuid = humanoidUUID;
        this.sat = sat;
        this.messageIsValid = true;
    }
    public UpdateHumanoidSittingC2SPacket() {
        this.messageIsValid = false;
    }

    public static UpdateHumanoidSittingC2SPacket decode(FriendlyByteBuf buf) {
        UpdateHumanoidSittingC2SPacket packet = new UpdateHumanoidSittingC2SPacket();

        try {
            packet.sat = buf.readBoolean();
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

        buf.writeBoolean(this.sat);
        buf.writeUUID(this.uuid);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // This only runs on the server
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            if (level.getEntity(this.uuid) instanceof HumanoidEntity humanoid) {
                humanoid.setSitting(this.sat);
            }
        });
        return true;
    }
}