package com.duzo.fakeplayers.networking.packets;

import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class UpdateHumanoidAIC2SPacket {
    public boolean messageIsValid;
    private UUID uuid;
    private boolean noAI;
    private boolean setTarget;
    private boolean forceTargeting;

    public UpdateHumanoidAIC2SPacket(UUID humanoidUUID, boolean noAI, boolean setTarget, boolean forceTarget) {
        this.uuid = humanoidUUID;
        this.noAI = noAI;
        this.setTarget = setTarget;
        this.forceTargeting = forceTarget;
        this.messageIsValid = true;
    }
    public UpdateHumanoidAIC2SPacket() {
        this.messageIsValid = false;
    }

    public static UpdateHumanoidAIC2SPacket decode(FriendlyByteBuf buf) {
        UpdateHumanoidAIC2SPacket packet = new UpdateHumanoidAIC2SPacket();

        try {
            packet.noAI = buf.readBoolean();
            packet.uuid = buf.readUUID();
            packet.setTarget = buf.readBoolean();
            packet.forceTargeting = buf.readBoolean();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }

        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;

        buf.writeBoolean(this.noAI);
        buf.writeUUID(this.uuid);
        buf.writeBoolean(this.setTarget);
        buf.writeBoolean(this.forceTargeting);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // This only runs on the server
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            if (level.getEntity(this.uuid) instanceof FakePlayerEntity humanoid) {
                humanoid.setNoAi(this.noAI);

                if (this.setTarget) {
                    humanoid.setTarget(player);
                }

                if (this.forceTargeting) {
                    humanoid.setForceTargeting(player);
                } else {
                    humanoid.setForceTargeting(false);
                }
            }
        });
        return true;
    }
}
